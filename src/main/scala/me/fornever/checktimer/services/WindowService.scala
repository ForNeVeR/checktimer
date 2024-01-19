package me.fornever.checktimer.services

import scalafx.application.JFXApp.PrimaryStage

trait WindowService {

  def stayOnTop(state: Boolean): Unit
}

class WindowServiceImpl(stage: PrimaryStage) extends WindowService {

  override def stayOnTop(state: Boolean): Unit = {
    stage.setAlwaysOnTop(state)
  }
}
