name := "worldsheet"

version := "1.0"

organization := "org.waman.worldsheet"

scalaVersion := "2.11.2"

//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.0"
libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.11.2",
  "org.spire-math" % "spire_2.11" % "0.8.2",
  "org.apache.commons" % "commons-math3" % "3.3",
  //"org.scala-lang" % "scala-swing" % "2.9.2",
  "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-encoding", "UTF-8")

scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8")

fork := true

initialCommands in console := """println("***** Console Start! *****")"""
//initialCommands in console := "import org.sample._"

mainClass in Compile := Some("org.waman.worldsheet.Main")
//mainClass in (Compile, packageBin) := Some("org.sample.Main")
//mainClass in (Compile, run) := Some("org.sample.Main")

crossPaths := false