package code.snippet

import net.liftweb._
import http._
import common._
import util._
import util.Helpers._
import js._
import JsCmds._
import JE._
import scala.xml.NodeSeq
import code.model._
import DBSchema._
import squerylrecord.RecordTypeMode._

object AddTodo {

  val logger = Logger(this.getClass)

	def render = {

    logger.debug("I'm called")

		def process(item: Item): Unit = {

      logger.debug("My item id is " + item.id)

      val descId = item.description.uniqueFieldId openOr ""

      val errors = item.validate
      if (!errors.isEmpty) {
        S.error(errors)
        S.appendJs(Focus(descId))
        return
      }

      try {
        items insert item
      }
      catch {
        case _ => {
          S.error("Something bad happened")
          return Focus(descId)
        }
      }

      S.notice("Successfully added")

      S.appendJs(SetHtml("todo_add", <lift:embed what="/todo/_add"/>) &
                  SetHtml("todo_list", <lift:embed what="/todo/_list"/>) &
                  SetValById(descId, "") &
                  Focus(descId))
		}

		"#add_form" #> Item.createRecord.toForm(Full("Plan!"))({ process _ })
	}
}
