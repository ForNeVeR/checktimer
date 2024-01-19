package me.fornever.checktimer

import javafx.animation.{KeyFrame, Timeline}
import javafx.event.ActionEvent
import me.fornever.checktimer.dto.TrackDto
import me.fornever.checktimer.services.WindowService
import org.tinylog.scala.Logger
import scalafx.animation.Animation
import scalafx.beans.binding.{Bindings, StringBinding}
import scalafx.beans.property.{BooleanProperty, ObjectProperty}

import java.time.Duration

class ApplicationModel(outFileName: Option[String] = None, windowService: WindowService) {

  val currentTrack = new ObjectProperty[Track]
  val currentTime = new ObjectProperty[Duration]
  val stayOnTop: BooleanProperty = new BooleanProperty() {
    onChange { (_, _, state) =>
      windowService.stayOnTop(state)
    }
  }

  val currentProjectInfo: StringBinding = Bindings.createStringBinding(() => {
    Option(currentTrack.value) map (t => s"${t.project} / ${t.activity}") getOrElse ""
  }, currentTrack)

  val currentTimeString: StringBinding = Bindings.createStringBinding(() => {
    Option(currentTime.value) map DateTimeUtils.toString getOrElse ""
  }, currentTime)

  protected def createTimeline(): Option[Timeline] = Some(
    new Timeline(
      new KeyFrame(javafx.util.Duration.seconds(1.0), (_: ActionEvent) => {
        Option(currentTrack.value) foreach (track => track.duration foreach (d => currentTime.value = d))
      })
    )
  )

  private val timeline = createTimeline()
  timeline.foreach(_.setCycleCount(Animation.Indefinite))

  def start(project: String, activity: String): Unit = {
    Logger.info("Starting project {} / {}", project, activity)
    currentTrack.value = Track(project, activity).start()
    currentTime.value = Duration.ZERO
    timeline.foreach(_.play())
  }

  def stop(): Unit = {
    Logger.info("Stopping project")
    timeline.foreach(_.stop())
    Option(currentTrack.value) foreach { track =>
      val trackDuration = track.duration()

      currentTrack.value = null
      currentTime.value = null

      (track.startDateTime, trackDuration) match {
        case (Some(startDateTime), Some(duration)) =>
          val dto = TrackDto(startDateTime, duration, track.project, track.activity)
          saveTime(dto)
        case _ =>
      }
    }
  }

  private def saveTime(track: TrackDto): Unit = {
    outFileName foreach { fileName =>
      Logger.info("Saving data to {}", fileName)
      CsvFile.append(fileName, track)
    }
  }
}
