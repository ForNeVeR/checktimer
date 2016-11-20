package me.fornever.checktimer

import java.time.Duration
import javafx.animation.{KeyFrame, Timeline}
import javafx.event.{ActionEvent, EventHandler}

import scalafx.animation.Animation
import scalafx.beans.binding.Bindings
import scalafx.beans.property.ObjectProperty

class ApplicationModel {

  val currentTrack = new ObjectProperty[Track]
  val currentTime = new ObjectProperty[Duration]

  val currentProjectInfo = Bindings.createStringBinding(() => {
    Option(currentTrack.value) map (t => s"${t.project} / ${t.activity}") getOrElse ""
  }, currentTrack)

  val currentTimeString = Bindings.createStringBinding(() => {
    Option(currentTime.value) map { value =>
      val seconds = value.getSeconds
      val minutes = (seconds / 60) % 60
      val hours = seconds / 3600
      f"$hours:$minutes%02d:${seconds % 60}%02d"
    } getOrElse ""
  }, currentTime)

  private val timeline = new Timeline(
    new KeyFrame(javafx.util.Duration.seconds(5.0), new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = {
        Option(currentTrack.value) foreach (track => track.duration foreach (d => currentTime.value = d))
      }
    }))
  timeline.setCycleCount(Animation.INDEFINITE)

  def start(project: String, activity: String): Unit = {
    currentTrack.value = Track(project, activity).start()
    currentTime.value = Duration.ZERO
    timeline.play()
  }

  def stop(): Unit = {
    // TODO[F]: save log (#2)
    timeline.stop()
    currentTrack.value = null
    currentTime.value = null
  }
}
