package me.fornever.checktimer

import javafx.animation.Timeline
import me.fornever.checktimer.services.{StubWindowService, WindowService}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File

class ApplicationModelSpec extends AnyFlatSpec with Matchers {

  private def newApplicationModel(outFileName: Option[String] = None,
                                  windowService: WindowService = new StubWindowService) =
    new ApplicationModel(outFileName, windowService) {
    override protected def createTimeline(): Option[Timeline] = None
  }

  "An ApplicationModel" should "contain no Track after creation" in {
    val model = newApplicationModel()
    model.currentTrack.value should be(null)
  }

  it should "contain a freshly started Track after start" in {
    val model = newApplicationModel()
    model.start("foo", "bar")
    val track = model.currentTrack.value
    track.project should be("foo")
    track.activity should be("bar")
    track.startDateTime shouldNot be(None)
  }

  it should "contain no Track after stop" in {
    val model = newApplicationModel()
    model.start("foo", "bar")
    model.stop()
    model.currentTrack.value should be(null)
  }

  it should "contain null time before start" in {
    val model = newApplicationModel()
    model.currentTime.value should be(null)
  }

  it should "contain not null time before start" in {
    val model = newApplicationModel()
    model.start("fff", "bbb")
    model.currentTime.value shouldNot be(null)
  }

  it should "contain null time after stop" in {
    val model = newApplicationModel()
    model.start("fff", "bbb")
    model.stop()
    model.currentTime.value should be(null)
  }

  it should "contain empty project info before start" in {
    val model = newApplicationModel()
    model.currentProjectInfo.value should be("")
  }

  it should "contain formatted project info after start" in {
    val model = newApplicationModel()
    model.start("proj01", "activ02")
    model.currentProjectInfo.value should be("proj01 / activ02")
  }

  it should "contain formatted time string value" in {
    val model = newApplicationModel()
    model.start("fff", "bbb")
    model.currentTimeString.value shouldNot be(null)
  }

  it should "save track to CSV file" in {
    val file = File.createTempFile("checktimer", ".csv")
    val model = newApplicationModel(Some(file.getAbsolutePath))
    model.start("fff", "zzz")
    model.stop()
    file.length > 0 shouldBe true
  }

  it should "call WindowService.stayOnTop on the corresponding property change" in {
    var currentValue = false
    val model = newApplicationModel(windowService = (state: Boolean) => currentValue = state)

    currentValue should be(false)
    model.stayOnTop.value = true
    currentValue should be(true)
    model.stayOnTop.value = false
    currentValue should be(false)
  }
}
