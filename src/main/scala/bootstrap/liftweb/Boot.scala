package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import code.lib.db.DBVendor

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {

    LiftRules.addToPackages("code")

    val entries = List(
      Menu.i("What Todo?") / "index" >> Hidden
    )

    LiftRules.setSiteMap(SiteMap(entries:_*))

    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
    MapperRules.columnName = (_,name) => StringHelpers.snakify(name)
}
}
