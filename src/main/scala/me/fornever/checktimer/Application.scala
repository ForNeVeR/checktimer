package me.fornever.checktimer

import java.nio.file.Paths

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.{Cursor, Scene}
import scalafx.stage.StageStyle

object Application extends JFXApp {

  val homeDirectory = System.getProperty("user.home")
  val outFileName = {
    parameters.unnamed match {
      case Seq(fileName) => fileName
      case _ => Paths.get(homeDirectory, "checktimer.csv").toString
    }
  }

  println("Arguments: " + parameters.unnamed)
  println("Target file: " + outFileName)

  stage = new PrimaryStage {
    val model = new ApplicationModel(Some(outFileName))

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
              new Label("Current project: "),
              new Label {
                styleClass += "changeable-text"
                text <== model.currentProjectInfo
              },
              new Label(", timing: "),
              new Label {
                styleClass += "changeable-text"
                text <== model.currentTimeString
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
