// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.{Duration, ZoneOffset, ZonedDateTime}

class DateTimeUtilsSpec extends AnyFlatSpec with Matchers {

  "DateTimeUtils" should "convert a duration to string" in {
    DateTimeUtils.toString(Duration.ZERO) should be("0:00:00")
  }

  it should "convert DateTime to string" in {
    val date = ZonedDateTime.of(2016, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
    DateTimeUtils.toString(date) should be("2016-01-01T00:00:00Z")
  }

  it should "return current time in UTC" in {
    val date = DateTimeUtils.now()
    date.getZone should be(ZoneOffset.UTC)
  }
}
