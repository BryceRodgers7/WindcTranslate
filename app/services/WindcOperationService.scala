
package services
 
import play.api.libs.json._
// import scala.util.parsing.json._
import scala.concurrent.Future
import sttp.client3.{Request, Response, SimpleHttpClient, UriContext, asStringAlways, basicRequest}
import play.api.Logger
 
case class WindcOperationServiceResponse(status: Int, body: Option[String] = None)

class WindcOperationService { 

  val logger: Logger = Logger(this.getClass())
  val client = SimpleHttpClient()

  def gTranslate(word: String): String = {
    try {
      val response: Response[Either[String, String]] = client.send(basicRequest.get(uri"http://www.boredapi.com/api/activity/"))

      response.body match {
        case Left(body)  => { 
          println("hmm not good " + response.code + " body reads " + body)
          return ""
        }
        case Right(body) => {
          val json: JsValue = Json.parse(body)
          val act = json("activity")
          // val json = JSON.parseFull(body)
          // println("return 2xx!!! "  + response.code + " activity reads " + body)
          println("return 2xx!!! "  + response.code + " activity reads " + act)
          return act.toString
        }
        // case Left(body)  => println("hmmmm " + response.code + " body starts with " + body.substring(0,11))
        // case Right(body) => println(s"2xx!!! "  + response.code + " body starts with " + body.substring(0,11))
      }
    } finally client.close()
  }

  // def jsonStrToMap(jsonStr: String): Map[String, Any] = {
  //   implicit val formats = org.json4s.DefaultFormats

  //   parse(jsonStr).extract[Map[String, Any]]
  // }



  // def gTranslate(word: String) = {
  //   // val response = Http("https://translate.google.com/?sl=en&tl=fr&text=translate%20these%20words%20please&op=translate").param("q", "monkeys").asString
    
  //   val request: WSRequest = ws.url("https://translate.google.com/?sl=en&tl=fr&text=translate%20these%20words%20please&op=translate")
  //   val complexRequest: WSRequest =
  //         request
  //             .withRequestTimeout(5000.millis)
  //   val futureResponse: Future[WSResponse] = complexRequest.get()
  //   logger.info("response was " + futureResponse.asString)
  //   // return result
  // }
}







    // try {
    //   logger.info("gTranslating word " + word)
    //   val response: Response[Either[String, String]] = client.send(basicRequest.get(uri"https://translate.google.com/?sl=en&tl=fr&text=translate%20these%20words%20please&op=translate"))
    //   response.body match {
    //     case Left(body)  => { println(s"Non-2xx response to GET with code ${response.code}:\n$body") 
    //       return response
    //     }
    //     case Right(body) => { println(s"2xx response to GET:\n$body")
    //       return response
    //     }
    //   }
    // } finally client.close()