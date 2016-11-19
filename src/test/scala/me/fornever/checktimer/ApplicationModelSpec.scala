package me.fornever.checktimer

import org.scalatest._

class ApplicationModelSpec extends FlatSpec with Matchers {

  "An ApplicationModel" should "contain no Track after creation" in {
    val model = new ApplicationModel
    model.currentTrack.value should be (null)
  }

  it should "contain a freshly started Track after start" in {
    val model = new ApplicationModel
    model.start("foo", "bar")
    val track = model.currentTrack.value
    track.project should be ("foo")
    track.activity should be ("bar")
    track.startDateTime shouldNot be (None)
  }

  it should "contain no Track after stop" in {
    val model = new ApplicationModel
    model.start("foo", "bar")
    model.stop()
    model.currentTrack.value should be (null)
  }
}
