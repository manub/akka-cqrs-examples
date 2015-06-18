package net.manub.akc

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorFlowMaterializer
import akka.util.Timeout

trait Server extends JsonProtocol {

  implicit val system = ActorSystem("akc")
  implicit val flowMaterializer = ActorFlowMaterializer()
  implicit val timeout: akka.util.Timeout = Timeout(2, SECONDS)

  val queryActor = system.actorOf(Props[QueryActor])
  val commandActor = system.actorOf(Props[CommandActor])

  Http().bindAndHandle(route, "localhost", 8080)

  def route =
    pathPrefix("post") {
      (post & entity(as[Post])) { post =>
        commandActor ! CreatePostCommand(post)
        complete(StatusCodes.Accepted)
      }
    } ~ get {
      path("lastPost") {
        onComplete((queryActor ? GetLastPost).mapTo[Post]) { lastPost =>
          complete(lastPost)
        }
      }
    }


}


object Boot extends Server with App