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

object AddTodo {

  val logger = Logger(this.getClass)

	def render = {

    logger.info("I'm called")

		def process(item: Item): JsCmd = {

      logger.info("My item id is " + item.id)

      val descId = item.description.uniqueFieldId openOr ""

      val errors = item.validate
      if (!errors.isEmpty) {
        S.error(errors)
        return Focus(descId)
      }

      if (item.save) {

        S.notice("Successfully added")

        SetHtml("todo_add", <lift:embed what="/todo/_add"/>) &
        SetHtml("todo_list", <lift:embed what="/todo/_list"/>) &
        SetValById(descId, "") &
        Focus(descId)
      }
      else {
        S.error("Something bad happened")
        Focus(descId)
      }
		}

		"#add_form" #> Item.create.toForm(Full("Plan!"), { process _ })
	}
}
