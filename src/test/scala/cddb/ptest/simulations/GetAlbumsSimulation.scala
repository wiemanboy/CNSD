package cddb.ptest.simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import cddb.ptest.config.Config.getBaseUrl

import cddb.ptest.scenarios.Scenarios._

class GetAlbumsSimulation extends Simulation {

  /* 
    The System.getProperty(propertyName: String, defaultValue: String) method 
     look for the value of the system property propertyName and return its value (as String).
     If a system property system property with the given name is not present it returns defaultValue.

     See also https://gatling.io/docs/current/cookbook/passing_parameters/

     With maven, system properties can be passed via the command line with the -D option 
  */
  // default 10 seconds   
  val duration = System.getProperty("duration", "10").toInt seconds
  // default 1
  val userRate = System.getProperty("userRate", "1").toDouble

  // default "staging"
  val environment = System.getProperty("environment", "local")

  /* 
     the base url is obtained from the application.conf file /src/test/resources/application.conf with the 
     cddb.ptest.config.Config.getBaseUrl method

     since, by default, environment = staging, the baseUrl will be https://staging<<nn>>-frontend.devops-labs.it/cddb/rest
     if the environment system property is not overridden.
   */  
  def httpProtocol = http.baseUrl(getBaseUrl(environment)).userAgentHeader("Gatling/test")

  before {
    println(s"Running GetAlbumsSimulation against ${getBaseUrl(environment)}")
  }

  setUp(getAlbumsScenario.inject(constantUsersPerSec(userRate) during duration).protocols(httpProtocol))
}