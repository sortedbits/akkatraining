package com.sortedbits.learningakka

import akka.actor.{Actor, ActorRef, Props}
import com.sortedbits.learningakka.Worker.Work

class Router extends Actor {

  var routees: List[ActorRef] = _

  override def preStart(): Unit = {
    routees = List.fill(5) (
      context.actorOf(Props[Worker])
    )
  }

  def receive = {
    case msg: Work =>
      println("router received a message...")
      routees(util.Random.nextInt(routees.size)) forward msg
  }
}
