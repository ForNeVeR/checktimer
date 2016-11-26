package me.fornever.checktimer

import java.time.Duration
import javafx.animation.{KeyFrame, Timeline}
import javafx.event.{ActionEvent, EventHandler}

import me.fornever.checktimer.dto.TrackDto

import scalafx.animation.Animation
import scalafx.beans.binding.{Bindings, StringBinding}
import scalafx.beans.property.ObjectProperty

class ApplicationModel(outFileName: Option[String] = None) {

  val currentTrack = new ObjectProperty[Track]
  val currentTime = new ObjectProperty[Duration]

  val currentProjectInfo: StringBinding = Bindings.createStringBinding(() => {
    Option(currentTrack.value) map (t => s"${t.project} / ${t.activity}") getOrElse ""
  }, currentTrack)

  val currentTimeString: StringBinding = Bindings.createStringBinding(() => {
    Option(currentTime.value) map DateTimeUtils.toString getOrElse ""
  }, currentTime)

  private val timeline = new Timeline(
    new KeyFrame(javafx.util.Duration.seconds(1.0), new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = {
        Option(currentTrack.value) foreach (track => track.duration foreach (d => currentTime.value = d))
      }
    }))
  timeline.setCycleCount(Animation.INDEFINITE)

  def start(project: String, activity: String): Unit = {
    println(s"Starting project $project / $activity")
    currentTrack.value = Track(project, activity).start()
    currentTime.value = Duration.ZERO
    timeline.play()
  }

  def stop(): Unit = {
    println("Stopping project")
    timeline.stop()
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
      println(s"Saving data to $fileName")
      CsvFile.append(fileName, track)
    }
  }
}
