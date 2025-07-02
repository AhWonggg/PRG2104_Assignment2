ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "Assignment2",

    libraryDependencies ++= {
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "21.0.4")
    },

    libraryDependencies += "org.scalafx" %% "scalafx" % "24.0.0-R35",

    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "2.0.0"
  )
