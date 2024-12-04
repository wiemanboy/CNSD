package cddb.ptest.requests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

// object are native Scala singletons
object AlbumRequest {

    private val contentTypeHeader = Map("Content-Type" -> "application/json;charset=UTF-8")

    /**
      * Gatling action to make a GET request to the "/" endpoint of the baseUrl
      */
    val getAll = http("get albums").get("/")

    /**
      * Gatling action to make a POST request to the "/" endpoint of the baseUrl
      * 
      * expect an `albumName` to be present in the user session
      */
    val create = http("create album")
        .post("/")
        .headers(contentTypeHeader)
        .body(StringBody("""{ "artist": "PT_TestArtist","name": "${albumName}","year":  2020}""")).asJson

    /**
      * Gatling action to make a PUT request to the "/" endpoint of the baseUrl
      * 
      * The body template is contained in the /src/test/resources/bodies/album.json file
      * 
      * expect an `albumName` and `albumId` to be present in the user session
      * to substitute in the template
      */    
    val edit = http("edit album")
        .put("/${albumid}")
        .headers(Map("Content-Type" -> "application/json;charset=UTF-8"))
        .body(ElFileBody("bodies/album.json")).asJson

    val delete = http("delete album")
        .delete("/${albumid}")
}