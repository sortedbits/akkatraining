package com.sortedbits.learningakka

import akka.actor.{ActorSystem, Props, PoisonPill}

object ActorPath extends App {

  val system = ActorSystem("actor-paths")

  val counter1 = system.actorOf(Props[Counter], "counter")
  println(s"actor reference for counter1: $counter1")

  val counterSelection1 = system.actorSelection("counter")
  println(s"actor selection for counter1: $counterSelection1")

  counter1 ! PoisonPill

  Thread.sleep(100)

  val counter2 = system.actorOf(Props[Counter], "counter")
  println(s"actor reference for counter2: $counter2")

  val counterSelection2 = system.actorSelection("counter")
  println(s"actor selection for counter2: $counterSelection2")

  system.terminate()
}
