package services
 
import play.api.libs.json._
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
          println("Operations unreachable " + response.code + " body reads " + body)
          return ""
        }
        case Right(body) => {
          val json: JsValue = Json.parse(body)
          val act = json("activity")
          println("return 2xx!!! "  + response.code + " activity reads " + act)
          return act.toString
        }
      }
    } finally client.close()
  }
}