name := """windcTranslate"""
organization := "com.Bryce"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += javaWs
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.11"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"

// play-slick 5.0.x is currently built and tested against version 1.4.200
libraryDependencies += "com.h2database" % "h2" % "1.4.200"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.Bryce.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.Bryce.binders._"
