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

object RoomInitialSyncData {

  object RoomInitialSync {

    object RoomInfo {

      object MembershipEnum extends Enumeration {

        val `invite`: Value = Value("invite")

        val `join`: Value = Value("join")

        val `leave`: Value = Value("leave")

        val `ban`: Value = Value("ban")

      }

      /**
        * <h1>PaginationChunk</h1>
        *
        *  The pagination chunk for this room.
        *
        * @see "defined in ./build/matrix-spec/data/api/client-server/room_initial_sync.yaml"
        */
      case class PaginationChunk(
          start: Option[String] = None,
          end: String,
          chunk: Seq[Definitions.ClientEvent]
      )

      object VisibilityEnum extends Enumeration {

        val `private`: Value = Value("private")

        val `public`: Value = Value("public")

      }

    }

    /**
      * <h1>RoomInfo</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/room_initial_sync.yaml"
      */
    case class RoomInfo(
        room_id: String,
        membership: Option[RoomInitialSync.RoomInfo.MembershipEnum.Value] = None,
        messages: Option[RoomInitialSync.RoomInfo.PaginationChunk] = None,
        state: Option[Seq[Definitions.ClientEvent]] = None,
        visibility: Option[RoomInitialSync.RoomInfo.VisibilityEnum.Value] = None,
        account_data: Option[Seq[Definitions.Event]] = None
    )

  }

}