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
import de.ship.matrixscala.model.clientserver.TagsData._
import de.ship.matrixscala.json.clientserver.TagsJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/tags.yaml"
  */
object TagsApi {

  /**
    * <h1>getRoomTags</h1>
    *
    * List the tags set by a user on a room.
    *
    * @param userId The id of the user to get tags for. The access token must be
    * authorized to make requests for this user ID.
    *
    * @param roomId The ID of the room to get tags for.
    */
  def getRoomTags(
      userId: String,
      roomId: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, GetRoomTags.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/user/{userId}/rooms/{roomId}/tags",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[GetRoomTags.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId,
        "roomId" -> roomId
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>setRoomTag</h1>
    *
    * Add a tag to the room.
    *
    * @param userId The id of the user to add a tag for. The access token must be
    * authorized to make requests for this user ID.
    *
    * @param roomId The ID of the room to add a tag to.
    *
    * @param tag The tag to add.
    */
  def setRoomTag(
      body: SetRoomTag.Body,
      userId: String,
      roomId: String,
      tag: String
  ): ApiRequest[AccessTokenAuthentication, SetRoomTag.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/user/{userId}/rooms/{roomId}/tags/{tag}",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[SetRoomTag.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId,
        "roomId" -> roomId,
        "tag"    -> tag
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>deleteRoomTag</h1>
    *
    * Remove a tag from the room.
    *
    * @param userId The id of the user to remove a tag for. The access token must be
    * authorized to make requests for this user ID.
    *
    * @param roomId The ID of the room to remove a tag from.
    *
    * @param tag The tag to remove.
    */
  def deleteRoomTag(
      userId: String,
      roomId: String,
      tag: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/user/{userId}/rooms/{roomId}/tags/{tag}",
      httpMethod = HttpMethods.DELETE,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId,
        "roomId" -> roomId,
        "tag"    -> tag
      ),
      inHeader = Map.empty
    )

}
