package com.sortedbits.learningakka

import akka.actor.{Actor, ActorSystem, Props, Stash}
import com.sortedbits.learningakka.UserStorage.{Connect, Disconnect, Operation}

case class User(username: String, mail: String)

object UserStorage {

  trait DBOperation

  object DBOperation {
    case object Create extends DBOperation
    case object Update extends DBOperation
    case object Read extends DBOperation
    case object Delete extends DBOperation
  }

  case object Connect
  case object Disconnect

  case class Operation(dbOperation: DBOperation, user: Option[User])
}

class UserStorage extends Actor with Stash {

  def receive = disconnected

  def connected: Actor.Receive = {
    case Disconnect =>
      println("UserStorage disconnected from DB...")
      context.unbecome()
    case Operation(op, user) =>
      println(s"UserStorage reveived $op for user $user")
  }

  def disconnected: Actor.Receive = {
    case Connect =>
      println("UserStorage connected to DB...")
      unstashAll()
      context.become(connected)
    case _ =>
      stash()
  }
}

object BecomeHotswap extends App {
  import UserStorage._

  val system = ActorSystem("hotswap-become")
  val userStorage = system.actorOf(Props[UserStorage], "userStorage")

  userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admiin@mail.com")))
  userStorage ! Connect
  userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admiin@mail.com")))

  userStorage ! Disconnect

  Thread.sleep(100)

  system.terminate()
}
