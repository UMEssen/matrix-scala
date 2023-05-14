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

package de.ship.matrixscala.model.clientserver

import de.ship.matrixscala.model.Definitions

import de.ship.matrixscala.core._

import spray.json.{JsObject, JsValue}

object ThirdPartyMembershipData {

  object InviteBy3PID {

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/third_party_membership.yaml"
      */
    case class Body(
        id_server: String,
        id_access_token: String,
        medium: String,
        address: String
    )

  }

}