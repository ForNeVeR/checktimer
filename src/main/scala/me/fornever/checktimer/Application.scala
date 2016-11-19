package me.fornever.checktimer

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.TextField
import scalafx.scene.layout.HBox
import scalafx.scene.text.Text
import scalafx.stage.StageStyle

object Application extends JFXApp {

  stage = new PrimaryStage {
    val projectField = new TextField
    val activityField = new TextField

    title = "checktimer"
    scene = new Scene {
      content = new HBox {
        alignment = Pos.Center
        children = Seq(
          new Text {
            text = "Project: "
          },
          projectField,
          new Text {
            text = "Activity: "
          },
          activityField
        )
      }
    }
  }

  stage.initStyle(StageStyle.Undecorated)
}
