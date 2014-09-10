name := "worldsheet"

version := "1.0"

organization := "org.waman.worldsheet"

scalaVersion := "2.11.2"

//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.0"
libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.3",
  //"org.scala-lang" % "scala-swing" % "2.9.2",
  "junit" % "junit" % "4.12-beta-1" % "test"
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-encoding", "UTF-8")

scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8")

fork := true

initialCommands in console := """println("***** Console Start! *****")"""
//initialCommands in console := "import org.sample._"

mainClass in Compile := Some("org.sample.Main")
//mainClass in (Compile, packageBin) := Some("org.sample.Main")
//mainClass in (Compile, run) := Some("org.sample.Main")

crossPaths := false