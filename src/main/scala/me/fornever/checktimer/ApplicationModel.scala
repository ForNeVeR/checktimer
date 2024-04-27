// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

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
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ApplicationModel(outFileName: Option[String] = None,
                       windowService: WindowService,
                       backgroundExecutor: ExecutionContext,
                       uiExecutor: ExecutionContext) {

  val isSaving = new BooleanProperty()
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
          saveTimeAsync(dto)
        case _ =>
      }
    }
  }

  private def saveTimeAsync(track: TrackDto): Unit = {
    outFileName foreach { fileName =>
      Logger.info("Saving data to {}.", fileName)
      isSaving.value = true
      // Note that this relies on linearity of the executor and on the fact that all the save operation is performed in
      // a single chunk of blocking IO. Update to a better strategy if needed, to keep the file action queue in order.
      Future {
        CsvFile.append(fileName, track)
      }(backgroundExecutor).onComplete(result => {
        isSaving.value = false
        result match {
          case Failure(exception) => Logger.error(exception, "Failed to save data.")
          case Success(_) =>
        }
      })(uiExecutor)
    }
  }
}
