package testscripts

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.collection.mutable

class AppSimulation extends Simulation{

  var httpProtocols: HttpProtocolBuilder = http.baseUrl("http://localhost:3000").disableCaching.disableAutoReferer.disableWarmUp

    val test = exec(http("getHomePage")
    .get("/home")
    .check(status.is("200"))) 

    val scn = scenario("Home Page")
      .exec(test)

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocols).assertions(global.failedRequests.count.is(0))
}
