
scalaVersion := "2.10.3"

name := "AegisStrategy"

version := "0.0.1-SNAPSHOT"

scalacOptions ++= Seq(
  "-deprecation"
)

javaOptions ++= Seq(
  "-Xms1G",
  "-Xmx1G",
  "-agentlib:hprof=cpu=samples,depth=50",
  "-verbose:gc",
  "-Xloggc:gc.log"
)

fork in run := true
