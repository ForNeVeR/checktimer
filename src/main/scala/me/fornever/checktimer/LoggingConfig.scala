// SPDX-FileCopyrightText: 2026 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import scribe._
import scribe.file._
import scribe.format._

import java.nio.file.Paths

object LoggingConfig {

  def configure(): Unit = {
    val tempDir = sys.env.getOrElse("TMP", sys.props.getOrElse("java.io.tmpdir", "/tmp"))
    val logDir = Paths.get(tempDir, "checktimer")

    // File format: [{level}] {date} <{class-name}> {message}
    val fileFormat = formatter"[$level] $date <$className> $messages"

    // Console format: [{level}] {message}
    val consoleFormat = formatter"[$level] $messages"

    // Rolling file: log-YYYY-MM-DD.log with size-based rolling
    val fileWriter = FileWriter(
      logDir / ("log" % rolling("-" % daily("-")) % ".log")
    ).flushAlways

    Logger.root
      .clearHandlers()
      .withHandler(
        formatter = consoleFormat,
        writer = scribe.writer.ConsoleWriter
      )
      .withHandler(
        formatter = fileFormat,
        writer = fileWriter
      )
      .replace()
  }
}
