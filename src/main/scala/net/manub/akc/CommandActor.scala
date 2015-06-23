package net.manub.akc

import java.time.Instant

import akka.persistence.PersistentActor

class CommandActor extends PersistentActor {

  override def receiveRecover: Receive = {
    case _ =>
  }

  override def receiveCommand: Receive = {
    case PostMessage(user, message) =>
      persist(MessagePosted(user, message, Instant.now())) { event => }
  }

  override def persistenceId: String = "messages"
}
