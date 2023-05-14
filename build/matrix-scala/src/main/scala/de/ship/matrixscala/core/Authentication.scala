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
package de.ship.matrixscala.core

import akka.http.scaladsl.model.HttpHeader.ParsingResult
import akka.http.scaladsl.model.{HttpHeader, HttpRequest}

sealed trait Authentication {
  private[core] def attachAuthentication(request: HttpRequest): HttpRequest
}
case class NoAuthentication() extends Authentication {
  override def attachAuthentication(request: HttpRequest): HttpRequest =
    identity(request) // do nothing
}
case class AccessTokenAuthentication(authToken: String) extends Authentication {
  def authHeader: HttpHeader =
    HttpHeader.parse("Authorization", s"Bearer $authToken") match {
      case ParsingResult.Ok(header, _) => header
      case ParsingResult.Error(error) =>
        throw new IllegalArgumentException(s"failed to set authentication token: $error")
    }
  override def attachAuthentication(request: HttpRequest): HttpRequest =
    request.withHeaders(request.headers :+ authHeader)
}

// this is a big ol TODO
trait HomeserverAccessTokenAuthentication extends Authentication
