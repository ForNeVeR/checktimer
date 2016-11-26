package me.fornever.checktimer

import java.time.{Duration, ZoneOffset, ZonedDateTime}

import org.scalatest.{FlatSpec, Matchers}

class DateTimeUtilsSpec extends FlatSpec with Matchers {

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
