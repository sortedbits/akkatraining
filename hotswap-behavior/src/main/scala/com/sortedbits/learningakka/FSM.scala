package com.sortedbits.learningakka

import akka.actor.{ActorSystem, FSM, Props, Stash}

object UserStorageFSM {

  sealed trait State
  case object Connected extends State
  case object Disconnected extends State

  sealed trait Data
  case object EmptyData extends Data

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

  case class User(username: String, mail: String)
}

class UserStorageFSM extends FSM[UserStorageFSM.State, UserStorageFSM.Data] with Stash {
  import UserStorageFSM._

  startWith(Disconnected, EmptyData)

  when(Disconnected) {
    case Event(Connect, _) =>
      println("UserStorage connected to DB")
      unstashAll()
      goto(Connected) using (EmptyData)
    case Event(_, _) =>
      stash
      stay using (EmptyData)
  }

  when(Connected) {
    case Event(Disconnect, _) =>
      println("UserStorage disconnected from DB")
      goto(Disconnected) using (EmptyData)
    case Event(Operation(op, user), _) =>
      println(s"UserStorage received $op operation for user $user")
      stay using (EmptyData)
  }

  initialize()
}

object FiniteStateMachine extends App {
  import UserStorageFSM._

  val system = ActorSystem("hotswap-fsm")
  val userStorage = system.actorOf(Props[UserStorageFSM], "user-storage-fsm")

  userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admin@mail.com")))
  userStorage ! Connect
  userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admin@mail.com")))
  userStorage ! Disconnect

  Thread.sleep(100)

  system.terminate()
}
