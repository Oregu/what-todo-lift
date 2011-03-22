package code.lib.db

import _root_.code.model._
import net.liftweb._
import util._
import mapper._

object SynchroSchema extends Application {

    DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
    MapperRules.columnName = (_,name) => StringHelpers.snakify(name)
    Schemifier.schemify(true, Schemifier.infoF _, Item)

}