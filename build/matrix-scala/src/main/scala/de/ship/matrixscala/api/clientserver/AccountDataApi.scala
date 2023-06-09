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
import de.ship.matrixscala.model.clientserver.AccountDataData._
import de.ship.matrixscala.json.clientserver.AccountDataJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/account-data.yaml"
  */
object AccountDataApi {

  /**
    * <h1>getAccountData</h1>
    *
    * Get some account data for the client. This config is only visible to the user
    * that set the account data.
    *
    * @param userId The ID of the user to get account data for. The access token must be
    * authorized to make requests for this user ID.
    *
    * @param type The event type of the account data to get. Custom types should be
    * namespaced to avoid clashes.
    */
  def getAccountData(
      userId: String,
      `type`: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/user/{userId}/account_data/{type}",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId,
        "type"   -> `type`
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>setAccountData</h1>
    *
    * Set some account data for the client. This config is only visible to the user
    * that set the account data. The config will be available to clients through the
    * top-level `account_data` field in the homeserver response to
    * [/sync](#get_matrixclientv3sync).
    *
    * @param userId The ID of the user to set account data for. The access token must be
    * authorized to make requests for this user ID.
    *
    * @param type The event type of the account data to set. Custom types should be
    * namespaced to avoid clashes.
    */
  def setAccountData(
      body: JsObject,
      userId: String,
      `type`: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/user/{userId}/account_data/{type}",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId,
        "type"   -> `type`
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>getAccountDataPerRoom</h1>
    *
    * Get some account data for the client on a given room. This config is only
    * visible to the user that set the account data.
    *
    * @param userId The ID of the user to get account data for. The access token must be
    * authorized to make requests for this user ID.
    *
    * @param roomId The ID of the room to get account data for.
    *
    * @param type The event type of the account data to get. Custom types should be
    * namespaced to avoid clashes.
    */
  def getAccountDataPerRoom(
      userId: String,
      roomId: String,
      `type`: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/user/{userId}/rooms/{roomId}/account_data/{type}",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId,
        "roomId" -> roomId,
        "type"   -> `type`
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>setAccountDataPerRoom</h1>
    *
    * Set some account data for the client on a given room. This config is only
    * visible to the user that set the account data. The config will be delivered to
    * clients in the per-room entries via [/sync](#get_matrixclientv3sync).
    *
    * @param userId The ID of the user to set account data for. The access token must be
    * authorized to make requests for this user ID.
    *
    * @param roomId The ID of the room to set account data on.
    *
    * @param type The event type of the account data to set. Custom types should be
    * namespaced to avoid clashes.
    */
  def setAccountDataPerRoom(
      body: JsObject,
      userId: String,
      roomId: String,
      `type`: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/user/{userId}/rooms/{roomId}/account_data/{type}",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId,
        "roomId" -> roomId,
        "type"   -> `type`
      ),
      inHeader = Map.empty
    )

}
