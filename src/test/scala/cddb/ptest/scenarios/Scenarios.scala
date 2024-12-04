package cddb.ptest.scenarios

import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import cddb.ptest.requests.AlbumRequest
import cddb.ptest.requests.TrackRequest

object Scenarios {

    /** Feeder: generate a random album name starting with 'PT_TestAlbum' at every iteration.
      * 
      * See https://gatling.io/docs/current/session/feeder
      */
    private val albumNameFeeder = Iterator.continually({
        val randomString = Random.alphanumeric.take(10).mkString
            Map("albumName" -> (s"PT_TestAlbum$randomString"))
        })

    /**
      * Scenario: Call the GET / endpoint once
      */
    val getAlbumsScenario = scenario("Get all albums")
            .exec(AlbumRequest.getAll.check(status.is(200)))

    /** 
      * Scenario: Create an album with a random title, change the title and delete the album 
      *
      * @param pauseBeforeDeletion how much time to pause before deleting the created album (default 2 seconds)
      */
    def createEditAndDeleteAlbumScenario(pauseBeforeDeletion : Duration = 2 seconds) = 
        scenario("Create, edit and delete an album")
        // use the albumNameFeeder to inject a random albumName in the session
        .feed(albumNameFeeder)
        .exec(AlbumRequest.create
            .check(status.is(200))
            //save the response in the session under the key "albumid"
            .check(bodyString.transform(x => x.trim().toInt).saveAs("albumid"))
        )
        // do not continue if the album was not successfully created
        .exitHereIfFailed
        .pause(1 second)
        // fetch another album name
        .feed(albumNameFeeder)
        // change the name of the album previously created
        .exec(AlbumRequest.edit
            .check(status.is(200))
        )
        // pause for a certain duration
        .pause(pauseBeforeDeletion)
        // delete the created album
        .exec(AlbumRequest.delete
            .check(status.is(200))
        )

    /**
      * Scenario: user get all the tracks of the album with the given id 
      *
      * @TODO: Complete implementation
      * 
      * @param albumId the id of the album
      */
    def getTracksOfFixedAlbumScenario(albumId : Int) = scenario("Get all tracks")

    /**
      * Scenario: user get all the tracks of an album selected randomly from
      * the src/test/resources/data/albumids.csv file
      * 
      * @TODO: Complete implementation
      */  
    val getTracksOfCsvAlbumsScenario = scenario("Get all tracks of give albums")

    /**
      * Scenario: user get all the album and get all the tracks for one of the albums, chosen randomly
      * 
      * @TODO: Complete the implementation
      */
    val getAlbumsAndTracksScenario = {
        scenario("Get albums and tracks for one album")
    }
}