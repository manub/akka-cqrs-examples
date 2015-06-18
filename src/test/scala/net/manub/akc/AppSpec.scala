package net.manub.akc

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.duration.FiniteDuration

class AppSpec extends WordSpec with Matchers with BeforeAndAfterAll with ScalaFutures with JsonProtocol {

  implicit val actorSystem = ActorSystem("test-system")
  implicit val flowMaterializer = ActorFlowMaterializer()
  implicit val defaultPatience = PatienceConfig(timeout = Span(2, Seconds), interval = Span(5, Millis))
  import actorSystem.dispatcher

  val server = new Server {}

  def sendRequest(req: HttpRequest) = Source
    .single(req)
    .via(Http().outgoingConnection(host = "localhost", port = 8080))
    .runWith(Sink.head)

  "posting an event" should {
    "return 202 accepted" in {
      val post = Post("manub", "hello world!")
      val entity = Marshal(post).to[RequestEntity].futureValue

      whenReady(sendRequest(HttpRequest(uri = Uri("/post"), method = POST, entity = entity))) { response =>
        response.status should be(StatusCodes.Accepted)
      }
    }
  }

  "calling /lastPost" should {
    "return the post that has been posted most recently" in {

      val earlyPost = Post("manub", "old!!!")
      val lastPost = Post("manub", "a shiny new post")

      sendRequest(HttpRequest(uri = Uri("/post"), method = POST,
        entity = Marshal(earlyPost).to[RequestEntity].futureValue)).futureValue

      sendRequest(HttpRequest(uri = Uri("/post"), method = POST,
        entity = Marshal(lastPost).to[RequestEntity].futureValue)).futureValue

      whenReady(sendRequest(HttpRequest(uri = Uri("/lastPost"), method = GET))) { response =>
        Unmarshal(response.entity).to[Post].futureValue shouldBe lastPost
      }
    }
  }

}
