package services
 
import play.api.libs.json._
import scala.concurrent.Future
import scalaj.http._
import play.api.Logger
 
case class WindcOperationServiceResponse(status: Int, body: Option[String] = None)

class WindcOperationService { 
  val logger: Logger = Logger(this.getClass())

  // represents an expensive operation
  def gTranslate(word: String, actType: Option[String]): String = {
    try {
      // constructing a simple request
      var url = "http://www.boredapi.com/api/activity?type=" + actType.getOrElse("")
      val response: HttpResponse[String] = Http(url).asString
      response.code match {
        case 200 => {
          println(response.code + ": body reads " + response.body)
          val json = Json.parse(response.body)
          val act = (json \ "activity").asOpt[String].getOrElse("")
          if (act!="") {
            return act.toString
          } else {
            val errorMsg = (json \ "error").asOpt[String].getOrElse("")
            println("unexpected response... error reads: " + errorMsg)
            return "gTranslate error while processing this word"
          }
        }
        case default  => { 
          println("gTranslate operations unreachable " + response.code + " body reads " + response.body)
          return "gTranslate error while processing this word"
        }
      }
    }
  }
}