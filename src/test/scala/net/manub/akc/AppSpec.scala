package net.manub.akc

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class AppSpec extends WordSpec with Matchers with BeforeAndAfterAll with ScalaFutures with PlayJsonSupport {

  implicit val actorSystem = ActorSystem("test-system")
  implicit val flowMaterializer = ActorFlowMaterializer()
  implicit val defaultPatience = PatienceConfig(timeout = Span(2, Seconds), interval = Span(5, Millis))

  import actorSystem.dispatcher

  val server = new Server {}

  def sendRequest(req: HttpRequest) = Source
    .single(req)
    .via(Http().outgoingConnection(host = "localhost", port = 8080))
    .runWith(Sink.head)

  "a user" should {
    "be able to post a message" in {
      val user = "manub"
      val message = Message("hello world!")

      whenReady(sendRequest(HttpRequest(uri = Uri(s"/user/$user/message"),
        method = POST,
        entity = Marshal(message).to[RequestEntity].futureValue))) {

        response =>
          response.status should be(StatusCodes.Accepted)
      }
    }

    "post a message and view it on his timeline" ignore {
      val user = "manub"
      val message = Message("hello world!")

      sendRequest(HttpRequest(
        uri = Uri(s"/user/$user/message"),
        method = POST,
        entity = Marshal(message).to[RequestEntity].futureValue)
      ).futureValue

      whenReady(sendRequest(HttpRequest(uri = Uri(s"/user/$user/timeline"), method = GET))) { response =>
        val timeline = Unmarshal(response.entity).to[Seq[Message]].futureValue

        timeline shouldBe Seq(message)
      }
    }
  }


}
