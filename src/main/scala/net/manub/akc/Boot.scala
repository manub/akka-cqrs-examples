package net.manub.akc

import java.util.concurrent.TimeUnit.SECONDS

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorFlowMaterializer
import akka.util.Timeout
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

trait Server extends PlayJsonSupport {

  import Message._
  import TimedMessage._

  implicit val system = ActorSystem("akc")
  implicit val flowMaterializer = ActorFlowMaterializer()
  implicit val timeout: akka.util.Timeout = Timeout(2, SECONDS)

  val queryActor = system.actorOf(Props[QueryActor])
  val commandActor = system.actorOf(Props[CommandActor])

  Http().bindAndHandle(route, "localhost", 8080)

  def route =
    pathPrefix("user" / Segment) { user =>
      path("message") {
        post {
          entity(as[Message]) { message =>
            commandActor ! PostMessage(User(user), message)
            complete(StatusCodes.Accepted)
          }
        }
      } ~ path("timeline") {
        get {
          complete(Seq.empty[TimedMessage])
        }
      }


    }
}


object Boot extends Server with App