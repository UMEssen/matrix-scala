/**
  * <h1>matrix-scala</h1>
  * @version 1.0.0
  * @author kirill.sokol@uk-essen.de
  *
  * This file was generated.
  *
  * DO NOT EDIT THIS FILE. Any changes to this file will be overwritten
  * the next time the code is generated. If you need to modify the code
  * generated by this tool, make your changes to the generator script
  * and regenerate the code.
  */

package de.ship.matrixscala.core;

import de.ship.matrixscala.core.ResponseMappings.{RangeResponseMapping, ResponseMapping}
import de.ship.matrixscala.json.core.MatrixErrorFormats._

import akka.{Done, NotUsed}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.http.scaladsl.model.{
  ContentTypes,
  HttpEntity,
  HttpRequest,
  HttpResponse,
  RequestEntity,
  Uri
}
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}
import akka.stream.{CompletionStrategy, Materializer, OverflowStrategy}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshal}
import spray.json._
import spray.json.RootJsonFormat

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

private object TypeAliases {
  type ClassicActorRef = akka.actor.ActorRef
}
import TypeAliases._

object InvokerActor {
  sealed trait InvokerCommand
  case class ChangeAuthentication(auth: Authentication) extends InvokerCommand
  // TODO: what about ACK and so forth?
  case class Invoke[A <: Authentication, T, U](
      req: ApiRequest[A, T, U],
      respondTo: ActorRef[ApiResponse[U]],
      //manual specification, otherwise attached by the invoker
      authentication: Option[Authentication] = None
  ) extends InvokerCommand

  private def pushingState(
      sourceActor: ClassicActorRef,
      auth: Authentication
  ): Behavior[InvokerCommand] =
    Behaviors.receive { (ctx, command) =>
      command match {
        case ChangeAuthentication(newAuth) => pushingState(sourceActor, newAuth)

        // manually set authentication
        case invoke: Invoke[_, _, _] if invoke.authentication.isDefined => {
          sourceActor ! invoke
          Behaviors.same
        }
        // otherwise attach our authentication
        case invoke: Invoke[_, _, _] => {
          sourceActor ! invoke.copy(authentication = Some(auth))
          Behaviors.same
        }
      }
    }

  def create(homeserverUrl: String, auth: Authentication): Behavior[InvokerCommand] =
    Behaviors.setup { ctx =>
      implicit val system: ActorSystem[_] = ctx.system
      implicit val ec: ExecutionContext   = ctx.executionContext
      if (homeserverUrl.endsWith("/"))
        throw new IllegalArgumentException(
          "the homeserver url should not have a trailing slash"
        )
      val sourceActor = StreamBuild(homeserverUrl).buildGraph.run()
      pushingState(sourceActor, auth)
    }
}

private case class StreamBuild(homeserver: String)(implicit
    system: ActorSystem[_],
    ec: ExecutionContext,
    mat: Materializer
) {
  import InvokerActor.Invoke
  private val http = Http(system)

  //data with the invoke as context, TODO: use FlowWithContext from akka
  private type WithInvoke[T] = (T, Invoke[_ <: Authentication, _, _])

  def buildGraph: RunnableGraph[ClassicActorRef] =
    actorBasedSource
      .map(invoke => (invoke.req, invoke))
      .via(httpRequestFlow)
      .via(httpUnmarshalFlow)
      .to(actorResponseSink)

  private val actorResponseSink: Sink[WithInvoke[ApiResponse[_]], _] =
    Sink.foreach[WithInvoke[ApiResponse[_]]] {
      case (response, invoke) =>
        // HACK for type constraints
        invoke.respondTo.asInstanceOf[ActorRef[ApiResponse[_]]] ! response
    }

  private val actorBasedSource: Source[Invoke[_ <: Authentication, _, _], akka.actor.ActorRef] =
    Source.actorRef(
      completionMatcher = {
        case Done =>
          CompletionStrategy.immediately
      },
      failureMatcher = PartialFunction.empty,
      bufferSize = 16,
      overflowStrategy = OverflowStrategy.dropHead //TODO
    )

  private val httpUnmarshalFlow: Flow[
    WithInvoke[HttpResponse],
    WithInvoke[ApiResponse[_]],
    NotUsed
  ] =
    Flow[WithInvoke[HttpResponse]]
      .mapAsync(1) {
        case (httpResponse, invoke) =>
          unmarshalHttpResponse(httpResponse, invoke.req.responseMappings)
            .map { case (code, data) => ApiResponse(code, data) }
            .map((_, invoke))
      }

  private val httpRequestFlow
      : Flow[WithInvoke[ApiRequest[_, _, _]], WithInvoke[HttpResponse], NotUsed] =
    Flow[WithInvoke[ApiRequest[_, _, _]]]
      .map {
        case (req, invoke) => {
          val auth = invoke.authentication.getOrElse(
            throw new IllegalArgumentException("no authentication strategy given")
          )
          (buildHttpRequest(req, auth), invoke)
        }
      }
      .mapAsync(1) { case (req, invoke) => http.singleRequest(req).map((_, invoke)) }

  private def buildHttpRequest[U, T](
      req: ApiRequest[_, U, T],
      auth: Authentication
  ): HttpRequest = {
    val nonAuthenticated = HttpRequest(
      method = req.httpMethod,
      uri = buildUri(req.endpoint, req.inQuery, req.inPath),
      entity = buildHttpEntity(req.body, req.bodyFormat)
    )
    auth.attachAuthentication(nonAuthenticated)
  }

  private def buildUri(
      endpoint: String,
      queryParams: Map[String, String],
      pathParams: Map[String, Any]
  ): String =
    formatQueryParameters(
      formatPathParameters((homeserver + endpoint), pathParams),
      queryParams
    )

  private def formatPathParameters(
      urlTemplate: String,
      params: Map[String, Any]
  ): String =
    params.foldLeft(urlTemplate) { (uri, param) =>
      uri.replace(s"{${param._1}}", param._2.toString)
    }

  private def formatQueryParameters(
      uri: String,
      params: Map[String, String]
  ): String =
    Uri(uri).withQuery(Uri.Query(params)).toString

  private def buildHttpEntity[T](
      body: T,
      format: RootJsonFormat[T]
  ): RequestEntity =
    HttpEntity(
      ContentTypes.`application/json`,
      body.toJson(format).compactPrint
    )

  private def unmarshalAsMatrixError(rawResp: HttpResponse): Future[MatrixError] = {
    implicit val unmarshaller: FromEntityUnmarshaller[MatrixError] =
      SprayJsonSupport.sprayJsonUnmarshaller[MatrixError]
    Unmarshal(rawResp).to[MatrixError]
  }

  private def unmarshalWithMapping[T](
      rawResp: HttpResponse,
      mapping: RangeResponseMapping[T]
  ): Future[T] = {
    implicit val unmarshaller: FromEntityUnmarshaller[T] =
      SprayJsonSupport.sprayJsonUnmarshaller[T](mapping.format)
    Unmarshal(rawResp).to[T]
  }

  private def unmarshalHttpResponse[T](
      rawResp: HttpResponse,
      mappings: Seq[ResponseMapping[T]]
  ): Future[(Int, Try[T])] = {
    val respCode = rawResp.status.intValue
    val mapping = mappings
      .collectFirst {
        case range: RangeResponseMapping[T] if (respCode >= range.start && respCode <= range.end) =>
          range
      }
    val future = mapping match {
      case Some(mapping) =>
        unmarshalWithMapping(rawResp, mapping).map(Success.apply)
      case None =>
        unmarshalAsMatrixError(rawResp)
          .map(e => Failure.apply(e))
          .fallbackTo(Future.failed(new RuntimeException("unexpected http entity")))
    }
    future.map((respCode, _))
  }
}
