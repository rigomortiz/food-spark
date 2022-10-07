package com.datio.example.food

import java.net.URI
import com.datio.dataproc.sdk.api.SparkProcess
import com.datio.dataproc.sdk.api.context.RuntimeContext
import com.datio.dataproc.sdk.datiosparksession.DatioSparkSession
import com.datio.dataproc.sdk.schema.DatioSchema
import com.datio.example.food.transformations.Transformations.{castCols, concatFood, justWoman, spicyFilter}
import com.datio.example.food.utils.IOUtils
import com.typesafe.config.ConfigException
import org.apache.spark.sql.DataFrame
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

/**
  * Main entrypoint for FoodSparkJob process.
  * Implements SparkProcess so it can be found by the Dataproc launcher using JSPI.
  *
  * Configuration for this class should be expressed in HOCON like this:
  *
  * FoodSparkJob {
  *   ...
  * }
  *
  * This example app reads and writes a csv file contained within the project, for external
  * input or output set the environment variables INPUT_PATH or OUTPUT_PATH, or modify the
  * proper configuration paths.
  *
  */
class FoodSparkJob extends SparkProcess with IOUtils {

  private val logger = LoggerFactory.getLogger(classOf[FoodSparkJob])

  override def getProcessId: String = "FoodSparkJob"

  override def runProcess(context: RuntimeContext): Int = {

    val OK = 0
    val ERR = 1

    Try {
      logger.info(s"Process Id: ${context.getProcessId}")

      // Read config file
      val jobConfig = context.getConfig.getConfig("FoodSparkJob").resolve()

      // Read the input files
      val foodDF: DataFrame = read(jobConfig.getConfig("input.t_kdit_food"))
      val foodieDF: DataFrame = read(jobConfig.getConfig("input.t_kdit_foodie"))

      // Transformations
      val dataFrame1 = spicyFilter(foodDF)
      val dataFrame2 = justWoman(foodieDF)
      val dataFrame3 = concatFood(dataFrame2, dataFrame1)
      val outputDF = castCols(dataFrame3)

      outputDF.show()
      // Write the output file
      write(outputDF, jobConfig.getConfig("output.report1"))

    } match {
      case Success(_) =>
        logger.info("Succesful processing!")
        OK
      case Failure(e) =>
        // You can do wathever exception control you see fit, keep in mind that if the exception
        // bubbles up, it will also be caught at launcher level and the process will return with error
        logger.error("There was an error during the processing of the data", e)
        ERR
    }
  }
}
