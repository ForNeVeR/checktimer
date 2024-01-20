package me.fornever.checktimer

import com.typesafe.config.ConfigFactory

import java.io.File
import java.nio.file.{Files, Paths}

object Configuration {

  val default = Configuration(Paths.get(Environment.homeDirectory, "checktimer.csv").toString)

  def loadFrom(path: String): Configuration = {
    if (!Files.exists(Paths.get(path))) {
      default
    } else {
      val config = ConfigFactory.parseFile(new File(path))
      Configuration(config.getString("database"))
    }
  }
}

case class Configuration(databasePath: String)
