package cddb.ptest.simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._



import cddb.ptest.config.Config.getBaseUrl
import cddb.ptest.scenarios.Scenarios._

class CreateEditAndDeleteAlbumSimulation extends Simulation {

  val duration = System.getProperty("duration", "10").toInt seconds
  val userRate = System.getProperty("userRate", "5").toInt
  val rampDuration = System.getProperty("rampDuration", "2").toInt seconds
  val deleteAlbumDelay = System.getProperty("deleteAlbumDelay", "2").toInt seconds

  val environment = System.getProperty("environment", "local")
  def httpProtocol = http.baseUrl(getBaseUrl(environment)).userAgentHeader("Gatling/test")

  setUp(
    createEditAndDeleteAlbumScenario(deleteAlbumDelay)
      .inject(
      rampUsersPerSec(0) to userRate during rampDuration,  
      constantUsersPerSec(userRate) during (duration) randomized)
      .protocols(httpProtocol))
}
