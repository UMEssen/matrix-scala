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

package de.ship.matrixscala.model.clientserver

import de.ship.matrixscala.model.Definitions

import de.ship.matrixscala.core._

import spray.json.{JsObject, JsValue}

object PushrulesData {

  object GetPushRules {

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pushrules.yaml"
      */
    case class OK(
        global: Definitions.PushRuleset
    )

  }

  object SetPushRule {

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pushrules.yaml"
      */
    case class Body(
        actions: Seq[Union2[String, JsObject]],
        conditions: Option[Seq[Definitions.PushCondition]] = None,
        pattern: Option[String] = None
    )

  }

  object IsPushRuleEnabled {

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pushrules.yaml"
      */
    case class OK(
        enabled: Boolean
    )

  }

  object SetPushRuleEnabled {

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pushrules.yaml"
      */
    case class Body(
        enabled: Boolean
    )

  }

  object GetPushRuleActions {

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pushrules.yaml"
      */
    case class OK(
        actions: Seq[Union2[String, JsObject]]
    )

  }

  object SetPushRuleActions {

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/client-server/pushrules.yaml"
      */
    case class Body(
        actions: Seq[Union2[String, JsObject]]
    )

  }

}
