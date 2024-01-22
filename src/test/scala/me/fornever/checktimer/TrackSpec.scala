package me.fornever.checktimer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TrackSpec extends AnyFlatSpec with Matchers {

  "A Track" should "obtain the date after the start" in {
    var track = Track("foo", "bar")
    track.startDateTime should be(None)
    val startDateTime = DateTimeUtils.now()
    track = track.start()
    track.startDateTime shouldNot be(None)
    val result = track.startDateTime.get.compareTo(startDateTime) >= 0
    result should be(true)
  }
}
