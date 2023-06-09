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
import de.ship.matrixscala.model.identity.V2PhoneAssociationsData._
import de.ship.matrixscala.json.identity.V2PhoneAssociationsJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/identity/v2_phone_associations.yaml"
  */
object V2PhoneAssociationsApi {

  /**
    * <h1>msisdnRequestTokenV2</h1>
    *
    * Create a session for validating a phone number.
    *
    * The identity server will send an SMS message containing a token. If
    * that token is presented to the identity server in the future, it
    * indicates that that user was able to read the SMS for that phone
    * number, and so we validate ownership of the phone number.
    *
    * Note that homeservers offer APIs that proxy this API, adding
    * additional behaviour on top, for example,
    * `/register/msisdn/requestToken` is designed specifically for use when
    * registering an account and therefore will inform the user if the phone
    * number given is already registered on the server.
    *
    * Note: for backwards compatibility with previous drafts of this
    * specification, the parameters may also be specified as
    * `application/x-form-www-urlencoded` data. However, this usage is
    * deprecated.
    */
  def msisdnRequestTokenV2(
      body: JsObject
  ): ApiRequest[AccessTokenAuthentication, JsObject, Definitions.Sid] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2/validate/msisdn/requestToken",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Definitions.Sid]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>msisdnSubmitTokenGetV2</h1>
    *
    * Validate ownership of a phone number.
    *
    * If the three parameters are consistent with a set generated by a
    * `requestToken` call, ownership of the phone number address is
    * considered to have been validated. This does not publish any
    * information publicly, or associate the phone number with any Matrix
    * user ID. Specifically, calls to `/lookup` will not show a binding.
    *
    * Note that, in contrast with the POST version, this endpoint will be
    * used by end-users, and so the response should be human-readable.
    *
    * @param sid The session ID, generated by the `requestToken` call.
    *
    * @param client_secret The client secret that was supplied to the `requestToken` call.
    *
    * @param token The token generated by the `requestToken` call and sent to the user.
    */
  def msisdnSubmitTokenGetV2(
      sid: String,
      client_secret: String,
      token: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2/validate/msisdn/submitToken",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = buildQuery(
        fromQueryEntry[String]("sid"           -> sid),
        fromQueryEntry[String]("client_secret" -> client_secret),
        fromQueryEntry[String]("token"         -> token)
      ),
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>msisdnSubmitTokenPostV2</h1>
    *
    * Validate ownership of a phone number.
    *
    * If the three parameters are consistent with a set generated by a
    * `requestToken` call, ownership of the phone number is considered to
    * have been validated. This does not publish any information publicly, or
    * associate the phone number address with any Matrix user
    * ID. Specifically, calls to `/lookup` will not show a binding.
    *
    * The identity server is free to match the token case-insensitively, or
    * carry out other mapping operations such as unicode
    * normalisation. Whether to do so is an implementation detail for the
    * identity server. Clients must always pass on the token without
    * modification.
    *
    * Note: for backwards compatibility with previous drafts of this
    * specification, the parameters may also be specified as
    * `application/x-form-www-urlencoded` data. However, this usage is
    * deprecated.
    */
  def msisdnSubmitTokenPostV2(
      body: MsisdnSubmitTokenPostV2.Body
  ): ApiRequest[
    AccessTokenAuthentication,
    MsisdnSubmitTokenPostV2.Body,
    MsisdnSubmitTokenPostV2.OK
  ] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2/validate/msisdn/submitToken",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[MsisdnSubmitTokenPostV2.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[MsisdnSubmitTokenPostV2.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

}
