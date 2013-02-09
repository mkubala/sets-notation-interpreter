import sbt._
import sbt.Keys._

object SetsnotationinterpreterBuild extends Build {

  lazy val setsnotationinterpreter = Project(
    id = "setsnotationinterpreter",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "SetsNotationInterpreter",
      organization := "pl.mkubala.sets",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.0",
      libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
      libraryDependencies += "org.scala-lang" % "jline" % "2.10.0"
    )
  )
}
