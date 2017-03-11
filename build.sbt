
name := "akkatraining-root"

lazy val commonSettings = Seq(
  organization := "com.sortedbits.akkatraining", 
  version := "0.0.1-SNAPSHOT",    
  scalaVersion := "2.11.8",      
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:implicitConversions")
)

lazy val helloAkka = (project in file("hello-akka"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= Dependencies.commonDependencies)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .aggregate(helloAkka)
