package cddb.ptest.simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


import cddb.ptest.config.Config.getBaseUrl
import cddb.ptest.scenarios.Scenarios._

class GetTracksSimulation extends Simulation {

  val duration = System.getProperty("duration", "10").toInt seconds
  val userRate = System.getProperty("userRate", "1").toDouble
  val albumId = System.getProperty("albumId", "1").toInt

  val environment = System.getProperty("environment", "local")
  def httpProtocol = http.baseUrl(getBaseUrl(environment)).userAgentHeader("Gatling/test")

  setUp(getTracksOfCsvAlbumsScenario.inject(constantUsersPerSec(userRate) during duration).protocols(httpProtocol))
}