package services
 
import play.api.libs.json._
import scala.concurrent.Future
import scalaj.http._
// import sttp.client3._
// import sttp.client3.{Request, Response, SimpleHttpClient, UriContext, Uri, asStringAlways, basicRequest}
import play.api.Logger
 
case class WindcOperationServiceResponse(status: Int, body: Option[String] = None)

class WindcOperationService { 
  val logger: Logger = Logger(this.getClass())
  // val client = SimpleHttpClient()
  def gTranslate(word: String, actType: Option[String]): String = {
    try {
      // val act = if (actType.exists(_.trim.nonEmpty)) ("?type="+actType) else ""
      // val response: Response[Either[String, String]] = client.send(basicRequest.get(uri"http://www.boredapi.com/api/activity"))
      val response: HttpResponse[String] = Http("http://www.boredapi.com/api/activity").asString
      // val response: HttpResponse[String] = Http("http://www.boredapi.com/api/activity").param("type",actType).asString
      // if (actType.exists(_.trim.nonEmpty)) {
      //   println("optinal parm found http://www.boredapi.com/api/activity?type=")
      // } else {
      //   println("optinal parm OMITTED")
      // }

      // // val act = if (actType.exists(_.trim.nonEmpty)) ("?type="+actType) else ""
      // val response: Response[Either[String, String]] = 
      //   if (actType.exists(_.trim.nonEmpty)) 
      //     client.send(basicRequest.get(uri"http://www.boredapi.com/api/activity?type=$actType")) 
      //     // client.send(basicRequest.get(uri"http://www.boredapi.com/api/activity/"))
      //   else 
      //     client.send(basicRequest.get(uri"http://www.boredapi.com/api/activity/"))

      response.code match {
        case 200 => {
          val json: JsValue = Json.parse(response.body)
          val act = json("activity")
          println("return 2xx!!! "  + response.code + " activity reads " + act)
          return act.toString
        }
        case default  => { 
          println("Operations unreachable " + response.code + " body reads " + response.body)
          return ""
        }
      }
    }
  }

  // def getUri(actType: Option[String]): Uri = {
  //   if (actType.exists(_.trim.nonEmpty)) {
  //     return (uri"actType http://www.boredapi.com/api/activity?type=$actType")
  //   } else {
  //     return (uri"actType http://www.boredapi.com/api/activity/")
  //   }
  // }
}