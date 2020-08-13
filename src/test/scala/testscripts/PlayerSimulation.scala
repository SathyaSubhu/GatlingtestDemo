package testscripts

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.collection.mutable

class PlayerSimulation extends Simulation{

  var httpProtocols: HttpProtocolBuilder = http.baseUrl("http://localhost:8019").disableCaching.disableAutoReferer.disableWarmUp

  val basicServiceHeaders: mutable.Map[String, String] = scala.collection.mutable.Map(
    HttpHeaderNames.Connection -> HttpHeaderValues.KeepAlive,
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Host -> "localhost",
    HttpHeaderNames.UserAgent -> "Gatling 3.3.1")

    val test = exec(http("createplayer")
    .post("/player")
    .headers(basicServiceHeaders.toMap)
    .body(ElFileBody("bodies/createPlayer.json")).asJson
    .check(status.is("200")))

    val scn = scenario("Create player")
      .exec(test)

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocols).assertions(global.failedRequests.count.is(0))
}
