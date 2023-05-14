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

//analogues to QueryBuilding, just more simple
object HeaderBuilding {
  type HeaderData = String

  sealed trait HeaderComponentBuilder[T] {
    def build(data: T): Option[HeaderData]
  }

  implicit object stringHeaderBuilder extends HeaderComponentBuilder[String] {
    override def build(data: String): Option[HeaderData] = Some(data)
  }

  implicit object optionalStringHeaderBuilder extends HeaderComponentBuilder[Option[String]] {
    override def build(data: Option[String]): Option[HeaderData] = data
  }

  def buildHeaders(entries: Option[(String, HeaderData)]*): Map[String, HeaderData] =
    entries.flatten.toMap

  def fromHeaderEntry[T](t: (String, T))(implicit
      builder: HeaderComponentBuilder[T]
  ): Option[(String, HeaderData)] = builder.build(t._2).map(t._1 -> _)

}
