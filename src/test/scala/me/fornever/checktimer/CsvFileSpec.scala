package me.fornever.checktimer

import java.io.File
import java.time.Duration

import me.fornever.checktimer.dto.TrackDto
import org.scalatest._

import scala.io.Source

class CsvFileSpec extends FlatSpec with Matchers {

  "The CsvFile" should "append the data in the proper format" in {
    val dto = TrackDto(DateTimeUtils.now(), Duration.ZERO, "project", "activity")
    val file = File.createTempFile("checktimer", ".csv")
    CsvFile.append(file.getAbsolutePath, dto)
    val data = Source.fromFile(file).mkString.trim
    data should be(s"${DateTimeUtils.toString(dto.startDateTime)},0:00:00,project,activity")
  }
}
