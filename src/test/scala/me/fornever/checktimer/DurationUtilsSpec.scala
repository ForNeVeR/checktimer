package me.fornever.checktimer

import org.scalatest.{FlatSpec, Matchers}

import java.time.Duration

class DurationUtilsSpec extends FlatSpec with Matchers {

  "DurationUtils" should "convert duration to string" in {
    DurationUtils.toString(Duration.ZERO) should be("0:00:00")
  }
}
