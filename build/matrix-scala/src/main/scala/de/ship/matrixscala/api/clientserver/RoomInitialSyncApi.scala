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
import de.ship.matrixscala.model.clientserver.RoomInitialSyncData._
import de.ship.matrixscala.json.clientserver.RoomInitialSyncJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/room_initial_sync.yaml"
  */
object RoomInitialSyncApi {

  /**
    * <h1>roomInitialSync</h1>
    *
    * @deprecated
    *
    * Get a copy of the current state and the most recent messages in a room.
    *
    * This endpoint was deprecated in r0 of this specification. There is no
    * direct replacement; the relevant information is returned by the
    * [`/sync`](/client-server-api/#get_matrixclientv3sync) API. See the
    * [migration guide](https://matrix.org/docs/guides/migrating-from-client-server-api-v-1#deprecated-endpoints).
    *
    * @param roomId The room to get the data.
    */
  def roomInitialSync(
      roomId: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, RoomInitialSync.RoomInfo] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/rooms/{roomId}/initialSync",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[RoomInitialSync.RoomInfo]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "roomId" -> roomId
      ),
      inHeader = Map.empty
    )

}
