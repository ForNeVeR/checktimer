// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer.dto

import me.fornever.checktimer.DateTimeUtils

import java.time.{Duration, ZonedDateTime}

case class TrackDto(startDateTime: ZonedDateTime, duration: Duration, project: String, activity: String) {
  lazy val toVector: Vector[String] = Vector(
    DateTimeUtils.toString(startDateTime),
    DateTimeUtils.toString(duration),
    project,
    activity)
}
