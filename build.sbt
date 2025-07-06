ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "Assignment2",
    
    // Set MainApp as the default main class (includes both CLI and GUI)
    Compile / mainClass := Some("MainApp"),

    // ScalaFX dependencies (replaces JavaFX)
    libraryDependencies += "org.scalafx" %% "scalafx" % "18.0.2-R29",

    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "2.0.0"
  )
