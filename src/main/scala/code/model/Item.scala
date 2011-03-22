package code
package model

import net.liftweb._
import common._
import mapper._
import util._
import scala.xml.{NodeSeq, Text}

class Item extends LongKeyedMapper[Item] with IdPK {

	def getSingleton = Item

	object description extends MappedString(this, 255) with ValidateLength {
		override def setFilter = trim _ :: super.setFilter
    override def fieldId = uniqueFieldId.toOption.map(Text(_))
		override def validations = noEmptyDescriptions _ :: super.validations

		def noEmptyDescriptions(desc: String) = {
			if (desc.isEmpty)
				List[FieldError](FieldError(this, "You didn't provide anything"))
			else
				List[FieldError]()
		}
	}

	object createdAt extends MappedDateTime(this) {
		override def dbIncludeInForm_? = false
	}

	object expiresAt extends MappedDateTime(this) {
		override def dbIncludeInForm_? = false
	}
}

object Item extends Item with LongKeyedMetaMapper[Item] {

  formatFormElement =
    (_, form) =>
    <xml:group>{form}</xml:group>

	override def fieldOrder = List(description, createdAt, expiresAt)
	override def dbTableName = "items"

	override def beforeCreate = setCreatedAt _ :: super.beforeCreate

	private def setCreatedAt(obj: Item): Unit =
		obj.createdAt(new java.util.Date)
}
