package com.sortedbits.learningakka

import akka.actor.{ActorLogging, Props}
import akka.persistence.{PersistentActor, SnapshotOffer}

object Counter {

  sealed trait Operation {
    val count: Int
  }

  case class Increment(override val count: Int) extends Operation
  case class Decrement(override val count: Int) extends Operation

  case class Cmd(op: Operation)
  case class Evt(op: Operation)

  case class State(count: Int)
}

class Counter extends PersistentActor with ActorLogging {

  import Counter._

  println("Starting...")

  override def persistenceId = "counter-example"

  var state: State = State(count = 0)

  def updateState(evt: Evt): Unit = evt match {
    case Evt(Increment(count)) =>
      state = State(count = state.count + count)
    case Evt(Decrement(count)) =>
      state = State(count = state.count - count)
  }

  val receiveRecover: Receive = {
    case evt: Evt =>
      println(s"Counter received $evt on recovering mood")
      updateState(evt)
    case SnapshotOffer(_, snapshot: State) =>
      println(s"Counter received snapshot with data $snapshot on recovering mood")
      state = snapshot
  }

  val receiveCommand: Receive = {
    case cmd @ Cmd(op) =>
      println(s"Counter received $cmd")
      persist(Evt(op)) { evt =>
        updateState(evt)
      }
    case "print" =>
      println(s"The current state of counter is $state")
  }
}
