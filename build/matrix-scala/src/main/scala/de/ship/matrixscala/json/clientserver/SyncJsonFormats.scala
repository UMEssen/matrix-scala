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

package de.ship.matrixscala.json.clientserver

import de.ship.matrixscala.core._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.model.Definitions
import de.ship.matrixscala.model.clientserver.SyncData._

import de.ship.matrixscala.json.core.CoreJsonFormats._
import spray.json._
import DefaultJsonProtocol._

object SyncJsonFormats {

  implicit val SyncOKRoomsJoinedRoomRoomSummaryFormat
      : RootJsonFormat[Sync.OK.Rooms.JoinedRoom.RoomSummary] = jsonFormat3(
    Sync.OK.Rooms.JoinedRoom.RoomSummary.apply
  )

  implicit val SyncOKRoomsJoinedRoomUnreadNotificationCountsFormat
      : RootJsonFormat[Sync.OK.Rooms.JoinedRoom.UnreadNotificationCounts] = jsonFormat2(
    Sync.OK.Rooms.JoinedRoom.UnreadNotificationCounts.apply
  )

  implicit val SyncOKRoomsJoinedRoomThreadNotificationCountsFormat
      : RootJsonFormat[Sync.OK.Rooms.JoinedRoom.ThreadNotificationCounts] = jsonFormat2(
    Sync.OK.Rooms.JoinedRoom.ThreadNotificationCounts.apply
  )

  implicit val SyncOKRoomsJoinedRoomFormat: RootJsonFormat[Sync.OK.Rooms.JoinedRoom] = jsonFormat7(
    Sync.OK.Rooms.JoinedRoom.apply
  )

  implicit val SyncOKRoomsInvitedRoomInviteStateFormat
      : RootJsonFormat[Sync.OK.Rooms.InvitedRoom.InviteState] = jsonFormat1(
    Sync.OK.Rooms.InvitedRoom.InviteState.apply
  )

  implicit val SyncOKRoomsInvitedRoomFormat: RootJsonFormat[Sync.OK.Rooms.InvitedRoom] =
    jsonFormat1(Sync.OK.Rooms.InvitedRoom.apply)

  implicit val SyncOKRoomsKnockedRoomKnockStateFormat
      : RootJsonFormat[Sync.OK.Rooms.KnockedRoom.KnockState] = jsonFormat1(
    Sync.OK.Rooms.KnockedRoom.KnockState.apply
  )

  implicit val SyncOKRoomsKnockedRoomFormat: RootJsonFormat[Sync.OK.Rooms.KnockedRoom] =
    jsonFormat1(Sync.OK.Rooms.KnockedRoom.apply)

  implicit val SyncOKRoomsLeftRoomFormat: RootJsonFormat[Sync.OK.Rooms.LeftRoom] = jsonFormat3(
    Sync.OK.Rooms.LeftRoom.apply
  )

  implicit val SyncOKRoomsFormat: RootJsonFormat[Sync.OK.Rooms] = jsonFormat4(Sync.OK.Rooms.apply)

  implicit val SyncOKFormat: RootJsonFormat[Sync.OK] = jsonFormat7(Sync.OK.apply)

}
