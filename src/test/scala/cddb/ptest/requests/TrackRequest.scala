package cddb.ptest.requests

import scala.concurrent.duration._

import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object TrackRequest {

    private val contentTypeHeader = Map("Content-Type" -> "application/json;charset=UTF-8")

    /**
      * Gatling action to make a GET request to the "/${albumid}/tracks/" endpoint of the baseUrl
      * 
      * expect an `albumid` attribute to be present in the user session
      */     
    val getAll = http("get tracks")
      .get("/${albumid}/tracks/")

    /**
      * Gatling action to make a GET request to the "/${albumid}/tracks/${trackid}" endpoint of the baseUrl
      * 
      * expect `albumid` and `trackid` attributes to be present in the user session
      */  
    val get = http("get track")
      .get("/${albumid}/tracks/${trackid}")

    /**
      * Gatling action to make a POST request to the "/${albumid}/tracks/" endpoint of the baseUrl
      * 
      * expect `albumid`, `trackName` and `tracknr` attribute to be present in the session
      */
    val create = http("create album track")
      .post("/${albumid}/tracks/")
      .headers(contentTypeHeader)
      .body(ElFileBody("bodies/track.json")).asJson
}