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

sealed trait MatrixError extends Throwable {
  val errcode: String
  val error: Option[String]

}

// as per https://spec.matrix.org/unstable/client-server-api/#standard-error-response
object KnownMatrixErrors {
  //fallback value
  case class Other(errcode: String, error: Option[String]) extends MatrixError

  case class Forbidden(error: Option[String]) extends MatrixError {
    val errcode: String = "M_FORBIDDEN"
  }
  case class UnknownToken(error: Option[String]) extends MatrixError {
    val errcode: String = "M_UNKNOWN_TOKEN"
  }
  case class MissingToken(error: Option[String]) extends MatrixError {
    val errcode: String = "M_MISSING_TOKEN"
  }
  case class BadJson(error: Option[String]) extends MatrixError {
    val errcode: String = "M_BAD_JSON"
  }
  case class NotJson(error: Option[String]) extends MatrixError {
    val errcode: String = "M_NOT_JSON"
  }
  case class NotFound(error: Option[String]) extends MatrixError {
    val errcode: String = "M_NOT_FOUND"
  }
  case class LimitExceeded(error: Option[String]) extends MatrixError {
    val errcode: String = "M_LIMIT_EXCEEDED"
  }
  case class Unrecognized(error: Option[String]) extends MatrixError {
    val errcode: String = "M_UNRECOGNIZED"
  }
  case class Unknown(error: Option[String]) extends MatrixError {
    val errcode: String = "M_UNKNOWN"
  }
  case class Unauthorized(error: Option[String]) extends MatrixError {
    val errcode: String = "M_UNAUTHORIZED"
  }
  case class UserDeactivated(error: Option[String]) extends MatrixError {
    val errcode: String = "M_USER_DEACTIVATED"
  }
  case class UserInUse(error: Option[String]) extends MatrixError {
    val errcode: String = "M_USER_IN_USE"
  }
  case class InvalidUsername(error: Option[String]) extends MatrixError {
    val errcode: String = "M_INVALID_USERNAME"
  }
  case class RoomInUse(error: Option[String]) extends MatrixError {
    val errcode: String = "M_ROOM_IN_USE"
  }
  case class InvalidRoomState(error: Option[String]) extends MatrixError {
    val errcode: String = "M_INVALID_ROOM_STATE"
  }
  case class ThreepidInUse(error: Option[String]) extends MatrixError {
    val errcode: String = "M_THREEPID_IN_USE"
  }
  case class ThreepidNotFound(error: Option[String]) extends MatrixError {
    val errcode: String = "M_THREEPID_NOT_FOUND"
  }
  case class ThreepidAuthFailed(error: Option[String]) extends MatrixError {
    val errcode: String = "M_THREEPID_AUTH_FAILED"
  }
  case class ThreepidDenied(error: Option[String]) extends MatrixError {
    val errcode: String = "M_THREEPID_DENIED"
  }
  case class ServerNotTrusted(error: Option[String]) extends MatrixError {
    val errcode: String = "M_SERVER_NOT_TRUSTED"
  }
  case class UnsupportedRoomVersion(error: Option[String]) extends MatrixError {
    val errcode: String = "M_UNSUPPORTED_ROOM_VERSION"
  }
  case class IncompatibleRoomVersion(error: Option[String]) extends MatrixError {
    val errcode: String = "M_INCOMPATIBLE_ROOM_VERSION"
  }
  case class BadState(error: Option[String]) extends MatrixError {
    val errcode: String = "M_BAD_STATE"
  }
  case class GuestAccessForbidden(error: Option[String]) extends MatrixError {
    val errcode: String = "M_GUEST_ACCESS_FORBIDDEN"
  }
  case class CaptchaNeeded(error: Option[String]) extends MatrixError {
    val errcode: String = "M_CAPTCHA_NEEDED"
  }
  case class CaptchaInvalid(error: Option[String]) extends MatrixError {
    val errcode: String = "M_CAPTCHA_INVALID"
  }
  case class MissingParam(error: Option[String]) extends MatrixError {
    val errcode: String = "M_MISSING_PARAM"
  }
  case class InvalidParam(error: Option[String]) extends MatrixError {
    val errcode: String = "M_INVALID_PARAM"
  }
  case class TooLarge(error: Option[String]) extends MatrixError {
    val errcode: String = "M_TOO_LARGE"
  }
  case class Exclusive(error: Option[String]) extends MatrixError {
    val errcode: String = "M_EXCLUSIVE"
  }
  case class ResourceLimitExceeded(error: Option[String]) extends MatrixError {
    val errcode: String = "M_RESOURCE_LIMIT_EXCEEDED"
  }
  case class CannotLeaveServerNoticeRoom(error: Option[String]) extends MatrixError {
    val errcode: String = "M_CANNOT_LEAVE_SERVER_NOTICE_ROOM"
  }

}
