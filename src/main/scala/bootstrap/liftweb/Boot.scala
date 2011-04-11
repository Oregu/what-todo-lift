package bootstrap.liftweb

import scala.util.control.Exception.ultimately
import net.liftweb._
import util._
import squerylrecord.SquerylRecord
import common._
import http._
import sitemap._
import Loc._
import mapper._
import org.squeryl.adapters.MySQLAdapter

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

    DB.defineConnectionManager(DefaultConnectionIdentifier,
       new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
                            Props.get("db.url") openOr "jdbc:h2:test",
                            Props.get("db.user"), Props.get("db.password")))

    SquerylRecord.init(() => new MySQLAdapter)

    S.addAround(DB.buildLoanWrapper(DefaultConnectionIdentifier::Nil))
    S.addAround(new LoanWrapper {
      def apply[T](f: => T): T = ultimately(println(S.getAllNotices))(f)
    })
}
}
