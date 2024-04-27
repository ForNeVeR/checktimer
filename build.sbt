name := "checktimer"
version := "1.0.0"
scalaVersion := "2.13.12"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.github.hervegirod" % "fxsvgimage" % "1.1",
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  "com.typesafe" % "config" % "1.4.3",
  "org.scalafx" %% "scalafx" % "21.0.0-R32",
  "org.scalatest" %% "scalatest" % "3.2.18" % "test",
  "org.tinylog" % "tinylog-impl" % "2.6.2",
  "org.tinylog" %% "tinylog-api-scala" % "2.6.2"
)

enablePlugins(JavaAppPackaging)
