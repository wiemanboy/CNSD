package cddb.ptest.simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


import cddb.ptest.config.Config.getBaseUrl
import cddb.ptest.scenarios.Scenarios._

class ConcurrentScenariosSimulation extends Simulation {
  
  val duration = System.getProperty("duration", "60").toInt seconds
  val creationRate = System.getProperty("creationRate", "0.5").toDouble
  val fetchRate = System.getProperty("fetchRate", "20").toDouble

  val environment = System.getProperty("environment", "local")
  def httpProtocol = http.baseUrl(getBaseUrl(environment)).userAgentHeader("Gatling/test")

  setUp(
    createEditAndDeleteAlbumScenario(duration)
      .inject(constantUsersPerSec(creationRate) during (duration))
      .protocols(httpProtocol),
    getAlbumsScenario
      .inject(constantUsersPerSec(fetchRate) during (duration))
      .protocols(httpProtocol))
}
