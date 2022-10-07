package com.datio.example.food.commons.columns

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.Column

trait Col {
  val name: String
  lazy val column: Column = col(name)
}
