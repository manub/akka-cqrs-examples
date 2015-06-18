package net.manub.akc

import akka.persistence.PersistentView

class QueryActor extends PersistentView {

  var lastPost: Post = _

  override def viewId: String = "view"

  override def persistenceId: String = "posts-id"

  override def receive: Receive = {
    case PostCreatedEvent(post) =>
      lastPost = post
    case GetLastPost =>
      sender() ! lastPost
  }
}
