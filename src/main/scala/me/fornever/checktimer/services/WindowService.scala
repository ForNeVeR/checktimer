package me.fornever.checktimer.services

import scalafx.stage.Stage

trait WindowService {

  def stayOnTop(state: Boolean): Unit
}

class WindowServiceImpl(stage: Stage) extends WindowService {

  override def stayOnTop(state: Boolean): Unit = {
    stage.setAlwaysOnTop(state)
  }
}
