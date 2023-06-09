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
import de.ship.matrixscala.model.clientserver.CapabilitiesData._

import de.ship.matrixscala.json.core.CoreJsonFormats._
import spray.json._
import DefaultJsonProtocol._

object CapabilitiesJsonFormats {

  implicit val GetCapabilitiesOKCapabilitiesChangePasswordCapabilityFormat
      : RootJsonFormat[GetCapabilities.OK.Capabilities.ChangePasswordCapability] = jsonFormat1(
    GetCapabilities.OK.Capabilities.ChangePasswordCapability.apply
  )

  implicit val GetCapabilitiesOKCapabilitiesRoomVersionsCapabilityRoomVersionStabilityFormat
      : RootJsonFormat[
        GetCapabilities.OK.Capabilities.RoomVersionsCapability.RoomVersionStability.Value
      ] = new EnumJsonConverter(
    GetCapabilities.OK.Capabilities.RoomVersionsCapability.RoomVersionStability
  )

  implicit val GetCapabilitiesOKCapabilitiesRoomVersionsCapabilityFormat
      : RootJsonFormat[GetCapabilities.OK.Capabilities.RoomVersionsCapability] = jsonFormat2(
    GetCapabilities.OK.Capabilities.RoomVersionsCapability.apply
  )

  implicit val GetCapabilitiesOKCapabilitiesFormat
      : RootJsonFormat[GetCapabilities.OK.Capabilities] = jsonFormat2(
    GetCapabilities.OK.Capabilities.apply
  )

  implicit val GetCapabilitiesOKFormat: RootJsonFormat[GetCapabilities.OK] = jsonFormat1(
    GetCapabilities.OK.apply
  )

}
