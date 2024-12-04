package cddb.ptest.simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import cddb.ptest.config.Config.getBaseUrl

import cddb.ptest.scenarios.Scenarios._

class GetAlbumsGradualIncreaseSimulation extends Simulation {

  val levelDuration = System.getProperty("levelDuration", "10").toInt seconds
  val rampDuration = System.getProperty("rampDuration", "2").toInt seconds
  val userIncrease = System.getProperty("userIncrease", "3").toDouble
  val levels = System.getProperty("levels", "6").toInt
  val startingRate = System.getProperty("startingRate", "0").toDouble

  val environment = System.getProperty("environment", "local")
  def httpProtocol = http.baseUrl(getBaseUrl(environment)).userAgentHeader("Gatling/test")

  setUp(getAlbumsScenario.inject(
    incrementUsersPerSec(userIncrease)
      .times(levels)
      .eachLevelLasting(levelDuration)
      .separatedByRampsLasting(rampDuration)
      .startingFrom(0)
    ).protocols(httpProtocol))
}
