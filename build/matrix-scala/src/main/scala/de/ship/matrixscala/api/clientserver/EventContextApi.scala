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

package de.ship.matrixscala.api.clientserver

import de.ship.matrixscala.model.Definitions
import de.ship.matrixscala.model.clientserver.EventContextData._
import de.ship.matrixscala.json.clientserver.EventContextJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/event_context.yaml"
  */
object EventContextApi {

  /**
    * <h1>getEventContext</h1>
    *
    * This API returns a number of events that happened just before and
    * after the specified event. This allows clients to get the context
    * surrounding an event.
    *
    * Note*: This endpoint supports lazy-loading of room member events. See
    * [Lazy-loading room members](/client-server-api/#lazy-loading-room-members) for more information.
    *
    * @param roomId The room to get events from.
    *
    * @param eventId The event to get context around.
    *
    * @param limit The maximum number of context events to return. The limit applies
    * to the sum of the `events_before` and `events_after` arrays. The
    * requested event ID is always returned in `event` even if `limit` is
    * 0. Defaults to 10.
    *
    * @param filter A JSON `RoomEventFilter` to filter the returned events with. The
    * filter is only applied to `events_before`, `events_after`, and
    * `state`. It is not applied to the `event` itself. The filter may
    * be applied before or/and after the `limit` parameter - whichever the
    * homeserver prefers.
    *
    * See [Filtering](/client-server-api/#filtering) for more information.
    */
  def getEventContext(
      roomId: String,
      eventId: String,
      limit: Option[Int] = None,
      filter: Option[String] = None
  ): ApiRequest[AccessTokenAuthentication, JsObject, GetEventContext.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/rooms/{roomId}/context/{eventId}",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[GetEventContext.OK]])
      ),
      inQuery = buildQuery(
        fromQueryEntry[Option[Int]]("limit"     -> limit),
        fromQueryEntry[Option[String]]("filter" -> filter)
      ),
      inPath = Map(
        "roomId"  -> roomId,
        "eventId" -> eventId
      ),
      inHeader = Map.empty
    )

}
