// SPDX-FileCopyrightText: 2024-2025 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

organization := "me.fornever"
name := "checktimer"
version := "2.0.2"
scalaVersion := "2.13.16"
maintainer := "friedrich@fornever.me"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.github.hervegirod" % "fxsvgimage" % "1.4",
  "com.github.tototoshi" %% "scala-csv" % "2.0.0",
  "com.jetbrains.rd" % "rd-core" % "2025.2.3",
  "com.typesafe" % "config" % "1.4.5",
  "org.scalafx" %% "scalafx" % "24.0.2-R36",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "org.tinylog" % "tinylog-impl" % "2.7.0",
  "org.tinylog" %% "tinylog-api-scala" % "2.7.0"
)

enablePlugins(JavaAppPackaging)
enablePlugins(LauncherJarPlugin)
