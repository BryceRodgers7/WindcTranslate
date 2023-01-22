name := """windcTranslate"""
organization := "com.Bryce"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += javaWs
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.11"
// libraryDependencies += "com.typesafe.play" %% "play-json" % "2.13.10"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"

// play-slick 5.0.x is currently built and tested against version 1.4.200
libraryDependencies += "com.h2database" % "h2" % "1.4.200"
// sttp 
// libraryDependencies += "com.softwaremill.sttp.client3" %% "core" % "3.8.8"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"



// libraryDependencies += "org.json4s" %% "json4s-jackson" % "2.11.0"

// libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.10"

// libraryDependencies += "com.typesafe.play" %% "play-json" % playVersion

// libraryDependencies += ws

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.Bryce.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.Bryce.binders._"
