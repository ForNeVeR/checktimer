package me.fornever.checktimer

import java.time.Duration

object DurationUtils {

  def toString(duration: Duration): String = {
    val seconds = duration.getSeconds
    val minutes = (seconds / 60) % 60
    val hours = seconds / 3600
    f"$hours:$minutes%02d:${seconds % 60}%02d"
  }
}
