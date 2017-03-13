package com.sortedbits.learningakka

import akka.actor.{ActorSystem, Props}
import com.sortedbits.learningakka.Worker.Work

object Router extends App {

  val system = ActorSystem("rotuer")

  val router = system.actorOf(Props[Router])

  router ! Work()
  router ! Work()
  router ! Work()

  Thread.sleep(100)

  system.terminate()
}
