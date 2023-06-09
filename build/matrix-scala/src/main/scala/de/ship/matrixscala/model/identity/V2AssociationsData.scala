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

package de.ship.matrixscala.model.identity

import de.ship.matrixscala.model.Definitions

import de.ship.matrixscala.core._

import spray.json.{JsObject, JsValue}

object V2AssociationsData {

  object GetValidated3pidV2 {

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/identity/v2_associations.yaml"
      */
    case class OK(
        medium: String,
        address: String,
        validated_at: Long
    )

  }

  object BindV2 {

    object OK {

      /**
        * <h1>Signatures</h1>
        *
        *  The signatures of the verifying identity servers which show that the
        * association should be trusted, if you trust the verifying identity
        * services.
        *
        * @see "defined in ./build/matrix-spec/data/api/identity/v2_associations.yaml"
        */
      case class Signatures(
      )

    }

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/identity/v2_associations.yaml"
      */
    case class OK(
        address: String,
        medium: String,
        mxid: String,
        not_before: Long,
        not_after: Long,
        ts: Long,
        signatures: BindV2.OK.Signatures
    )

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/identity/v2_associations.yaml"
      */
    case class Body(
        sid: String,
        client_secret: String,
        mxid: String
    )

  }

  object UnbindV2 {

    object Body {

      /**
        * <h1>PID</h1>
        *
        *  The 3PID to remove. Must match the 3PID used to generate the session
        * if using `sid` and `client_secret` to authenticate this request.
        *
        * @see "defined in ./build/matrix-spec/data/api/identity/v2_associations.yaml"
        */
      case class PID(
          medium: String,
          address: String
      )

    }

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/identity/v2_associations.yaml"
      */
    case class Body(
        sid: Option[String] = None,
        client_secret: Option[String] = None,
        mxid: String,
        threepid: UnbindV2.Body.PID
    )

  }

}
