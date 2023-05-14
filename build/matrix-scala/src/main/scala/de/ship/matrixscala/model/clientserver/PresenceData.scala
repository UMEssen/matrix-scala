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

object PresenceData {

  object GetPresence {

    object OK {

      object PresenceEnum extends Enumeration {

        val `online`: Value = Value("online")

        val `offline`: Value = Value("offline")

        val `unavailable`: Value = Value("unavailable")

      }

    }

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/presence.yaml"
      */
    case class OK(
        presence: GetPresence.OK.PresenceEnum.Value,
        last_active_ago: Option[Int] = None,
        status_msg: Option[Option[String]] = None,
        currently_active: Option[Boolean] = None
    )

  }

  object SetPresence {

    object Body {

      object PresenceEnum extends Enumeration {

        val `online`: Value = Value("online")

        val `offline`: Value = Value("offline")

        val `unavailable`: Value = Value("unavailable")

      }

    }

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/presence.yaml"
      */
    case class Body(
        presence: SetPresence.Body.PresenceEnum.Value,
        status_msg: Option[String] = None
    )

  }

}