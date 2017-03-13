package com.sortedbits.learningakka

import akka.actor.{Actor, ActorRef, Identify, ActorSelection, ActorSystem, Props, ActorIdentity}

object Watch extends App {

  val system = ActorSystem("watch-actor-selection")

  val counter = system.actorOf(Props[Counter], "counter")
  val watcher = system.actorOf(Props[Watcher], "watcher")

  Thread.sleep(1000)

  system.terminate()
}
