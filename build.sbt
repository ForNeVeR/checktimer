// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

organization := "me.fornever"
name := "checktimer"
version := "2.0.0"
scalaVersion := "2.13.15"
maintainer := "friedrich@fornever.me"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.github.hervegirod" % "fxsvgimage" % "1.1",
  "com.github.tototoshi" %% "scala-csv" % "2.0.0",
  "com.typesafe" % "config" % "1.4.3",
  "org.scalafx" %% "scalafx" % "22.0.0-R33",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "org.tinylog" % "tinylog-impl" % "2.7.0",
  "org.tinylog" %% "tinylog-api-scala" % "2.7.0"
)

enablePlugins(JavaAppPackaging)
enablePlugins(LauncherJarPlugin)
