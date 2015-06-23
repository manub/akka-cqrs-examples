package net.manub.akc

import java.time.Instant

import play.api.libs.json.Json

case class Message(message: String)
object Message {
  implicit val formats = Json.format[Message]
}

case class User(user: String)
case class TimedMessage(message: String, timestamp: Instant)
object TimedMessage {
  implicit val formats = Json.format[TimedMessage]
}

// commands

case class PostMessage(user: User, message: Message)

// events

case class MessagePosted(user: User, message: Message, timestamp: Instant)