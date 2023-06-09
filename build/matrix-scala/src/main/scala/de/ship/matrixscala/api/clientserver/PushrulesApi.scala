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
import de.ship.matrixscala.model.clientserver.PushrulesData._
import de.ship.matrixscala.json.clientserver.PushrulesJsonFormats._
import de.ship.matrixscala.json.DefinitionFormats._
import de.ship.matrixscala.core._
import de.ship.matrixscala.core.ResponseMappings._
import de.ship.matrixscala.core.QueryBuilding._
import de.ship.matrixscala.core.HeaderBuilding._
import akka.http.scaladsl.model.HttpMethods
import spray.json.{JsObject, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol._

/**
  * @see "defined in ./build/matrix-spec/data/api/client-server/pushrules.yaml"
  */
object PushrulesApi {

  /**
    * <h1>getPushRules</h1>
    *
    * Retrieve all push rulesets for this user. Clients can "drill-down" on
    * the rulesets by suffixing a `scope` to this path e.g.
    * `/pushrules/global/`. This will return a subset of this data under the
    * specified key e.g. the `global` key.
    */
  def getPushRules(
  ): ApiRequest[AccessTokenAuthentication, JsObject, GetPushRules.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[GetPushRules.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map.empty,
      inHeader = Map.empty
    )

  /**
    * <h1>getPushRule</h1>
    *
    * Retrieve a single specified push rule.
    *
    * @param scope `global` to specify global rules.
    *
    * @param kind The kind of rule
    *
    * @param ruleId The identifier for the rule.
    */
  def getPushRule(
      scope: String,
      kind: String,
      ruleId: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, Definitions.PushRule] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/{scope}/{kind}/{ruleId}",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[Definitions.PushRule]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "scope"  -> scope,
        "kind"   -> kind,
        "ruleId" -> ruleId
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>setPushRule</h1>
    *
    * This endpoint allows the creation and modification of user defined push
    * rules.
    *
    * If a rule with the same `rule_id` already exists among rules of the same
    * kind, it is updated with the new parameters, otherwise a new rule is
    * created.
    *
    * If both `after` and `before` are provided, the new or updated rule must
    * be the next most important rule with respect to the rule identified by
    * `before`.
    *
    * If neither `after` nor `before` are provided and the rule is created, it
    * should be added as the most important user defined rule among rules of
    * the same kind.
    *
    * When creating push rules, they MUST be enabled by default.
    *
    * @param scope `global` to specify global rules.
    *
    * @param kind The kind of rule
    *
    * @param ruleId The identifier for the rule. If the string starts with a dot ("."),
    * the request MUST be rejected as this is reserved for server-default
    * rules. Slashes ("/") and backslashes ("\\") are also not allowed.
    *
    * @param before Use 'before' with a `rule_id` as its value to make the new rule the
    * next-most important rule with respect to the given user defined rule.
    * It is not possible to add a rule relative to a predefined server rule.
    *
    * @param after This makes the new rule the next-less important rule relative to the
    * given user defined rule. It is not possible to add a rule relative
    * to a predefined server rule.
    */
  def setPushRule(
      body: SetPushRule.Body,
      scope: String,
      kind: String,
      ruleId: String,
      before: Option[String] = None,
      after: Option[String] = None
  ): ApiRequest[AccessTokenAuthentication, SetPushRule.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/{scope}/{kind}/{ruleId}",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[SetPushRule.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = buildQuery(
        fromQueryEntry[Option[String]]("before" -> before),
        fromQueryEntry[Option[String]]("after"  -> after)
      ),
      inPath = Map(
        "scope"  -> scope,
        "kind"   -> kind,
        "ruleId" -> ruleId
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>deletePushRule</h1>
    *
    * This endpoint removes the push rule defined in the path.
    *
    * @param scope `global` to specify global rules.
    *
    * @param kind The kind of rule
    *
    * @param ruleId The identifier for the rule.
    */
  def deletePushRule(
      scope: String,
      kind: String,
      ruleId: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/{scope}/{kind}/{ruleId}",
      httpMethod = HttpMethods.DELETE,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "scope"  -> scope,
        "kind"   -> kind,
        "ruleId" -> ruleId
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>isPushRuleEnabled</h1>
    *
    * This endpoint gets whether the specified push rule is enabled.
    *
    * @param scope Either `global` or `device/<profile_tag>` to specify global
    * rules or device rules for the given `profile_tag`.
    *
    * @param kind The kind of rule
    *
    * @param ruleId The identifier for the rule.
    */
  def isPushRuleEnabled(
      scope: String,
      kind: String,
      ruleId: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, IsPushRuleEnabled.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/{scope}/{kind}/{ruleId}/enabled",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[IsPushRuleEnabled.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "scope"  -> scope,
        "kind"   -> kind,
        "ruleId" -> ruleId
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>setPushRuleEnabled</h1>
    *
    * This endpoint allows clients to enable or disable the specified push rule.
    *
    * @param scope `global` to specify global rules.
    *
    * @param kind The kind of rule
    *
    * @param ruleId The identifier for the rule.
    */
  def setPushRuleEnabled(
      body: SetPushRuleEnabled.Body,
      scope: String,
      kind: String,
      ruleId: String
  ): ApiRequest[AccessTokenAuthentication, SetPushRuleEnabled.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/{scope}/{kind}/{ruleId}/enabled",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[SetPushRuleEnabled.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "scope"  -> scope,
        "kind"   -> kind,
        "ruleId" -> ruleId
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>getPushRuleActions</h1>
    *
    * This endpoint get the actions for the specified push rule.
    *
    * @param scope Either `global` or `device/<profile_tag>` to specify global
    * rules or device rules for the given `profile_tag`.
    *
    * @param kind The kind of rule
    *
    * @param ruleId The identifier for the rule.
    */
  def getPushRuleActions(
      scope: String,
      kind: String,
      ruleId: String
  ): ApiRequest[AccessTokenAuthentication, JsObject, GetPushRuleActions.OK] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/{scope}/{kind}/{ruleId}/actions",
      httpMethod = HttpMethods.GET,
      body = JsObject(),
      bodyFormat = implicitly[RootJsonFormat[JsObject]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[GetPushRuleActions.OK]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "scope"  -> scope,
        "kind"   -> kind,
        "ruleId" -> ruleId
      ),
      inHeader = Map.empty
    )

  /**
    * <h1>setPushRuleActions</h1>
    *
    * This endpoint allows clients to change the actions of a push rule.
    * This can be used to change the actions of builtin rules.
    *
    * @param scope `global` to specify global rules.
    *
    * @param kind The kind of rule
    *
    * @param ruleId The identifier for the rule.
    */
  def setPushRuleActions(
      body: SetPushRuleActions.Body,
      scope: String,
      kind: String,
      ruleId: String
  ): ApiRequest[AccessTokenAuthentication, SetPushRuleActions.Body, JsObject] =
    ApiRequest(
      endpoint = "/_matrix/client/v3/pushrules/{scope}/{kind}/{ruleId}/actions",
      httpMethod = HttpMethods.PUT,
      body = body,
      bodyFormat = implicitly[RootJsonFormat[SetPushRuleActions.Body]],
      responseMappings = Seq(
        SingleResponseMapping(200, implicitly[RootJsonFormat[JsObject]])
      ),
      inQuery = Map.empty,
      inPath = Map(
        "scope"  -> scope,
        "kind"   -> kind,
        "ruleId" -> ruleId
      ),
      inHeader = Map.empty
    )

}
