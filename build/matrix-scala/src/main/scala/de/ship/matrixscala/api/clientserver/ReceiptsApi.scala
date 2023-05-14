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
import de.ship.matrixscala.model.clientserver.ReceiptsData._
import de.ship.matrixscala.json.clientserver.ReceiptsJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/receipts.yaml"
  */
object ReceiptsApi {

  /**
    * <h1>postReceipt</h1>
    *
    * This API updates the marker for the given receipt type to the event ID
    * specified.
    *
    * @param roomId The room in which to send the event.
    *
    * @param receiptType The type of receipt to send. This can also be `m.fully_read` as an
    * alternative to [`/read_markers`](/client-server-api/#post_matrixclientv3roomsroomidread_markers).
    *
    * Note that `m.fully_read` does not appear under `m.receipt`: this endpoint
    * effectively calls `/read_markers` internally when presented with a receipt
    * type of `m.fully_read`.
    *
    * @param eventId The event ID to acknowledge up to.
    */
  def postReceipt(
      body: PostReceipt.Body,
      roomId: String,
      receiptType: String,
      eventId: String
  ): ApiRequest[AccessTokenAuthentication, PostReceipt.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/rooms/{roomId}/receipt/{receiptType}/{eventId}",
      httpMethod = HttpMethods.POST,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[PostReceipt.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "roomId"      -> roomId,
        "receiptType" -> receiptType,
        "eventId"     -> eventId
      ),
      inHeader = Map.empty
    )

}