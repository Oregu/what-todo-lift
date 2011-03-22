package code.snippet

import net.liftweb._
import http._
import common._
import code.model._
import util.Helpers._

class ListTodo {

	val items: List[Item] = Item.findAll

	def render = "li *" #> items.map(_.description)
}
