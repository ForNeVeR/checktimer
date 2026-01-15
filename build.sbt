// SPDX-FileCopyrightText: 2024-2026 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

organization := "me.fornever"
name := "checktimer"
version := "2.0.2"
scalaVersion := "3.8.0"
maintainer := "friedrich@fornever.me"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.github.hervegirod" % "fxsvgimage" % "1.4",
  "com.github.tototoshi" %% "scala-csv" % "2.0.0",
  "com.jetbrains.rd" % "rd-core" % "2025.3.1",
  "com.typesafe" % "config" % "1.4.5",
  "org.scalafx" %% "scalafx" % "24.0.2-R36",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "com.outr" %% "scribe" % "3.15.2",
  "com.outr" %% "scribe-file" % "3.15.2"
)

enablePlugins(JavaAppPackaging)
enablePlugins(LauncherJarPlugin)

scalacOptions += "-Werror"
