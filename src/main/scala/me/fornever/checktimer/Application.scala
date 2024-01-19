package me.fornever.checktimer

import me.fornever.checktimer.services.WindowServiceImpl
import org.tinylog.scala.Logger
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Label, TextField, ToggleButton}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.layout.{HBox, Region, VBox}
import scalafx.scene.text.Font
import scalafx.scene.{Cursor, Scene}
import scalafx.stage.StageStyle

import java.nio.file.Paths

object Application extends JFXApp {

  private val configuration = {
    val configPath =
      parameters.unnamed match {
        case Seq(fileName) => fileName
        case _ => Paths.get(Environment.homeDirectory, "checktimer.cfg").toString
      }
    Configuration.loadFrom(configPath)
  }

  Logger.info("Arguments: {}", parameters.unnamed)
  Logger.info("Configuration: {}", configuration)

  stage = new PrimaryStage {
    val windowService = new WindowServiceImpl(this)
    val model = new ApplicationModel(Some(configuration.databasePath), windowService)

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

    onCloseRequest = handle {
      model.stop()
    }
  }

  stage.initStyle(StageStyle.Undecorated)
  stage.icons.add(Resources.checktimerIcon)

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
