
scalaVersion := "2.10.3"

name := "AegisStrategy"

version := "0.0.1-SNAPSHOT"

scalacOptions ++= Seq(
  "-deprecation"
)

javaOptions ++= Seq(
  "-Xms1G",
  "-Xmx1G"
)

fork in run := true
