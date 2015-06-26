package net.manub.akc

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import net.manub.akc.support.SocialNetworkISpec

import scala.language.postfixOps

class AppSpec extends SocialNetworkISpec {

  import actorSystem.dispatcher

  "a user" should {

    "be able post a message and view it on his timeline" in {
      val user = "manub"
      val message = Message("hello world!")

      val postResponse = sendRequest(HttpRequest(
        uri = Uri(s"/user/$user/message"),
        method = POST,
        entity = Marshal(message).to[RequestEntity].futureValue)
      ).futureValue

      postResponse.status shouldBe StatusCodes.Accepted

      eventually {
        whenReady(sendRequest(HttpRequest(uri = Uri(s"/user/$user/timeline"), method = GET))) { response =>
          val timeline = Unmarshal(response.entity).to[Timeline].futureValue
          timeline.messages.map(_.text) shouldBe Seq(message.text)
        }
      }
    }
  }


}
