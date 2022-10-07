package com.datio.example.food.transformations


import com.datio.example.food.commons.Constants.{DATE, INT, LONG, ROW_NUMBER}
import com.datio.example.food.commons.columns.input.{Food, Foodie}
import com.datio.example.food.commons.columns.output.Report1
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Column, DataFrame, Dataset, functions}
import org.apache.spark.sql.functions.{col, concat, date_format, lit, row_number, to_date, when}

object Transformations {

  def spicyFilter(dataFrame: DataFrame): DataFrame = {
    val seq = Seq(1,2,3,5,6,7,8,9)
    dataFrame.filter(!Food.SpicyLevel.column.isin(seq:_*))
  }

  def justWoman(dataFrame: DataFrame): DataFrame = {
    val rowNumberCol = col(ROW_NUMBER)
    val windowSpec = Window.partitionBy(Foodie.Nationality.column, Foodie.Genre.column)
      .orderBy((Foodie.SpicyTolerance.column.cast(INT).desc))
    dataFrame.select(dataFrame.columns.map(s => col(s)) :+ row_number().over(windowSpec).alias(ROW_NUMBER): _*)
      .filter(Foodie.Genre.column === "FEMALE")
      .filter(rowNumberCol === 1 || rowNumberCol === 2)
      .distinct()
  }

  def listOfFood(dataFrame: DataFrame): String = {
    val list: DataFrame = dataFrame
      .select(
        concat(Food.Code.column, lit(": "), Food.Name.column,
          when(Food.ContainsAnimalProducts.column === "N", "(*)").otherwise("")
        )
      )
    list.collect().drop(1).map(_.getString(0)).mkString(",")
  }

  def concatFood(foodie: DataFrame, food: DataFrame): DataFrame = {
    val concatListOfFood = listOfFood(food)
    println(concatListOfFood)
    foodie.select(foodie.columns.map(s => col(s)) :+ lit(concatListOfFood).alias(Report1.FoodList.name) :_*)
  }

  def castCols(dataFrame: DataFrame): DataFrame = {
    dataFrame.select(
      Report1.SpicyTolerance.column.cast(INT),
      Report1.Name.column,
      Report1.Preferences.column,
      Report1.Nationality.column,
      Report1.Genre.column,
      Report1.RowNumber.column,
      Report1.FoodList.column
    )
  }
}
