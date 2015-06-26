package net.manub.akc

import akka.persistence.PersistentView

class QueryActor extends PersistentView {

  override def viewId: String = "view"

  override def persistenceId: String = "messages"

  override def receive: Receive = queryFrom(List.empty)

  def queryFrom(messages: List[TimedMessage]): Receive = { 
    case MessagePosted(user, message, timestamp) => 
      context.become(queryFrom(messages :+ TimedMessage(message.text, timestamp)))

    case GetMessages =>
      sender() ! Timeline(messages)
  }
}
