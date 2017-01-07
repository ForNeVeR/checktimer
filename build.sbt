name := "checktimer"
version := "0.0.1"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.github.tototoshi" %% "scala-csv" % "1.3.4",
  "com.jsuereth" %% "scala-arm" % "2.0",
  "com.typesafe" % "config" % "1.3.1",
  "org.scalafx" %% "scalafx" % "8.0.92-R10",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

fork := true
