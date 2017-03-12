package com.sortedbits.learningakka

import akka.actor.{Actor, ActorSystem, Props}
import com.sortedbits.learningakka.MusicController.{Play, Stop}
import com.sortedbits.learningakka.MusicPlayer.{StartMusic, StopMusic}

object MusicController {
  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg

  def props = Props[MusicController]
}

class MusicController extends Actor {
  def receive = {
    case Play =>
      println("Music started ...")
    case Stop =>
      println("Music stopped ...")
  }
}

object MusicPlayer {
  sealed trait PlayMsg
  case object StopMusic extends PlayMsg
  case object StartMusic extends PlayMsg
}

class MusicPlayer extends Actor {
  def receive = {
    case StopMusic =>
      println("I don't want to stop music")
    case StartMusic =>
      //val controller = context.actorOf(Props[MusicController], "controller")
      val controller = context.actorOf(MusicController.props, "controller")
      controller ! Play
    case _ =>
      println("Unknown message")
  }
}

object Creation extends App {
  val system = ActorSystem("creation")
  val player = system.actorOf(Props[MusicPlayer], "player")

  player ! StartMusic

  // stop MusicController

  system.terminate()
}
