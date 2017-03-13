package com.sortedbits.learningakka

import akka.actor.{Actor, ActorRef, Identify, ActorSelection, ActorSystem, Props, ActorIdentity}

class Watcher extends Actor {

  var counterRef: ActorRef = _

  val selection = context.actorSelection("/user/counter")

  selection ! Identify(None)

  def receive = {
    case ActorIdentity(_, Some(ref)) =>
      println(s"Actor reference for actor counter is $ref")
    case ActorIdentity(_, None) =>
      println("Actor selection for actor doesn't live :(")
  }
}
