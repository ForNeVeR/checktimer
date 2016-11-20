package me.fornever.checktimer

import java.nio.file.Paths

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.TextField
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.text.Text
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
      content = new VBox {
        children = Seq(
          new HBox {
            alignment = Pos.Center
            children = Seq(
              new Text("Project: "),
              projectField,
              new Text("Activity: "),
              activityField
            )
          },
          new HBox {
            alignment = Pos.CenterRight
            children = Seq(
              new Text("Current project: "),
              new Text {
                text <== model.currentProjectInfo
              },
              new Text(", timing: "),
              new Text {
                text <== model.currentTimeString
              }
            )
          }
        )
      }
    }

    onCloseRequest = handle {
      model.stop()
    }
  }

  stage.initStyle(StageStyle.Undecorated)
}
