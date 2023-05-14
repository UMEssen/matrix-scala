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
import de.ship.matrixscala.model.identity.V2StoreInviteData._
import de.ship.matrixscala.json.identity.V2StoreInviteJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/identity/v2_store_invite.yaml"
  */
object V2StoreInviteApi {

  /**
    * <h1>storeInviteV2</h1>
    *
    * Store pending invitations to a user's 3pid.
    *
    * In addition to the request parameters specified below, an arbitrary
    * number of other parameters may also be specified. These may be used in
    * the invite message generation described below.
    *
    * The service will generate a random token and an ephemeral key used for
    * accepting the invite.
    *
    * The service also generates a `display_name` for the inviter, which is
    * a redacted version of `address` which does not leak the full contents
    * of the `address`.
    *
    * The service records persistently all of the above information.
    *
    * It also generates an email containing all of this data, sent to the
    * `address` parameter, notifying them of the invitation. The email should
    * reference the `inviter_name`, `room_name`, `room_avatar`, and `room_type`
    * (if present) from the request here.
    *
    * Also, the generated ephemeral public key will be listed as valid on
    * requests to `/_matrix/identity/v2/pubkey/ephemeral/isvalid`.
    *
    * Currently, invites may only be issued for 3pids of the `email` medium.
    *
    * Optional fields in the request should be populated to the best of the
    * server's ability. Identity servers may use these variables when notifying
    * the `address` of the pending invite for display purposes.
    */
  def storeInviteV2(
      body: StoreInviteV2.Body
  ): ApiRequest[AccessTokenAuthentication, StoreInviteV2.Body, StoreInviteV2.OK] =
    ApiRequest(
      endpoint = "/_matrix/identity/v2/store-invite",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[StoreInviteV2.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[StoreInviteV2.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

}