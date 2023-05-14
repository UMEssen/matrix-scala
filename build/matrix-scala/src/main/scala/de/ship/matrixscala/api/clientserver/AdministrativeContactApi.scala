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
import de.ship.matrixscala.model.clientserver.AdministrativeContactData._
import de.ship.matrixscala.json.clientserver.AdministrativeContactJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/administrative_contact.yaml"
  */
object AdministrativeContactApi {

  /**
    * <h1>getAccount3PIDs</h1>
    *
    * Gets a list of the third-party identifiers that the homeserver has
    * associated with the user's account.
    *
    * This is *not* the same as the list of third-party identifiers bound to
    * the user's Matrix ID in identity servers.
    *
    * Identifiers in this list may be used by the homeserver as, for example,
    * identifiers that it will accept to reset the user's account password.
    */
  def getAccount3PIDs(
  ): ApiRequest[AccessTokenAuthentication, JsObject, GetAccount3PIDs.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[GetAccount3PIDs.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>post3PIDs</h1>
    *
    * @deprecated
    *
    * Adds contact information to the user's account.
    *
    * This endpoint is deprecated in favour of the more specific `/3pid/add`
    * and `/3pid/bind` endpoints.
    *
    * *Note:**
    * Previously this endpoint supported a `bind` parameter. This parameter
    * has been removed, making this endpoint behave as though it was `false`.
    * This results in this endpoint being an equivalent to `/3pid/bind` rather
    * than dual-purpose.
    */
  def post3PIDs(
      body: Post3PIDs.Body
  ): ApiRequest[AccessTokenAuthentication, Post3PIDs.Body, Post3PIDs.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[Post3PIDs.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Post3PIDs.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>add3PID</h1>
    *
    * This API endpoint uses the [User-Interactive Authentication API](/client-server-api/#user-interactive-authentication-api).
    *
    * Adds contact information to the user's account. Homeservers should use 3PIDs added
    * through this endpoint for password resets instead of relying on the identity server.
    *
    * Homeservers should prevent the caller from adding a 3PID to their account if it has
    * already been added to another user's account on the homeserver.
    */
  def add3PID(
      body: Add3PID.Body
  ): ApiRequest[AccessTokenAuthentication, Add3PID.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid/add",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[Add3PID.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>bind3PID</h1>
    *
    * Binds a 3PID to the user's account through the specified identity server.
    *
    * Homeservers should not prevent this request from succeeding if another user
    * has bound the 3PID. Homeservers should simply proxy any errors received by
    * the identity server to the caller.
    *
    * Homeservers should track successful binds so they can be unbound later.
    */
  def bind3PID(
      body: Bind3PID.Body
  ): ApiRequest[AccessTokenAuthentication, Bind3PID.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid/bind",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[Bind3PID.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>delete3pidFromAccount</h1>
    *
    * Removes a third-party identifier from the user's account. This might not
    * cause an unbind of the identifier from the identity server.
    *
    * Unlike other endpoints, this endpoint does not take an `id_access_token`
    * parameter because the homeserver is expected to sign the request to the
    * identity server instead.
    */
  def delete3pidFromAccount(
      body: Delete3pidFromAccount.Body
  ): ApiRequest[AccessTokenAuthentication, Delete3pidFromAccount.Body, Delete3pidFromAccount.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid/delete",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[Delete3pidFromAccount.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Delete3pidFromAccount.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>unbind3pidFromAccount</h1>
    *
    * Removes a user's third-party identifier from the provided identity server
    * without removing it from the homeserver.
    *
    * Unlike other endpoints, this endpoint does not take an `id_access_token`
    * parameter because the homeserver is expected to sign the request to the
    * identity server instead.
    */
  def unbind3pidFromAccount(
      body: Unbind3pidFromAccount.Body
  ): ApiRequest[AccessTokenAuthentication, Unbind3pidFromAccount.Body, Unbind3pidFromAccount.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid/unbind",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[Unbind3pidFromAccount.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Unbind3pidFromAccount.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>requestTokenTo3PIDEmail</h1>
    *
    * The homeserver must check that the given email address is **not**
    * already associated with an account on this homeserver. This API should
    * be used to request validation tokens when adding an email address to an
    * account. This API's parameters and response are identical to that of
    * the [`/register/email/requestToken`](/client-server-api/#post_matrixclientv3registeremailrequesttoken)
    * endpoint. The homeserver should validate
    * the email itself, either by sending a validation email itself or by using
    * a service it has control over.
    */
  def requestTokenTo3PIDEmail(
      body: JsObject
  ): ApiRequest[NoAuthentication, JsObject, Definitions.RequestTokenResponse] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid/email/requestToken",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Definitions.RequestTokenResponse]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>requestTokenTo3PIDMSISDN</h1>
    *
    * The homeserver must check that the given phone number is **not**
    * already associated with an account on this homeserver. This API should
    * be used to request validation tokens when adding a phone number to an
    * account. This API's parameters and response are identical to that of
    * the [`/register/msisdn/requestToken`](/client-server-api/#post_matrixclientv3registermsisdnrequesttoken)
    * endpoint. The homeserver should validate
    * the phone number itself, either by sending a validation message itself or by using
    * a service it has control over.
    */
  def requestTokenTo3PIDMSISDN(
      body: JsObject
  ): ApiRequest[NoAuthentication, JsObject, Definitions.RequestTokenResponse] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/account/3pid/msisdn/requestToken",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Definitions.RequestTokenResponse]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

}
