package com.sortedbits.learningakka

import akka.actor.{Actor, ActorRef, ActorSystem}

class Counter extends Actor {

  import Counter._

  var count = 0

  def receive = {
    case Inc(x) =>
      count += x
    case Dec(x) =>
      count -= x
  }
}

object Counter {

  final case class Inc(num: Int)
  final case class Dec(num: Int)
}
