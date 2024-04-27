// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import com.github.tototoshi.csv.CSVWriter
import me.fornever.checktimer.dto.TrackDto

import scala.util.Using

object CsvFile {

  def append(path: String, track: TrackDto): Unit = {
    Using.resource(CSVWriter.open(path, append = true)) { writer =>
      writer.writeRow(track.toVector)
    }
  }
}
