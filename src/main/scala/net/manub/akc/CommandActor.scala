package net.manub.akc

import akka.persistence.PersistentActor

class CommandActor extends PersistentActor {

  // can this be a variable and change with the become?
  var state = Vector.empty[Post]

  def updateState(postCreatedEvent: PostCreatedEvent): Unit = {
    state :+= postCreatedEvent.post
  }

  override def receiveRecover: Receive = {
    case _ =>
  }

  override def receiveCommand: Receive = {
    case CreatePostCommand(post) =>
      persist(PostCreatedEvent(post)) { event =>
        state :+= post
      }
  }

  override def persistenceId: String = "posts-id"
}
