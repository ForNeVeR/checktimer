// SPDX-FileCopyrightText: 2024-2025 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import com.jetbrains.rd.util.lifetime.{Lifetime, LifetimeDefinition, LifetimeTerminationTimeoutKind}
import me.fornever.checktimer.services.WindowServiceImpl
import org.tinylog.scala.Logger
import scalafx.Includes._
import scalafx.animation.{Animation, FadeTransition}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Label, TextField, ToggleButton}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.layout.{HBox, Priority, Region, VBox}
import scalafx.scene.text.Font
import scalafx.scene.{Cursor, Scene}
import scalafx.stage.StageStyle

import java.nio.file.Paths
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object Application extends JFXApp3 {

  private def loadConfiguration() = {
    val configPath =
      parameters.unnamed.toArray match {
        case Array(fileName) => fileName
        case _ => Paths.get(Environment.homeDirectory, "checktimer.cfg").toString
      }
    Configuration.loadFrom(configPath)
  }

  private def createModel(lifetime: Lifetime, configuration: Configuration, stage: PrimaryStage) = {
    val windowService = new WindowServiceImpl(stage)

    val ioThread = Executors.newSingleThreadExecutor()
    lifetime.onTermination(() => ioThread.shutdown())

    val backgroundExecutor = ExecutionContext.fromExecutor(ioThread)
    val uiExecutor = ExecutionContext.fromExecutor((command: Runnable) => Platform.runLater(command))
    new ApplicationModel(Some(configuration.databasePath), windowService, backgroundExecutor, uiExecutor)
  }

  private val ld = {
    val x = new LifetimeDefinition()
    x.setTerminationTimeoutKind(LifetimeTerminationTimeoutKind.ExtraLong)
    x
  }

  override def start(): Unit = {
    val configuration = loadConfiguration()

    Logger.info("Arguments: {}", parameters.unnamed)
    Logger.info("Configuration: {}", configuration)

    stage = new PrimaryStage {
      private val model = createModel(ld.getLifetime, configuration, this)

      def keyPress(e: KeyEvent): Unit =
        e match {
          case _ if e.code == KeyCode.Enter =>
            model.stop()
            model.start(projectField.text.value, activityField.text.value)
          case _ if e.code == KeyCode.Escape =>
            model.stop()
          case _ =>
        }

      private val stayOnTopButton = new ToggleButton("", Resources.pinIcon) {
        tooltip = "Stay on Top"
        selected.bindBidirectional(model.stayOnTop)
      }
      private val projectField = new TextField {
        onKeyPressed = keyPress _
      }
      private val activityField = new TextField {
        onKeyPressed = keyPress _
      }

      title = "checktimer"

      private val savingLabel = new Label("Saving…") {
        minWidth = Region.USE_PREF_SIZE
        visible <== model.isSaving
        managed <== visible

        private val flashingAnimation = new FadeTransition(1.0.s, this)
        flashingAnimation.setFromValue(0.3)
        flashingAnimation.setToValue(1.0)
        flashingAnimation.setCycleCount(Animation.Indefinite)
        flashingAnimation.setAutoReverse(true)
        flashingAnimation.play()
      }

      scene = new Scene {
        stylesheets += getClass.getResource("/style.css").toExternalForm

        private val em = Font.default.getSize
        HBox.setMargin(stayOnTopButton, Insets(0, 0, 0, -0.5 * em))

        root = new VBox {
          children = Seq(
            new HBox {
              alignment = Pos.Center
              children = Seq(
                stayOnTopButton,
                new Label("Project: ") {
                  styleClass += "project-label"
                },
                projectField,
                new Label(" Activity: "),
                activityField
              )
            },
             new HBox {
              alignment = Pos.CenterRight
              children = Seq(
               savingLabel,
                new Region {
                  hgrow = Priority.Always
                },
                new Label("Current: ") {
                  minWidth = Region.USE_PREF_SIZE
                },
                new Label {
                  styleClass += "changeable-text"
                  text <== model.currentProjectInfo
                },
                new Label(", timing: ") {
                  minWidth = Region.USE_PREF_SIZE
                },
                new Label {
                  styleClass ++= Seq("changeable-text")
                  text <== model.currentTimeString
                  minWidth = Region.USE_PREF_SIZE
                }
              )
            }
          )
        }

        onMousePressed = saveMousePosition _
        onMouseReleased = removeMousePosition _
        onMouseDragged = moveWindow _

        cursor = Cursor.Move
      }

      onCloseRequest = _ => model.stop()
    }

    stage.initStyle(StageStyle.Undecorated)
    stage.icons.add(Resources.checktimerIcon)
  }

  override def stopApp(): Unit = {
    Logger.info("Stopping application.")
    ld.terminate(true)
    Logger.info("Goodbye!")
  }

  private var baseMousePosition: Option[(Double, Double)] = None

  private def saveMousePosition(event: MouseEvent): Unit = {
    baseMousePosition = Some((stage.x.value - event.screenX, stage.y.value - event.screenY))
  }

  private def removeMousePosition(): Unit = {
    baseMousePosition = None
  }

  private def moveWindow(event: MouseEvent): Unit = {
    baseMousePosition foreach { case (x0, y0) =>
      stage.x = event.screenX + x0
      stage.y = event.screenY + y0
    }
  }
}
