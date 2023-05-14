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
import de.ship.matrixscala.model.clientserver.KickingData._
import de.ship.matrixscala.json.clientserver.KickingJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/kicking.yaml"
  */
object KickingApi {

  /**
    * <h1>kick</h1>
    *
    * Kick a user from the room.
    *
    * The caller must have the required power level in order to perform this operation.
    *
    * Kicking a user adjusts the target member's membership state to be `leave` with an
    * optional `reason`. Like with other membership changes, a user can directly adjust
    * the target member's state by making a request to `/rooms/<room id>/state/m.room.member/<user id>`.
    *
    * @param roomId The room identifier (not alias) from which the user should be kicked.
    */
  def kick(
      body: Kick.Body,
      roomId: String
  ): ApiRequest[AccessTokenAuthentication, Kick.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/rooms/{roomId}/kick",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[Kick.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "roomId" -> roomId
      ),
      inHeader = Map.empty
    )

}