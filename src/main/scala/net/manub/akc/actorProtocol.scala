package net.manub.akc

case class CreatePostCommand(post: Post)
case class PostCreatedEvent(post: Post)
case object GetLastPost