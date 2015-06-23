package net.manub.akc

import akka.persistence.PersistentView

class QueryActor extends PersistentView {


  override def viewId: String = "view"

  override def persistenceId: String = "messages"

  override def receive: Receive = queryFrom(List.empty)

  def queryFrom(messages: List[Message]): Receive = { case _ => }
}
