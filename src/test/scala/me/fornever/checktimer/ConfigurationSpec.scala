package me.fornever.checktimer

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

import org.scalatest.{FlatSpec, Matchers}

class ConfigurationSpec extends FlatSpec with Matchers {

  "The Configuration" should "load the default instance if the file doesn't exist" in {
    val nonexistent = File.createTempFile("checktimer", "cfg")
    nonexistent.delete()

    Configuration.loadFrom(nonexistent.getAbsolutePath) should be(Configuration.default)
  }

  it should "be loaded from existing file successfully" in {
    val configFile = File.createTempFile("checktimer", "cfg")
    configFile.deleteOnExit()

    Files.write(
      configFile.toPath,
      """
        |database: "C:\\Users\\Герасим\\checktimer.csv"
        |task: "zomg"
      """.stripMargin.getBytes(StandardCharsets.UTF_8))

    val config = Configuration("C:\\Users\\Герасим\\checktimer.csv", "zomg")
    Configuration.loadFrom(configFile.getAbsolutePath) should be(config)
  }
}
