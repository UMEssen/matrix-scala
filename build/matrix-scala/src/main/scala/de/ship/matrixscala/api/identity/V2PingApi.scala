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

package de.ship.matrixscala.api.identity

import de.ship.matrixscala.model.Definitions
import de.ship.matrixscala.model.identity.V2PingData._
import de.ship.matrixscala.json.identity.V2PingJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/identity/v2_ping.yaml"
  */
object V2PingApi {

  /**
    * <h1>pingV2</h1>
    *
    * Checks that an identity server is available at this API endpoint.
    *
    * To discover that an identity server is available at a specific URL,
    * this endpoint can be queried and will return an empty object.
    *
    * This is primarily used for auto-discovery and health check purposes
    * by entities acting as a client for the identity server.
    */
  def pingV2(
  ): ApiRequest[NoAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

}