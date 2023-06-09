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

package de.ship.matrixscala.api.clientserver

import de.ship.matrixscala.model.Definitions
import de.ship.matrixscala.model.clientserver.CreateRoomData._
import de.ship.matrixscala.json.clientserver.CreateRoomJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/create_room.yaml"
  */
object CreateRoomApi {

  /**
    * <h1>createRoom</h1>
    *
    * Create a new room with various configuration options.
    *
    * The server MUST apply the normal state resolution rules when creating
    * the new room, including checking power levels for each event. It MUST
    * apply the events implied by the request in the following order:
    *
    * 1. The `m.room.create` event itself. Must be the first event in the
    *   room.
    *
    * 2. An `m.room.member` event for the creator to join the room. This is
    *   needed so the remaining events can be sent.
    *
    * 3. A default `m.room.power_levels` event, giving the room creator
    *   (and not other members) permission to send state events. Overridden
    *   by the `power_level_content_override` parameter.
    *
    * 4. An `m.room.canonical_alias` event if `room_alias_name` is given.
    *
    * 5. Events set by the `preset`. Currently these are the `m.room.join_rules`,
    *   `m.room.history_visibility`, and `m.room.guest_access` state events.
    *
    * 6. Events listed in `initial_state`, in the order that they are
    *   listed.
    *
    * 7. Events implied by `name` and `topic` (`m.room.name` and `m.room.topic`
    *   state events).
    *
    * 8. Invite events implied by `invite` and `invite_3pid` (`m.room.member` with
    *   `membership: invite` and `m.room.third_party_invite`).
    *
    * The available presets do the following with respect to room state:
    *
    * | Preset                 | `join_rules` | `history_visibility` | `guest_access` | Other |
    * |------------------------|--------------|----------------------|----------------|-------|
    * | `private_chat`         | `invite`     | `shared`             | `can_join`     |       |
    * | `trusted_private_chat` | `invite`     | `shared`             | `can_join`     | All invitees are given the same power level as the room creator. |
    * | `public_chat`          | `public`     | `shared`             | `forbidden`    |       |
    *
    * The server will create a `m.room.create` event in the room with the
    * requesting user as the creator, alongside other keys provided in the
    * `creation_content`.
    */
  def createRoom(
      body: CreateRoom.Body
  ): ApiRequest[AccessTokenAuthentication, CreateRoom.Body, CreateRoom.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/createRoom",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[CreateRoom.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[CreateRoom.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

}
