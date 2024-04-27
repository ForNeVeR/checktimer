// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import me.fornever.checktimer.dto.TrackDto
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.time.Duration
import scala.io.Source
import scala.util.Using

class CsvFileSpec extends AnyFlatSpec with Matchers {

  "The CsvFile" should "append the data in the proper format" in {
    val dto = TrackDto(DateTimeUtils.now(), Duration.ZERO, "project", "activity")
    val file = File.createTempFile("checktimer", ".csv")
    CsvFile.append(file.getAbsolutePath, dto)
    val data = Using.resource(Source.fromFile(file))(_.mkString.trim)
    data should be(s"${DateTimeUtils.toString(dto.startDateTime)},0:00:00,project,activity")
  }
}
