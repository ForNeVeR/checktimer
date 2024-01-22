package me.fornever.checktimer

import javafx.animation.Timeline
import me.fornever.checktimer.services.{StubWindowService, WindowService}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import scala.collection.mutable
import scala.concurrent.ExecutionContext

class ApplicationModelSpec extends AnyFlatSpec with Matchers {

  private val blockingExecutor = ExecutionContext.fromExecutor((command: Runnable) => command.run())
  private def newApplicationModel(outFileName: Option[String] = None,
                                  windowService: WindowService = new StubWindowService,
                                  backgroundExecutor: ExecutionContext = blockingExecutor,
                                  uiExecutor: ExecutionContext = blockingExecutor) =
    new ApplicationModel(outFileName, windowService, backgroundExecutor, uiExecutor) {
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
    withTempFile { file =>
      val model = newApplicationModel(Some(file.getAbsolutePath))
      model.start("fff", "zzz")
      model.stop()
      file.length > 0 shouldBe true
    }
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

  it should "set isSaving while saving the data" in {
    val actionQueue = mutable.Queue.empty[Runnable]
    val executor = ExecutionContext.fromExecutor((command: Runnable) => actionQueue.synchronized {
      actionQueue += command
    })

    def pumpExecutor(): Unit = {
      while (actionQueue.nonEmpty) {
        actionQueue.dequeue().run()
      }
    }

    withTempFile { file =>
      val model = newApplicationModel(Some(file.getPath), backgroundExecutor = executor, uiExecutor = executor)
      model.start("x", "y")
      model.isSaving.value should be(false)

      model.stop()
      model.isSaving.value should be(true)
      pumpExecutor()

      model.isSaving.value should be(false)
    }
  }

  def withTempFile(f: File => Unit): Unit = {
    val file = File.createTempFile("checktimer", ".csv")
    try {
      f(file)
    } finally {
      file.delete()
    }
  }
}
