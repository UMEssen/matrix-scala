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
import de.ship.matrixscala.model.clientserver.RefreshData._
import de.ship.matrixscala.json.clientserver.RefreshJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/refresh.yaml"
  */
object RefreshApi {

  /**
    * <h1>refresh</h1>
    *
    * Refresh an access token. Clients should use the returned access token
    * when making subsequent API calls, and store the returned refresh token
    * (if given) in order to refresh the new access token when necessary.
    *
    * After an access token has been refreshed, a server can choose to
    * invalidate the old access token immediately, or can choose not to, for
    * example if the access token would expire soon anyways. Clients should
    * not make any assumptions about the old access token still being valid,
    * and should use the newly provided access token instead.
    *
    * The old refresh token remains valid until the new access token or refresh token
    * is used, at which point the old refresh token is revoked.
    *
    * Note that this endpoint does not require authentication via an
    * access token. Authentication is provided via the refresh token.
    *
    * Application Service identity assertion is disabled for this endpoint.
    */
  def refresh(
      body: Refresh.Body
  ): ApiRequest[NoAuthentication, Refresh.Body, Refresh.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/refresh",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[Refresh.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Refresh.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

}
