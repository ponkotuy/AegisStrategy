
scalaVersion := "2.10.3"

version := "0.0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.vert-x" % "vertx-platform" % "1.3.1.final"
)

scalacOptions ++= Seq(
  "-deprecation"
)

initialCommands in console := "import aegis._"
