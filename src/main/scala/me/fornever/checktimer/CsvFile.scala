package me.fornever.checktimer

import com.github.tototoshi.csv.CSVWriter
import me.fornever.checktimer.dto.TrackDto
import resource.managed

object CsvFile {

  def append(path: String, track: TrackDto): Unit = {
    for (writer <- managed(CSVWriter.open(path, append = true))) {
      writer.writeRow(track.toVector)
    }
  }
}
