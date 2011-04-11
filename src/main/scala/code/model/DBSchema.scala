package code.model

import org.squeryl.Schema

/**
 * User: oregusya
 * Date: 4/3/11
 * Time: 1:24 PM
 */
object DBSchema extends Schema {

  val items = table[Item]("items")

  /**
   * Drops an old schema if exists and then creates
   * the new schema.
   */
  def dropAndCreate {
    drop
    create
  }
}