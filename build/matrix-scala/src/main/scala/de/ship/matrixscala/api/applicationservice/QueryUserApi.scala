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

package de.ship.matrixscala.api.applicationservice

import de.ship.matrixscala.model.Definitions
import de.ship.matrixscala.model.applicationservice.QueryUserData._
import de.ship.matrixscala.json.applicationservice.QueryUserJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/application-service/query_user.yaml"
  */
object QueryUserApi {

  /**
    * <h1>queryUserById</h1>
    *
    * This endpoint is invoked by the homeserver on an application service to query
    * the existence of a given user ID. The homeserver will only query user IDs
    * inside the application service's `users` namespace. The homeserver will
    * send this request when it receives an event for an unknown user ID in
    * the application service's namespace, such as a room invite.
    *
    * @param userId The user ID being queried.
    */
  def queryUserById(
      userId: String
  ): ApiRequest[HomeserverAccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/app/v1/users/{userId}",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "userId" -> userId
      ),
      inHeader = Map.empty
    )

}
