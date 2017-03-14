package com.sortedbits.learningakka

import akka.actor.{Actor}

class Worker extends Actor {

  import Worker._

  def receive = {
    case msg: Work =>
      println(s"actor $self received Work message")
  } 
}

object Worker {
  case class Work(message: String)
}
