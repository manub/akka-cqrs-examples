package net.manub.akc

import spray.json.DefaultJsonProtocol

trait JsonProtocol extends DefaultJsonProtocol {
  implicit val postFormat = jsonFormat2(Post.apply)
}

case class Post(user: String, message: String)
