package com.github.torczuk.io.tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class NonBlockingNumbersSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://ec2-3-1-81-147.ap-southeast-1.compute.amazonaws.com:8080")

  val addNumberScn = scenario("POST numbers")
    .exec(http("post")
      .post("/api/v1/non-blocking/numbers")
      .header("Content-Type", "application/json")
      .body(StringBody("""{"value": "10.00"}""")))


  val getStatsScn = scenario("GET stats")
    .exec(http("get stats").get("/api/v1/non-blocking/numbers"))

//  setUp(
//    addNumberScn
//      .inject(constantUsersPerSec(400) during (10 minutes))
//      .throttle(jumpToRps(10), holdFor(1 minute)) //warm up
//      .throttle(jumpToRps(500), holdFor(10 minute)), // hard work
//    getStatsScn
//      .inject(constantUsersPerSec(400) during (10 minutes))
//      .throttle(jumpToRps(1), holdFor(1 minute)) //warm up
//      .throttle(jumpToRps(1), holdFor(10 minute)), // hard work
//  ).protocols(httpProtocol)

}
