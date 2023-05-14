package logintesting

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import de.ship.matrixscala.api.clientserver.{RefreshApi, WhoamiApi}
import de.ship.matrixscala.model.clientserver.{RefreshData, WhoamiData}
import de.ship.matrixscala.core.{AccessTokenAuthentication, ApiRequest, ApiResponse, InvokerActor, NoAuthentication}
import de.ship.matrixscala.api.clientserver.LoginApi
import de.ship.matrixscala.core.InvokerActor.Invoke
import de.ship.matrixscala.core.Authentication
import de.ship.matrixscala.model.clientserver.LoginData
import de.ship.matrixscala.model.clientserver.LoginData.Login
import de.ship.matrixscala.model.clientserver.RefreshData.Refresh
import de.ship.matrixscala.model.clientserver.WhoamiData.GetTokenOwner
import spray.json.JsObject

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration


object RequestProxyActor {
  sealed trait Command
  case class Proxy[U,T](invoke: Invoke[_ <: Authentication,U,T]) extends Command
  case class FreshAuth(authToken: String, refreshToken: String, validFor: FiniteDuration) extends Command

  def loginReq(user: String, password: String): ApiRequest[NoAuthentication, Login.Body, Login.OK] =
    LoginApi.login(
      LoginData.Login.Body(
        `type` = LoginData.Login.Body.TypeEnum.`m.login.password`,
        user = Some(user),
        password = Some(password),
        //let matrix now, we want a expirable token, which we will continuously refresh
        refresh_token = Some(true),
        initial_device_display_name = Some("login-example")
      )
    )
  def init(homeserver: String, user: String, password: String): Behavior[Command] = Behaviors.setup{ ctx=>
    //let us initialize a non authenticated actor
    val invokerActor = ctx.spawn(InvokerActor.create(homeserver, NoAuthentication()), "invoker-actor")
    // adapt the raw respone to the FreshAuth command

    val loginResponseAdapter: ActorRef[ApiResponse[LoginData.Login.OK]] = ctx.messageAdapter { rsp =>
      val data = rsp.data.get
      FreshAuth(data.access_token, data.refresh_token.get, data.expires_in_ms.get.millis)
    }
    invokerActor ! InvokerActor.Invoke(loginReq(user, password), loginResponseAdapter)

    //stash any incoming requests, while we are not logged in yet
    Behaviors.withStash(capacity=10) { stash =>
      Behaviors.receiveMessage {
        case proxy: Proxy[_, _] => {
          ctx.log.warn("not logget in yet, stashing ...")
          stash.stash(proxy)
          Behaviors.same
        }
        case fresh: FreshAuth => {
          stash.unstashAll(proxyState(invokerActor, fresh, None))
        }
      }
    }
  }

  //build our request
  def refreshReq(refreshToken: String): ApiRequest[NoAuthentication, Refresh.Body, Refresh.OK] =
    RefreshApi.refresh(RefreshData.Refresh.Body(refreshToken))

  def proxyState(invokerActor: ActorRef[InvokerActor.InvokerCommand], fresh: FreshAuth, refreshAdapter: Option[ActorRef[ApiResponse[Refresh.OK]]]): Behavior[Command] = Behaviors.setup{ ctx =>
    val refreshDelay = fresh.validFor - 5000.millis
    ctx.log.info(s"validated for ${fresh.validFor}, will refresh token in: $refreshDelay")

    /*
    Well this is ugly, let me explain (synthwave goose - blade runner 2049 starts playing)
    typed adapters use a simple class match, and since a ApiResponse[A] and ApiResponse[B] use the same class, they're both matched
    Type-Erasure is the root issue for us here, a future version will implement some better matching
     */
    val adapter: ActorRef[ApiResponse[Refresh.OK]] = refreshAdapter.getOrElse(ctx.messageAdapter { rsp =>
      val data = rsp.data.get
      FreshAuth(data.access_token, data.refresh_token.get, data.expires_in_ms.get.millis)
    })
    // change authentication in our invoker
    invokerActor ! InvokerActor.ChangeAuthentication(AccessTokenAuthentication(fresh.authToken))

    // refresh our token
    ctx.scheduleOnce(refreshDelay, invokerActor, InvokerActor.Invoke(refreshReq(fresh.refreshToken), adapter))
    Behaviors.receiveMessage{
      //proxy any incoming messages
      case Proxy(invoke) => {
        invokerActor ! invoke
        Behaviors.same
      }
      case fresh: FreshAuth => proxyState(invokerActor,fresh, Some(adapter))
    }

  }
}


object DummySender{
  type SomeData = WhoamiData.GetTokenOwner.OK

  def someReq: ApiRequest[AccessTokenAuthentication, JsObject, GetTokenOwner.OK] = WhoamiApi.getTokenOwner()
  def apply(proxy: ActorRef[RequestProxyActor.Command]): Behavior[ApiResponse[SomeData]] = Behaviors.setup{ ctx=>
    implicit val ec: ExecutionContext = ctx.executionContext
    //just some random authenticated response every 5 seconds
    ctx.system.scheduler.scheduleAtFixedRate(0.millis, 5.seconds)(() => proxy ! RequestProxyActor.Proxy(Invoke(someReq, ctx.self)))
    Behaviors.receiveMessage{foo =>
      ctx.log.info(s"received: $foo")
      Behaviors.same
    }
  }
}

object RootActor{
  def apply(homeserver: String, user: String, password: String): Behavior[Unit] = Behaviors.setup{ctx =>
    val proxyActor = ctx.spawn(RequestProxyActor.init(homeserver, user, password), "proxy-actor")
    val sender = ctx.spawn(DummySender.apply(proxyActor), "dummy-sender")
    Behaviors.ignore
  }
}

object LoginTesting{
  def main(args: Array[String]): Unit = {
    val system = ActorSystem(RootActor.apply(homeserver="http://my.matrix.host:8008", user="robot", password="robot"), "login-testing")
  }
}