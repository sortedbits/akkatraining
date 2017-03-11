import sbt._
import Keys._

object Dependencies {
  val commonDependencies : Seq[ModuleID] = Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % "2.4.17"
  )
}
