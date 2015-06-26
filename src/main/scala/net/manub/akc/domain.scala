package net.manub.akc

import java.time.Instant

import play.api.libs.json.Json

case class Message(text: String)

object Message {
  implicit val formats = Json.format[Message]
}

case class User(user: String)

case class TimedMessage(text: String, timestamp: Instant)

object TimedMessage {
  implicit val formats = Json.format[TimedMessage]
}

case class Timeline(messages: Seq[TimedMessage])

object Timeline {
  implicit val formats = Json.format[Timeline]
}

// commands

case class PostMessage(user: User, message: Message)

case object GetMessages

// events

case class MessagePosted(user: User, message: Message, timestamp: Instant)