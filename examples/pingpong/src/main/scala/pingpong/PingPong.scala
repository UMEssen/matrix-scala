package pingpong

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import de.ship.matrixscala.api.clientserver.{RoomSendApi, SyncApi}
import de.ship.matrixscala.core.{AccessTokenAuthentication, ApiRequest, ApiResponse, InvokerActor}
import de.ship.matrixscala.json.EventSchemaFormats._
import de.ship.matrixscala.model.Definitions.ClientEventWithoutRoomID
import de.ship.matrixscala.model.clientserver.SyncData.Sync
import de.ship.matrixscala.model.EventSchemas.MRoomMessageMText
import de.ship.matrixscala.model.clientserver.RoomSendData.SendMessage
import scala.concurrent.duration._
import spray.json._

import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Random}

//just for more readability
object TypeAliases{
  type MessageEvent = MRoomMessageMText.Content
  type UserId = String
  type RoomId = String
  type Invoker = ActorRef[InvokerActor.Invoke[_,_,_]]
}
import TypeAliases._

//in matrix, a text message event only holds the content
case class MessageWithContext(roomId: RoomId, sender: UserId, msg: String )

object PongActor{
  // the txnId ensures idempotency
  def genTxnId: String = Random.alphanumeric.take(10).mkString

  //reply with a simple PONG and return the future
  def replyToReq(message: MessageWithContext): ApiRequest[AccessTokenAuthentication, JsObject, SendMessage.OK] =
    RoomSendApi.sendMessage(
      eventType = MRoomMessageMText.TypeEnum.`m.room.message`.toString,
      txnId = genTxnId,
      roomId = message.roomId,
      body = MRoomMessageMText.Content(body="PONG", msgtype = MRoomMessageMText.Content.MsgtypeEnum.`m.text`, format = None, formatted_body = None).toJson.asJsObject
    )


  def apply(invoker: Invoker): Behavior[Seq[MessageWithContext]] = Behaviors.receive {
    (ctx, msgList) => {
      msgList.filter(_.msg == "ping").foreach {
        //since I am lazy, I will discard any response
        message => invoker ! InvokerActor.Invoke(replyToReq(message), ctx.system.ignoreRef)
      }
      Behaviors.same
    }
  }
}

object MessageExractingActor{
  val roomMessageType: String = MRoomMessageMText.TypeEnum.`m.room.message`.toString

  def extractMessageEventsFromSync(data: Sync.OK): Seq[MessageWithContext] = {
    // go through all sync data, extract the client events and tuple them up with their respective room origin  
    val clientEvents = data.rooms.flatMap(_.join).iterator.flatten
      .flatMap{
        case (roomId,joinedRooms) => joinedRooms.timeline.map((v => (roomId, v.events)))
      }
    val messages = clientEvents.flatMap{
      case (roomId, clientEvents) => clientEvents.collect {
        case event: ClientEventWithoutRoomID if event.`type` == roomMessageType => MessageWithContext(
          roomId,
          event.sender,
          // in matrix, a event is any Json Object. But there are some predefined ones.
          // keep in mind, that any event data is not trustable/expectable
          event.content.convertTo[MessageEvent].body
        )
      }
    }.toSeq
    messages
  }

  def apply(replyTo: ActorRef[Seq[MessageWithContext]]): Behavior[Sync.OK] = Behaviors.receive{ (ctx, msg) =>
    // extract messages and pass them to the ping pong actor
    replyTo ! extractMessageEventsFromSync(msg)
    Behaviors.same
  }
}

object SyncActor{
  //call the sync endpoint with the given since parameter
  //the `/sync` endpoint implements long-polling
  //we will not receive a response, until new events are there
  //when the timeout is reached, a "empty" response is returned
  def syncReq(lastBatch: Option[String]): ApiRequest[AccessTokenAuthentication, JsObject, Sync.OK] = SyncApi.sync(
    since = lastBatch,
    timeout = Some(3000.millis.toMillis.intValue())
  )

  // Genesis of the sync loop:
  // Then God said, 'Let there be a long-polled loop, which holds the newest events'
  // And God saw that it was good
  def apply(invoker: Invoker, extractingActor: ActorRef[Sync.OK], lastBatch: Option[String] = None, adapter: Option[ActorRef[ApiResponse[Sync.OK]]] = None): Behavior[Sync.OK] = Behaviors.setup{ (ctx) =>
    implicit val ec: ExecutionContext = ctx.executionContext

    val adapterActor: ActorRef[ApiResponse[Sync.OK]] = adapter.getOrElse(ctx.messageAdapter(resp =>
      resp.data.get
    ))

    invoker ! InvokerActor.Invoke(syncReq(lastBatch), adapterActor)
    Behaviors.receiveMessage{ syncData=>
      //ctx.log.info(s"last: $lastBatch, new batch: ${syncData.next_batch}")
      extractingActor ! syncData
      apply(invoker, extractingActor, Some(syncData.next_batch), Some(adapterActor))
    }
  }
}

object InitActor{
  def apply(homeserverUrl: String, authToken: String): Behavior[Unit] = Behaviors.setup{ (ctx) =>
    implicit val ec = ctx.system.executionContext
    implicit val system = ctx.system
    val invokerActor = ctx.spawn(InvokerActor.create(homeserverUrl, auth = AccessTokenAuthentication(authToken)), "invoker-actor")

    val pongActor = ctx.spawn(PongActor.apply(invokerActor), "pong-actor")
    val extractorActor = ctx.spawn(MessageExractingActor.apply(pongActor), "message-extracting-actor")
    ctx.spawn(SyncActor.apply(invokerActor, extractorActor), "sync-actor")
    Behaviors.empty
  }
}

object PingPong {
    def main(args: Array[String]): Unit = {
      val matrixHost = "http://my.matrix.host:8008" //your server here
      val authToken = "syt_cm9ib3Q_bkMcgTAguTWdgEIkvPTb_4cmMZ6" //your token here
      val system = ActorSystem(InitActor.apply(matrixHost, authToken), "dummy-system")
      Await.result(system.whenTerminated, 10.days)
    }
}
