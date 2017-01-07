package me.fornever.checktimer

import java.io.File
import java.nio.file.{Files, Paths}

import com.typesafe.config.ConfigFactory

case class Configuration(databasePath: String, taskExecutable: String)

object Configuration {

  val default = Configuration(Paths.get(Environment.homeDirectory, "checktimer.csv").toString, "task")

  def loadFrom(path: String): Configuration = {
    if (!Files.exists(Paths.get(path))) {
      default
    } else {
      val config = ConfigFactory.parseFile(new File(path))
      Configuration(config.getString("database"), config.getString("task"))
    }
  }
}
