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

object PusherData {

  object GetPushers {

    object OK {

      object Pusher {

        /**
          * <h1>PusherData</h1>
          *
          *  A dictionary of information for the pusher implementation
          * itself.
          *
          * @see "defined in ./build/matrix-spec/data/api/client-server/pusher.yaml"
          */
        case class PusherData(
            url: Option[String] = None,
            format: Option[String] = None
        )

      }

      /**
        * <h1>Pusher</h1>
        *
        * @see "defined in ./build/matrix-spec/data/api/client-server/pusher.yaml"
        */
      case class Pusher(
          pushkey: String,
          kind: String,
          app_id: String,
          app_display_name: String,
          device_display_name: String,
          profile_tag: Option[String] = None,
          lang: String,
          data: GetPushers.OK.Pusher.PusherData
      )

    }

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pusher.yaml"
      */
    case class OK(
        pushers: Option[Seq[GetPushers.OK.Pusher]] = None
    )

  }

  object PostPusher {

    object Body {

      /**
        * <h1>PusherData</h1>
        *
        *  Required if `kind` is not `null`. A dictionary of information
        * for the pusher implementation itself. If `kind` is `http`,
        * this should contain `url` which is the URL to use to send
        * notifications to.
        *
        * @see "defined in ./build/matrix-spec/data/api/client-server/pusher.yaml"
        */
      case class PusherData(
          url: Option[String] = None,
          format: Option[String] = None
      )

    }

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pusher.yaml"
      */
    case class Body(
        pushkey: String,
        kind: Option[String] = None,
        app_id: String,
        app_display_name: Option[String] = None,
        device_display_name: Option[String] = None,
        profile_tag: Option[String] = None,
        lang: Option[String] = None,
        data: Option[PostPusher.Body.PusherData] = None,
        append: Option[Boolean] = None
    )

  }

}