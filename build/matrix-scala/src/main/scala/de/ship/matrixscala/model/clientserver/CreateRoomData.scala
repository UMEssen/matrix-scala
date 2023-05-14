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

object CreateRoomData {

  object CreateRoom {

    /**
      * <h1>OK</h1>
      *
      *  Information about the newly created room.
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/create_room.yaml"
      */
    case class OK(
        room_id: String
    )

    object Body {

      object VisibilityEnum extends Enumeration {

        val `public`: Value = Value("public")

        val `private`: Value = Value("private")

      }

      /**
        * <h1>Invite3pid</h1>
        *
        * @see "defined in ./build/matrix-spec/data/api/client-server/create_room.yaml"
        */
      case class Invite3pid(
          id_server: String,
          id_access_token: String,
          medium: String,
          address: String
      )

      /**
        * <h1>StateEvent</h1>
        *
        * @see "defined in ./build/matrix-spec/data/api/client-server/create_room.yaml"
        */
      case class StateEvent(
          `type`: String,
          state_key: Option[String] = None,
          content: JsObject
      )

      object PresetEnum extends Enumeration {

        val `private_chat`: Value = Value("private_chat")

        val `public_chat`: Value = Value("public_chat")

        val `trusted_private_chat`: Value = Value("trusted_private_chat")

      }

    }

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/create_room.yaml"
      */
    case class Body(
        visibility: Option[CreateRoom.Body.VisibilityEnum.Value] = None,
        room_alias_name: Option[String] = None,
        name: Option[String] = None,
        topic: Option[String] = None,
        invite: Option[Seq[String]] = None,
        invite_3pid: Option[Seq[CreateRoom.Body.Invite3pid]] = None,
        room_version: Option[String] = None,
        creation_content: Option[JsObject] = None,
        initial_state: Option[Seq[CreateRoom.Body.StateEvent]] = None,
        preset: Option[CreateRoom.Body.PresetEnum.Value] = None,
        is_direct: Option[Boolean] = None,
        power_level_content_override: Option[JsObject] = None
    )

  }

}
