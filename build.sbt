name := "worldsheet"

version in Global := "1.0"

organization in Global := "org.waman.worldsheet"

scalaVersion in Global := "2.11.4"


//***** Custom settings *****
val javaVersion = settingKey[String]("javac source/target version")

val encoding = settingKey[String]("source encoding")

javaVersion in Global := "1.8"

encoding in Global := "UTF-8"


//***** Projects *****
lazy val root = (project in file(".")) aggregate(macroProj, core)

lazy val macroProj = (project in file("macro")).
  settings(
    scalacOptions += "-language:experimental.macros",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  )

lazy val core = project.
  dependsOn(macroProj).
  settings(
    libraryDependencies ++= Seq(
      "org.spire-math" % "spire_2.11" % "0.8.2",
      "org.apache.commons" % "commons-math3" % "3.3"
    )
  )


//***** Options & Dependencies *****
javacOptions in Global ++= Seq(
  "-source", javaVersion.value,
  "-target", javaVersion.value,
  "-encoding", encoding.value
)

scalacOptions in Global ++= Seq(
  "-deprecation",
  "-encoding", encoding.value
)

libraryDependencies in Global ++= Seq(
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  //"org.scala-lang" % "scala-swing" % "2.9.2",
  "org.scalatest" % "scalatest_2.11" % "2.2.2" % Test
)


//***** Running *****
fork in Global := true


//***** Packaging *****
//mainClass in Compile := Some("org.waman.worldsheet.Main")
//mainClass in (Compile, packageBin) := Some("org.sample.Main")
//mainClass in (Compile, run) := Some("org.sample.Main")

crossPaths in Global := false
