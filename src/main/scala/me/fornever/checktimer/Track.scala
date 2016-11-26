package me.fornever.checktimer

import java.time._

case class Track(project: String, activity: String, startDateTime: Option[ZonedDateTime] = None) {

  def start(): Track = {
    copy(startDateTime = Some(DateTimeUtils.now()))
  }

  def duration(): Option[Duration] = {
    startDateTime.map(Duration.between(_, DateTimeUtils.now()))
  }
}
