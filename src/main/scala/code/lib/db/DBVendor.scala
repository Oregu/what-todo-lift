package code
package lib
package db

import net.liftweb._
import mapper._
import common._

import _root_.java.sql._


object DBVendor extends ConnectionManager {

  // Force load the driver
  Class.forName("com.mysql.jdbc.Driver")

  // define methods
  def newConnection(name : ConnectionIdentifier) = {
    try {
      Full(DriverManager.getConnection(
           "jdbc:mysql://localhost/whattodo",
           "whattodo", "secr3t"))
    } catch {
      case e : Exception => e.printStackTrace; Empty
    }
  }

  def releaseConnection (conn : Connection) { conn.close }
}