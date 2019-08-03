package com.github.torczuk.io.tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class BlockingCurrencySimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://ec2-52-221-215-253.ap-southeast-1.compute.amazonaws.com:8080")

  val downloadFile = scenario("GET file")
    .exec(http("blocking currency exchange")
      .get("/api/v1/blocking/exchange"))

  setUp(
    downloadFile
      .inject(constantUsersPerSec(100) during (10 minutes))
      .throttle(jumpToRps(10), holdFor(1 minute)) //warm up
      .throttle(jumpToRps(100), holdFor(10 minute)), // hard work
  ).protocols(httpProtocol)

}
