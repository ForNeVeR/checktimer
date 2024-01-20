name := "checktimer"
version := "0.0.1"
scalaVersion := "2.12.18"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.github.hervegirod" % "fxsvgimage" % "1.1",
  "com.github.tototoshi" %% "scala-csv" % "1.3.4",
  "com.jsuereth" %% "scala-arm" % "2.0",
  "com.typesafe" % "config" % "1.3.1",
  "org.scalafx" %% "scalafx" % "12.0.2-R18",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.tinylog" % "tinylog-impl" % "2.6.2",
  "org.tinylog" %% "tinylog-api-scala" % "2.6.2"
)

libraryDependencies ++= {
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "12" classifier osName)
}

fork := true

enablePlugins(JavaAppPackaging)
