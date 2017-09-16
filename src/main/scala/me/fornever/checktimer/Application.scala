package me.fornever.checktimer

import java.nio.file.Paths

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.layout.{HBox, Region, VBox}
import scalafx.scene.{Cursor, Scene}
import scalafx.stage.StageStyle

object Application extends JFXApp {

  val configuration = {
    val configPath =
      parameters.unnamed match {
        case Seq(fileName) => fileName
        case _ => Paths.get(Environment.homeDirectory, "checktimer.cfg").toString
      }
    Configuration.loadFrom(configPath)
  }

  println("Arguments: " + parameters.unnamed)
  println("Configuration: " + configuration)

  stage = new PrimaryStage {
    val model = new ApplicationModel(Some(configuration.databasePath))

    def keyPress(e: KeyEvent): Unit =
      e match {
        case _ if e.code == KeyCode.Enter =>
          model.stop()
          model.start(projectField.text.value, activityField.text.value)
        case _ if e.code == KeyCode.Escape =>
          model.stop()
        case _ =>
      }

    val projectField = new TextField {
      onKeyPressed = keyPress _
    }
    val activityField = new TextField {
      onKeyPressed = keyPress _
    }

    title = "checktimer"
    scene = new Scene {
      stylesheets += getClass.getResource("/style.css").toExternalForm

      root = new VBox {
        children = Seq(
          new HBox {
            alignment = Pos.Center
            children = Seq(
              new Label("Project: "),
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

  var baseMousePosition: Option[(Double, Double)] = None

  private def saveMousePosition(event: MouseEvent): Unit = {
    baseMousePosition = Some((stage.x.value - event.screenX, stage.y.value - event.screenY))
  }

  private def removeMousePosition(event: MouseEvent): Unit = {
    baseMousePosition = None
  }

  private def moveWindow(event: MouseEvent): Unit = {
    baseMousePosition foreach { case (x0, y0) =>
      stage.x = event.screenX + x0
      stage.y = event.screenY + y0
    }
  }
}
