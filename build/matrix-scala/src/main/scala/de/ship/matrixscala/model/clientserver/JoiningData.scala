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

object JoiningData {

  object JoinRoomById {

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/joining.yaml"
      */
    case class OK(
        room_id: String
    )

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/joining.yaml"
      */
    case class Body(
        third_party_signed: Option[Definitions.ThirdPartySigned] = None,
        reason: Option[String] = None
    )

  }

  object JoinRoom {

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/joining.yaml"
      */
    case class OK(
        room_id: String
    )

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/joining.yaml"
      */
    case class Body(
        third_party_signed: Option[Definitions.ThirdPartySigned] = None,
        reason: Option[String] = None
    )

  }

}