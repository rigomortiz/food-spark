package com.datio.example.food.steps

import java.nio.file.Files._
import java.nio.file.Paths

import com.datio.dataproc.sdk.launcher.SparkLauncher
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.Matchers

import scala.util.Try

class FoodSparkJobSteps extends ScalaDsl with EN with Matchers {

  private var givenProcessId = "FoodSparkJob"
  private var givenConfigPath = "src/test/resources/config/application-test.conf"
  private var executionExitCode = -1

  Given("""The id of the process as {string}""") {
    processId: String => {
      givenProcessId = processId
    }
  }

  Given("""A config file with the contents:""") {
    content: String => {
      val configFile = Paths.get("target", "config/application-test.conf").toAbsolutePath
      Try(delete(configFile))
      Try(createDirectories(configFile.getParent))
      createFile(configFile)
      write(configFile, content.getBytes("UTF-8"))
      givenConfigPath = configFile.toString
    }
  }

  Given("""The env {string} targeting the resource file {string}""") {
    (env: String, resourceFile: String) => {
      setEnv(env, getTestResourcePath(resourceFile))
    }
  }

  Given("""The env {string} targeting the target file {string}""") {
    (env: String, resourceFile: String) => {
      setEnv(env, getTargetPath(resourceFile))
    }
  }

  When("""Executing the Launcher""") {
    () => {
      val args = Array(givenConfigPath, givenProcessId)
      executionExitCode = new SparkLauncher().execute(args)
    }
  }

  Then("""The exit code should be {int}""") {
    exitCode: Int =>
      executionExitCode shouldBe exitCode
  }

  private def setEnv(key: String, value: String) = {
    val field = System.getenv().getClass.getDeclaredField("m")
    field.setAccessible(true)
    val map = field.get(System.getenv()).asInstanceOf[java.util.Map[java.lang.String, java.lang.String]]
    map.put(key, value)
  }

  private def getTestResourcePath(file: String): String = {
    val resource = Paths.get("src", "test", "resources", file)
    resource.toFile.getAbsolutePath
  }

  private def getTargetPath(file: String): String = {
    val resource = Paths.get("target", file)
    resource.toFile.getAbsolutePath
  }
}
