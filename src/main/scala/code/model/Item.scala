package code
package model

import DBSchema._
import net.liftweb._
import common._
import record.{MetaRecord, Record}
import record.field._
import squerylrecord.KeyedRecord

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.annotations.Column

import util._
import scala.xml.NodeSeq

class Item extends Record[Item] with KeyedRecord[Long] {

  override def meta = Item

  @Column(name = "id")
  val idField = new LongField(this)

  val description = new StringField(this, 255, "") {

    val msgFieldId = Full("items_description_msg")

    override def setFilter = notNull _ :: trim _ :: super.setFilter

    override def validations = noEmptyDescriptions _ :: super.validations

    def noEmptyDescriptions(desc: String) : List[FieldError] = {
      if (desc.isEmpty)
        List[FieldError](FieldError(
          new FieldIdentifier { override def uniqueFieldId = msgFieldId },
          "You didn't provide anything"))
      else
        List[FieldError]()
    }
  }

  val expiresAt = new OptionalDateTimeField(this)
}

object Item extends Item with MetaRecord[Item] {

  def findAll = from(items)(select(_)).toList

	override def fieldOrder = List(idField, description, expiresAt)

  formTemplate = Full(<lift:field name="description"/>)
}
