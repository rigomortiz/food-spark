package com.datio.example.food.transformations

import com.datio.example.food.commons.columns.input.{Food, Foodie}
import com.datio.example.food.context.ContextProvider
import com.datio.example.food.transformations.Transformations.{justWoman, listOfFood, spicyFilter}
import com.datio.example.food.utils.IOUtils
import org.apache.spark.sql.DataFrame

class TransformationsTest extends ContextProvider with IOUtils {

  "spicyFilter method" should "return a DF in column 'SPICY_LEVEL' without Seq(1,2,3,5,6,7,8,9)" in {
    val path = config.getConfig("FoodSparkJob.input.t_kdit_food")
    val dataFrame: DataFrame = read(path)
    val output = spicyFilter(dataFrame)
    val seq = Seq(1,2,3,5,6,7,8,9)

    val select = output.filter(Food.SpicyLevel.column.isin(seq:_*))
      select.count() shouldBe 0
  }

  "justWoman method" should "return a DF with column GENRE equals to 'FEMALE'" in {
    val path = config.getConfig("FoodSparkJob.input.t_kdit_foodie")
    val dataFrame: DataFrame = read(path)
    val output = justWoman(dataFrame)
    output.show()
    output.filter(Foodie.Genre.column =!= "FEMALE").count() shouldBe 0
  }

  "listOfFood method" should "return a string with the concatenation of the columns CODE and NAME" in {
    val path = config.getConfig("FoodSparkJob.input.t_kdit_food")
    val dataFrame: DataFrame = read(path)
    val output = listOfFood(dataFrame)

    output should not be empty
  }

}
