package me.fornever.checktimer

import scalafx.beans.property.ObjectProperty

class ApplicationModel {

  val currentTrack = new ObjectProperty[Track]

  def start(project: String, activity: String): Unit = {
    currentTrack.value = Track(project, activity).start()
  }

  def stop(): Unit = {
    // TODO[F]: save log (#2)
    currentTrack.value = null
  }
}
