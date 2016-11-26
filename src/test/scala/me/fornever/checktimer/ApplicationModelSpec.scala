package me.fornever.checktimer

import java.io.File

import org.scalatest._

class ApplicationModelSpec extends FlatSpec with Matchers {

  "An ApplicationModel" should "contain no Track after creation" in {
    val model = new ApplicationModel
    model.currentTrack.value should be(null)
  }

  it should "contain a freshly started Track after start" in {
    val model = new ApplicationModel
    model.start("foo", "bar")
    val track = model.currentTrack.value
    track.project should be("foo")
    track.activity should be("bar")
    track.startDateTime shouldNot be(None)
  }

  it should "contain no Track after stop" in {
    val model = new ApplicationModel
    model.start("foo", "bar")
    model.stop()
    model.currentTrack.value should be(null)
  }

  it should "contain null time before start" in {
    val model = new ApplicationModel
    model.currentTime.value should be(null)
  }

  it should "contain not null time before start" in {
    val model = new ApplicationModel
    model.start("fff", "bbb")
    model.currentTime.value shouldNot be(null)
  }

  it should "contain null time after stop" in {
    val model = new ApplicationModel
    model.start("fff", "bbb")
    model.stop()
    model.currentTime.value should be(null)
  }

  it should "contain empty project info before start" in {
    val model = new ApplicationModel
    model.currentProjectInfo.value should be("")
  }

  it should "contain formatted project info after start" in {
    val model = new ApplicationModel
    model.start("proj01", "activ02")
    model.currentProjectInfo.value should be("proj01 / activ02")
  }

  it should "contain formatted time string value" in {
    val model = new ApplicationModel
    model.start("fff", "bbb")
    model.currentTimeString.value shouldNot be(null)
  }

  it should "save track to CSV file" in {
    val file = File.createTempFile("checktimer", ".csv")
    val model = new ApplicationModel(Some(file.getAbsolutePath))
    model.start("fff", "zzz")
    model.stop()
    file.length > 0 shouldBe true
  }
}
