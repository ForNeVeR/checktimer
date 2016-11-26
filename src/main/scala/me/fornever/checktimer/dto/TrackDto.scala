package me.fornever.checktimer.dto

import java.time.{Duration, ZonedDateTime}

import me.fornever.checktimer.DateTimeUtils

case class TrackDto(startDateTime: ZonedDateTime, duration: Duration, project: String, activity: String) {
  lazy val toVector: Vector[String] = Vector(
    DateTimeUtils.toString(startDateTime),
    DateTimeUtils.toString(duration),
    project,
    activity)
}
