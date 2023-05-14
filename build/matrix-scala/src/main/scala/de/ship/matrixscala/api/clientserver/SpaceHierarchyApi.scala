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
import de.ship.matrixscala.model.clientserver.SpaceHierarchyData._
import de.ship.matrixscala.json.clientserver.SpaceHierarchyJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/space_hierarchy.yaml"
  */
object SpaceHierarchyApi {

  /**
    * <h1>getSpaceHierarchy</h1>
    *
    * Paginates over the space tree in a depth-first manner to locate child rooms of a given space.
    *
    * Where a child room is unknown to the local server, federation is used to fill in the details.
    * The servers listed in the `via` array should be contacted to attempt to fill in missing rooms.
    *
    * Only [`m.space.child`](#mspacechild) state events of the room are considered. Invalid child
    * rooms and parent events are not covered by this endpoint.
    *
    * @param roomId The room ID of the space to get a hierarchy for.
    *
    * @param suggested_only Optional (default `false`) flag to indicate whether or not the server should only consider
    * suggested rooms. Suggested rooms are annotated in their [`m.space.child`](#mspacechild) event
    * contents.
    *
    * @param limit Optional limit for the maximum number of rooms to include per response. Must be an integer
    * greater than zero.
    *
    * Servers should apply a default value, and impose a maximum value to avoid resource exhaustion.
    *
    * @param max_depth Optional limit for how far to go into the space. Must be a non-negative integer.
    *
    * When reached, no further child rooms will be returned.
    *
    * Servers should apply a default value, and impose a maximum value to avoid resource exhaustion.
    *
    * @param from A pagination token from a previous result. If specified, `max_depth` and `suggested_only` cannot
    * be changed from the first request.
    */
  def getSpaceHierarchy(
      roomId: String,
      suggested_only: Option[Boolean] = None,
      limit: Option[Int] = None,
      max_depth: Option[Int] = None,
      from: Option[String] = None
  ): ApiRequest[AccessTokenAuthentication, JsObject, GetSpaceHierarchy.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v1/rooms/{roomId}/hierarchy",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[GetSpaceHierarchy.OK]])
      ),
      inQuery = buildQuery(
        fromQueryEntry[Option[Boolean]]("suggested_only" -> suggested_only),
        fromQueryEntry[Option[Int]]("limit"              -> limit),
        fromQueryEntry[Option[Int]]("max_depth"          -> max_depth),
        fromQueryEntry[Option[String]]("from"            -> from)
      ),
      inPath = Map(
        "roomId" -> roomId
      ),
      inHeader = Map.empty
    )

}