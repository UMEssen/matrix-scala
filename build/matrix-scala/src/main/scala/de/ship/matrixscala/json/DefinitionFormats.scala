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

package de.ship.matrixscala.json

import de.ship.matrixscala.core._
import de.ship.matrixscala.model.Definitions
import de.ship.matrixscala.json.core.CoreJsonFormats._

import spray.json._
import DefaultJsonProtocol._

object DefinitionFormats {

  implicit val DefinitionsKeyBackupDataLazyFormat: RootJsonFormat[Definitions.KeyBackupData] =
    rootFormat(
      lazyFormat(
        jsonFormat(
          Definitions.KeyBackupData,
          "first_message_index",
          "forwarded_count",
          "is_verified",
          "session_data"
        )
      )
    )

  implicit val DefinitionsRoomKeyBackupLazyFormat: RootJsonFormat[Definitions.RoomKeyBackup] =
    rootFormat(lazyFormat(jsonFormat(Definitions.RoomKeyBackup, "sessions")))

  implicit val DefinitionsPushConditionLazyFormat: RootJsonFormat[Definitions.PushCondition] =
    rootFormat(lazyFormat(jsonFormat(Definitions.PushCondition, "kind", "key", "pattern", "is")))

  implicit val DefinitionsPushRuleLazyFormat: RootJsonFormat[Definitions.PushRule] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.PushRule,
        "actions",
        "default",
        "enabled",
        "rule_id",
        "conditions",
        "pattern"
      )
    )
  )

  implicit val DefinitionsPushRulesetLazyFormat: RootJsonFormat[Definitions.PushRuleset] =
    rootFormat(
      lazyFormat(
        jsonFormat(Definitions.PushRuleset, "content", "override", "room", "sender", "underride")
      )
    )

  implicit val DefinitionsEventFilterLazyFormat: RootJsonFormat[Definitions.EventFilter] =
    rootFormat(
      lazyFormat(
        jsonFormat(Definitions.EventFilter, "limit", "not_senders", "not_types", "senders", "types")
      )
    )

  implicit val DefinitionsRoomEventFilterRoomEventFilterLazyFormat
      : RootJsonFormat[Definitions.RoomEventFilter.RoomEventFilter] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.RoomEventFilter.RoomEventFilter,
        "unread_thread_notifications",
        "lazy_load_members",
        "include_redundant_members",
        "not_rooms",
        "rooms",
        "contains_url"
      )
    )
  )

  implicit val DefinitionsRoomEventFilterLazyFormat: RootJsonFormat[Definitions.RoomEventFilter] =
    rootFormat(
      lazyFormat(
        jsonFormat(
          Definitions.RoomEventFilter.apply,
          "limit",
          "not_senders",
          "not_types",
          "senders",
          "types",
          "unread_thread_notifications",
          "lazy_load_members",
          "include_redundant_members",
          "not_rooms",
          "rooms",
          "contains_url"
        )
      )
    )

  implicit val DefinitionsFilterEventFormatEnumFormat
      : RootJsonFormat[Definitions.Filter.EventFormatEnum.Value] = new EnumJsonConverter(
    Definitions.Filter.EventFormatEnum
  )

  implicit val DefinitionsFilterRoomFilterLazyFormat
      : RootJsonFormat[Definitions.Filter.RoomFilter] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.Filter.RoomFilter,
        "not_rooms",
        "rooms",
        "ephemeral",
        "include_leave",
        "state",
        "timeline",
        "account_data"
      )
    )
  )

  implicit val DefinitionsFilterLazyFormat: RootJsonFormat[Definitions.Filter] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.Filter.apply,
        "event_fields",
        "event_format",
        "presence",
        "account_data",
        "room"
      )
    )
  )

  implicit val DefinitionsClientEventWithoutRoomIDUnsignedDataLazyFormat
      : RootJsonFormat[Definitions.ClientEventWithoutRoomID.UnsignedData] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.ClientEventWithoutRoomID.UnsignedData,
        "age",
        "redacted_because",
        "transaction_id",
        "prev_content"
      )
    )
  )

  implicit val DefinitionsClientEventWithoutRoomIDLazyFormat
      : RootJsonFormat[Definitions.ClientEventWithoutRoomID] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.ClientEventWithoutRoomID.apply,
        "event_id",
        "type",
        "state_key",
        "sender",
        "origin_server_ts",
        "content",
        "unsigned"
      )
    )
  )

  implicit val DefinitionsThirdPartySignedLazyFormat: RootJsonFormat[Definitions.ThirdPartySigned] =
    rootFormat(
      lazyFormat(jsonFormat(Definitions.ThirdPartySigned, "sender", "mxid", "token", "signatures"))
    )

  implicit val DefinitionsAuthenticationDataLazyFormat
      : RootJsonFormat[Definitions.AuthenticationData] = rootFormat(
    lazyFormat(jsonFormat(Definitions.AuthenticationData, "type", "session"))
  )

  implicit val DefinitionsRequestTokenResponseLazyFormat
      : RootJsonFormat[Definitions.RequestTokenResponse] = rootFormat(
    lazyFormat(jsonFormat(Definitions.RequestTokenResponse, "sid", "submit_url"))
  )

  implicit val DefinitionsStateEventBatchLazyFormat: RootJsonFormat[Definitions.StateEventBatch] =
    rootFormat(lazyFormat(jsonFormat(Definitions.StateEventBatch, "events")))

  implicit val DefinitionsTimelineBatchLazyFormat: RootJsonFormat[Definitions.TimelineBatch] =
    rootFormat(lazyFormat(jsonFormat(Definitions.TimelineBatch, "limited", "prev_batch", "events")))

  implicit val DefinitionsEventLazyFormat: RootJsonFormat[Definitions.Event] = rootFormat(
    lazyFormat(jsonFormat(Definitions.Event, "content", "type"))
  )

  implicit val DefinitionsEventBatchLazyFormat: RootJsonFormat[Definitions.EventBatch] = rootFormat(
    lazyFormat(jsonFormat(Definitions.EventBatch, "events"))
  )

  implicit val DefinitionsStrippedStateEventLazyFormat
      : RootJsonFormat[Definitions.StrippedStateEvent] = rootFormat(
    lazyFormat(jsonFormat(Definitions.StrippedStateEvent, "content", "state_key", "type", "sender"))
  )

  implicit val DefinitionsHomeserverInformationLazyFormat
      : RootJsonFormat[Definitions.HomeserverInformation] = rootFormat(
    lazyFormat(jsonFormat(Definitions.HomeserverInformation, "base_url"))
  )

  implicit val DefinitionsIdentityServerInformationLazyFormat
      : RootJsonFormat[Definitions.IdentityServerInformation] = rootFormat(
    lazyFormat(jsonFormat(Definitions.IdentityServerInformation, "base_url"))
  )

  implicit val DefinitionsDiscoveryInformationLazyFormat
      : RootJsonFormat[Definitions.DiscoveryInformation] = rootFormat(
    lazyFormat(jsonFormat(Definitions.DiscoveryInformation, "m.homeserver", "m.identity_server"))
  )

  implicit val DefinitionsPublicRoomsChunkLazyFormat: RootJsonFormat[Definitions.PublicRoomsChunk] =
    rootFormat(
      lazyFormat(
        jsonFormat(
          Definitions.PublicRoomsChunk,
          "canonical_alias",
          "name",
          "num_joined_members",
          "room_id",
          "topic",
          "world_readable",
          "guest_can_join",
          "avatar_url",
          "join_rule",
          "room_type"
        )
      )
    )

  implicit val DefinitionsProtocolFieldTypeLazyFormat
      : RootJsonFormat[Definitions.Protocol.FieldType] = rootFormat(
    lazyFormat(jsonFormat(Definitions.Protocol.FieldType, "regexp", "placeholder"))
  )

  implicit val DefinitionsProtocolProtocolInstanceLazyFormat
      : RootJsonFormat[Definitions.Protocol.ProtocolInstance] = rootFormat(
    lazyFormat(
      jsonFormat(Definitions.Protocol.ProtocolInstance, "desc", "icon", "fields", "network_id")
    )
  )

  implicit val DefinitionsProtocolLazyFormat: RootJsonFormat[Definitions.Protocol] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.Protocol.apply,
        "user_fields",
        "location_fields",
        "icon",
        "field_types",
        "instances"
      )
    )
  )

  implicit val DefinitionsLocationLazyFormat: RootJsonFormat[Definitions.Location] = rootFormat(
    lazyFormat(jsonFormat(Definitions.Location, "alias", "protocol", "fields"))
  )

  implicit val DefinitionsUserLazyFormat: RootJsonFormat[Definitions.User] = rootFormat(
    lazyFormat(jsonFormat(Definitions.User, "userid", "protocol", "fields"))
  )

  implicit val DefinitionsClientEventCompositeUnsignedLazyFormat
      : RootJsonFormat[Definitions.ClientEvent.Composite.Unsigned] = rootFormat(
    lazyFormat(jsonFormat(Definitions.ClientEvent.Composite.Unsigned, "redacted_because"))
  )

  implicit val DefinitionsClientEventCompositeLazyFormat
      : RootJsonFormat[Definitions.ClientEvent.Composite] = rootFormat(
    lazyFormat(jsonFormat(Definitions.ClientEvent.Composite.apply, "room_id", "unsigned"))
  )

  implicit val DefinitionsClientEventLazyFormat: RootJsonFormat[Definitions.ClientEvent] =
    rootFormat(
      lazyFormat(
        jsonFormat(
          Definitions.ClientEvent.apply,
          "event_id",
          "type",
          "state_key",
          "sender",
          "origin_server_ts",
          "content",
          "unsigned",
          "room_id"
        )
      )
    )

  implicit val DefinitionsDeviceLazyFormat: RootJsonFormat[Definitions.Device] = rootFormat(
    lazyFormat(
      jsonFormat(Definitions.Device, "device_id", "display_name", "last_seen_ip", "last_seen_ts")
    )
  )

  implicit val DefinitionsOpenIdCredentialsLazyFormat
      : RootJsonFormat[Definitions.OpenIdCredentials] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.OpenIdCredentials,
        "access_token",
        "token_type",
        "matrix_server_name",
        "expires_in"
      )
    )
  )

  implicit val DefinitionsUserIdentifierLazyFormat: RootJsonFormat[Definitions.UserIdentifier] =
    rootFormat(lazyFormat(jsonFormat(Definitions.UserIdentifier, "type")))

  implicit val DefinitionsDeviceKeysLazyFormat: RootJsonFormat[Definitions.DeviceKeys] = rootFormat(
    lazyFormat(
      jsonFormat(Definitions.DeviceKeys, "user_id", "device_id", "algorithms", "keys", "signatures")
    )
  )

  implicit val DefinitionsCrossSigningKeyUsageEnumFormat
      : RootJsonFormat[Definitions.CrossSigningKey.UsageEnum.Value] = new EnumJsonConverter(
    Definitions.CrossSigningKey.UsageEnum
  )

  implicit val DefinitionsCrossSigningKeyLazyFormat: RootJsonFormat[Definitions.CrossSigningKey] =
    rootFormat(
      lazyFormat(
        jsonFormat(Definitions.CrossSigningKey.apply, "user_id", "usage", "keys", "signatures")
      )
    )

  implicit val DefinitionsPublicRoomsResponseChunkPublicRoomsChunkLazyFormat
      : RootJsonFormat[Definitions.PublicRoomsResponse.Chunk.PublicRoomsChunk] = rootFormat(
    lazyFormat(jsonFormat(Definitions.PublicRoomsResponse.Chunk.PublicRoomsChunk, "join_rule"))
  )

  implicit val DefinitionsPublicRoomsResponseChunkLazyFormat
      : RootJsonFormat[Definitions.PublicRoomsResponse.Chunk] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.PublicRoomsResponse.Chunk.apply,
        "canonical_alias",
        "name",
        "num_joined_members",
        "room_id",
        "topic",
        "world_readable",
        "guest_can_join",
        "avatar_url",
        "join_rule",
        "room_type"
      )
    )
  )

  implicit val DefinitionsPublicRoomsResponseLazyFormat
      : RootJsonFormat[Definitions.PublicRoomsResponse] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.PublicRoomsResponse.apply,
        "chunk",
        "next_batch",
        "prev_batch",
        "total_room_count_estimate"
      )
    )
  )

  implicit val DefinitionsSidLazyFormat: RootJsonFormat[Definitions.Sid] = rootFormat(
    lazyFormat(jsonFormat(Definitions.Sid, "sid"))
  )

  implicit val DefinitionsModerationPolicyRuleLazyFormat
      : RootJsonFormat[Definitions.ModerationPolicyRule] = rootFormat(
    lazyFormat(jsonFormat(Definitions.ModerationPolicyRule, "entity", "recommendation", "reason"))
  )

  implicit val DefinitionsUnsignedDataLazyFormat: RootJsonFormat[Definitions.UnsignedData] =
    rootFormat(lazyFormat(jsonFormat(Definitions.UnsignedData, "age")))

  implicit val DefinitionsSyncRoomEventLazyFormat: RootJsonFormat[Definitions.SyncRoomEvent] =
    rootFormat(
      lazyFormat(
        jsonFormat(
          Definitions.SyncRoomEvent,
          "content",
          "type",
          "event_id",
          "sender",
          "origin_server_ts",
          "unsigned"
        )
      )
    )

  implicit val DefinitionsRoomEventLazyFormat: RootJsonFormat[Definitions.RoomEvent] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.RoomEvent,
        "event_id",
        "sender",
        "origin_server_ts",
        "unsigned",
        "room_id"
      )
    )
  )

  implicit val DefinitionsSyncStateEventLazyFormat: RootJsonFormat[Definitions.SyncStateEvent] =
    rootFormat(
      lazyFormat(
        jsonFormat(
          Definitions.SyncStateEvent,
          "event_id",
          "sender",
          "origin_server_ts",
          "unsigned",
          "state_key"
        )
      )
    )

  implicit val DefinitionsStateEventLazyFormat: RootJsonFormat[Definitions.StateEvent] = rootFormat(
    lazyFormat(jsonFormat(Definitions.StateEvent, "room_id", "state_key"))
  )

  implicit val DefinitionsThumbnailInfoLazyFormat: RootJsonFormat[Definitions.ThumbnailInfo] =
    rootFormat(lazyFormat(jsonFormat(Definitions.ThumbnailInfo, "h", "w", "mimetype", "size")))

  implicit val DefinitionsVerificationRelatesToRelTypeEnumFormat
      : RootJsonFormat[Definitions.VerificationRelatesTo.RelTypeEnum.Value] = new EnumJsonConverter(
    Definitions.VerificationRelatesTo.RelTypeEnum
  )

  implicit val DefinitionsVerificationRelatesToLazyFormat
      : RootJsonFormat[Definitions.VerificationRelatesTo] = rootFormat(
    lazyFormat(jsonFormat(Definitions.VerificationRelatesTo.apply, "rel_type", "event_id"))
  )

  implicit val DefinitionsImageInfoLazyFormat: RootJsonFormat[Definitions.ImageInfo] = rootFormat(
    lazyFormat(
      jsonFormat(
        Definitions.ImageInfo,
        "h",
        "w",
        "mimetype",
        "size",
        "thumbnail_url",
        "thumbnail_file",
        "thumbnail_info"
      )
    )
  )

}
