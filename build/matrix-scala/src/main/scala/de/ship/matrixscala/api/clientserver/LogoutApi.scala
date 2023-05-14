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
import de.ship.matrixscala.model.clientserver.LogoutData._
import de.ship.matrixscala.json.clientserver.LogoutJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/logout.yaml"
  */
object LogoutApi {

  /**
    * <h1>logout</h1>
    *
    * Invalidates an existing access token, so that it can no longer be used for
    * authorization. The device associated with the access token is also deleted.
    * [Device keys](/client-server-api/#device-keys) for the device are deleted alongside the device.
    */
  def logout(
  ): ApiRequest[AccessTokenAuthentication, JsObject, Logout.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/logout",
      httpMethod = HttpMethods.POST,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Logout.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>logout_all</h1>
    *
    * Invalidates all access tokens for a user, so that they can no longer be used for
    * authorization. This includes the access token that made this request. All devices
    * for the user are also deleted. [Device keys](/client-server-api/#device-keys) for the device are
    * deleted alongside the device.
    *
    * This endpoint does not use the [User-Interactive Authentication API](/client-server-api/#user-interactive-authentication-api) because
    * User-Interactive Authentication is designed to protect against attacks where the
    * someone gets hold of a single access token then takes over the account. This
    * endpoint invalidates all access tokens for the user, including the token used in
    * the request, and therefore the attacker is unable to take over the account in
    * this way.
    */
  def logout_all(
  ): ApiRequest[AccessTokenAuthentication, JsObject, LogoutAll.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/logout/all",
      httpMethod = HttpMethods.POST,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[LogoutAll.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

}