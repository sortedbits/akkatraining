package com.sortedbits.learningakka

import akka.actor.{ActorSystem, Props}

import com.typesafe.config.ConfigFactory

object MembersService extends App {

  val config = ConfigFactory.load.getConfig("MembersService")
  val system = ActorSystem("MembersService", config)
  val worker = system.actorOf(Props[Worker], "remote-actor")

  println(s"Worker actor path is ${worker.path}")
}


object MemberServiceLookup extends App {

  val config = ConfigFactory.load.getConfig("MemberServiceLookup")
  val system = ActorSystem("MemberServiceLookup", config)
  val worker = system.actorSelection("akka.tcp://MembersService@localhost:2552/user/remote-worker")

  worker ! Worker.Work("Hi Remote Actor")
}

object MembersServiceRemoteCreation extends App {

  val config = ConfigFactory.load.getConfig("MembersServiceRemoteCreation")
  val system = ActorSystem("MembersServiceRemoteCreation", config)
  val workerActor = system.actorOf(Props[Worker], "workerActorRemote")

  println(s"The remote path of worker Actor is ${workerActor.path}")

  workerActor ! Worker.Work("Hi Remote Worker")
}
