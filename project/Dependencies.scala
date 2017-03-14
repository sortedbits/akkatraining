import sbt._
import Keys._

object Dependencies {
  val commonDependencies : Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-actor_2.11" % "2.4.17"
  )

  val persistenceDependencies : Seq [ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-persistence" % "2.4.17",
    "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.16"
  )
}
