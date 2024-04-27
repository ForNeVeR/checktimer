// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import java.time.format.DateTimeFormatter
import java.time.{Duration, ZoneOffset, ZonedDateTime}

object DateTimeUtils {

  def now(): ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC)

  def toString(zonedDateTime: ZonedDateTime): String = DateTimeFormatter.ISO_INSTANT.format(zonedDateTime)

  def toString(duration: Duration): String = {
    val seconds = duration.getSeconds
    val minutes = (seconds / 60) % 60
    val hours = seconds / 3600
    f"$hours:$minutes%02d:${seconds % 60}%02d"
  }
}
