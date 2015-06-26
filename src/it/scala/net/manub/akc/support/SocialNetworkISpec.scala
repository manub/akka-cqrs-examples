package net.manub.akc.support

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import net.manub.akc.Server
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

trait SocialNetworkISpec
  extends WordSpec with Matchers with BeforeAndAfterAll with ScalaFutures with PlayJsonSupport with Eventually {

  implicit val actorSystem: ActorSystem = ActorSystem("test-system")
  implicit val flowMaterializer: ActorMaterializer = ActorMaterializer()
  implicit val defaultPatience = PatienceConfig(timeout = Span(2, Seconds), interval = Span(10, Millis))

  val server = new Server {}

  override def afterAll(): Unit = {
    actorSystem.shutdown()
  }

  def sendRequest(req: HttpRequest) = Source
    .single(req)
    .via(Http().outgoingConnection(host = "localhost", port = 8080))
    .runWith(Sink.head)
}
