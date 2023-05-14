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
import de.ship.matrixscala.model.clientserver.RoomStateData._
import de.ship.matrixscala.json.clientserver.RoomStateJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/room_state.yaml"
  */
object RoomStateApi {

  /**
    * <h1>setRoomStateWithKey</h1>
    *
    * State events can be sent using this endpoint.  These events will be
    * overwritten if `<room id>`, `<event type>` and `<state key>` all
    * match.
    *
    * Requests to this endpoint **cannot use transaction IDs**
    * like other `PUT` paths because they cannot be differentiated from the
    * `state_key`. Furthermore, `POST` is unsupported on state paths.
    *
    * The body of the request should be the content object of the event; the
    * fields in this object will vary depending on the type of event. See
    * [Room Events](/client-server-api/#room-events) for the `m.` event specification.
    *
    * If the event type being sent is `m.room.canonical_alias` servers
    * SHOULD ensure that any new aliases being listed in the event are valid
    * per their grammar/syntax and that they point to the room ID where the
    * state event is to be sent. Servers do not validate aliases which are
    * being removed or are already present in the state event.
    *
    * @param roomId The room to set the state in
    *
    * @param eventType The type of event to send.
    *
    * @param stateKey The state_key for the state to send. Defaults to the empty string. When
    * an empty string, the trailing slash on this endpoint is optional.
    */
  def setRoomStateWithKey(
      body: JsObject,
      roomId: String,
      eventType: String,
      stateKey: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, SetRoomStateWithKey.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/rooms/{roomId}/state/{eventType}/{stateKey}",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[SetRoomStateWithKey.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "roomId"    -> roomId,
        "eventType" -> eventType,
        "stateKey"  -> stateKey
      ),
      inHeader = Map.empty
    )

}