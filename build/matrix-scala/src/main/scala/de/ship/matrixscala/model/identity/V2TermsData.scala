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

package de.ship.matrixscala.model.identity

import de.ship.matrixscala.model.Definitions

import de.ship.matrixscala.core._

import spray.json.{JsObject, JsValue}

object V2TermsData {

  object GetTerms {

    object OK {

      object PolicyObject {

        /**
          * <h1>InternationalisedPolicy</h1>
          *
          *  The policy information for the specified language.
          *
          * @see "defined in ./build/matrix-spec/data/api/identity/v2_terms.yaml"
          */
        case class InternationalisedPolicy(
            name: String,
            url: String
        )

      }

      /**
        * <h1>PolicyObject</h1>
        *
        *  The policy. Includes a map of language (ISO 639-2) to language-specific
        * policy information.
        *
        * @see "defined in ./build/matrix-spec/data/api/identity/v2_terms.yaml"
        */
      case class PolicyObject(
          version: String
      ) extends AdditionalFields[
            GetTerms.OK.PolicyObject,
            GetTerms.OK.PolicyObject.InternationalisedPolicy
          ]

    }

    /**
      * <h1>OK</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/identity/v2_terms.yaml"
      */
    case class OK(
        policies: Map[String, GetTerms.OK.PolicyObject]
    )

  }

  object AgreeToTerms {

    /**
      * <h1>Body</h1>
      *
      * @see "defined in ./build/matrix-spec/data/api/identity/v2_terms.yaml"
      */
    case class Body(
        user_accepts: Seq[String]
    )

  }

}