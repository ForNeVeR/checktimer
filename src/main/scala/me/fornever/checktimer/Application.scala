package me.fornever.checktimer

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.TextField
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.layout.HBox
import scalafx.scene.text.Text
import scalafx.stage.StageStyle

object Application extends JFXApp {

  stage = new PrimaryStage {
    val model = new ApplicationModel

    def keyPress(e: KeyEvent): Unit =
      e match {
        case _ if e.code == KeyCode.Enter =>
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
      content = new HBox {
        alignment = Pos.Center
        children = Seq(
          new Text("Project: "),
          projectField,
          new Text("Activity: "),
          activityField
        )
      }
    }
  }

  stage.initStyle(StageStyle.Undecorated)
}
