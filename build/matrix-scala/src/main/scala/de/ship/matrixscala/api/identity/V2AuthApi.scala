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
import de.ship.matrixscala.model.identity.V2AuthData._
import de.ship.matrixscala.json.identity.V2AuthJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/identity/v2_auth.yaml"
  */
object V2AuthApi {

  /**
    * <h1>registerAccount</h1>
    *
    * Exchanges an OpenID token from the homeserver for an access token to
    * access the identity server. The request body is the same as the values
    * returned by `/openid/request_token` in the Client-Server API.
    */
  def registerAccount(
      body: JsObject
  ): ApiRequest[NoAuthentication, JsObject, RegisterAccount.OK] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2/account/register",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[RegisterAccount.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>getAccount</h1>
    *
    * Gets information about what user owns the access token used in the request.
    */
  def getAccount(
  ): ApiRequest[AccessTokenAuthentication, JsObject, GetAccount.OK] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2/account",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[GetAccount.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>logout</h1>
    *
    * Logs out the access token, preventing it from being used to authenticate
    * future requests to the server.
    */
  def logout(
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2/account/logout",
      httpMethod = HttpMethods.POST,
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
