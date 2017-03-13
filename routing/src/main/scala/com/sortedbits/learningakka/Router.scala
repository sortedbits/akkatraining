package com.sortedbits.learningakka

import akka.actor.{ActorSystem, Actor, ActorRef, Props}
import com.sortedbits.learningakka.Worker.Work

class RouterPool extends Actor {

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

class RouterGroup(routees: List[String]) extends Actor {

  def receive = {
    case msg: Work =>
      println("RouterGroup received Work message...")
      context.actorSelection(routees(util.Random.nextInt(routees.size))) forward msg
  }
}
