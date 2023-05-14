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
import de.ship.matrixscala.model.clientserver.ToDeviceData._
import de.ship.matrixscala.json.clientserver.ToDeviceJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/to_device.yaml"
  */
object ToDeviceApi {

  /**
    * <h1>sendToDevice</h1>
    *
    * This endpoint is used to send send-to-device events to a set of
    * client devices.
    *
    * @param eventType The type of event to send.
    *
    * @param txnId The [transaction ID](/client-server-api/#transaction-identifiers) for this event. Clients should generate an
    * ID unique across requests with the same access token; it will be
    * used by the server to ensure idempotency of requests.
    */
  def sendToDevice(
      body: SendToDevice.Body,
      eventType: String,
      txnId: String
  ): ApiRequest[AccessTokenAuthentication, SendToDevice.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/sendToDevice/{eventType}/{txnId}",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[SendToDevice.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "eventType" -> eventType,
        "txnId"     -> txnId
      ),
      inHeader = Map.empty
    )

}
