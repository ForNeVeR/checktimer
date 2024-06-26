// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class ConfigurationSpec extends AnyFlatSpec with Matchers {

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
      """.stripMargin.getBytes(StandardCharsets.UTF_8))

    val config = Configuration("C:\\Users\\Герасим\\checktimer.csv")
    Configuration.loadFrom(configFile.getAbsolutePath) should be(config)
  }
}
