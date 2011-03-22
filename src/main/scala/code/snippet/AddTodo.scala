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

	def render = {

		def process(item: Item): JsCmd = {

      val descId = item.description.uniqueFieldId match {
        case Full(s) => s
        case _ => ""
      }

      val errors = item.validate
      if (!errors.isEmpty) {
        S.error(errors)
        return Focus(descId)
      }

      if (item.save) {

        S.notice("Successfully added")

        SetHtml("todo_list", <lift:embed what="/todo/_list"/>) &
        SetValById(descId, "") &
        Focus(descId)
      }
      else {
        S.error("Something bad happened")
        Focus(descId)
      }
		}

		val item = Item.create
		"#add_form" #> item.toForm(Full("Plan!"), { process _ })
	}
}
