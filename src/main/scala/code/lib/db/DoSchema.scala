package code.lib.db

import _root_.code.model._
import org.squeryl.adapters.MySQLAdapter
import java.sql.DriverManager
import org.squeryl.Session
import org.squeryl.PrimitiveTypeMode._
import net.liftweb.squerylrecord.SquerylRecord

object DoSchema extends Application {

//  DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
//  MapperRules.columnName = (_,name) => StringHelpers.snakify(name)
//  Schemifier.schemify(true, Schemifier.infoF _, Item)

  Class.forName("com.mysql.jdbc.Driver")

  SquerylRecord.initWithSquerylSession(
    Session.create(
      DriverManager.getConnection("jdbc:mysql://localhost/whattodo", "whattodo", "secr3t"),
      new MySQLAdapter))

  transaction { DBSchema.dropAndCreate }
}