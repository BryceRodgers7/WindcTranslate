package controllers.api
 
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import javax.inject._
import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity
import play.api.libs.json._
import play.api.data.FormError
import play.api.Logger
import akka.actor.ActorSystem
import akka.util.ByteString
import akka.stream.scaladsl._
import models.{WindcTranslate, WindcTranslateForm}
import services.{WindcTranslateService, WindcOperationService}
 
class WindcTranslateController @Inject()(
    cc: ControllerComponents,
    windcTranslateService: WindcTranslateService,
    windcOperationService: WindcOperationService,
    ws: WSClient
) extends AbstractController(cc) {
 
    val logger: Logger = Logger(this.getClass())
    implicit val windcTranslateFormat = Json.format[WindcTranslate]
 
    def getCache() = Action.async { implicit request: Request[AnyContent] =>
      windcTranslateService.listTranslations map { items =>
        Ok(Json.toJson(items))
      }
    }
    
    def translate() = Action.async { implicit request: Request[AnyContent] =>
      WindcTranslateForm.form.bindFromRequest.fold(
        errorForm => {
          errorForm.errors.foreach(println)
          Future.successful(BadRequest("Error!"))
        },
        data => {
          var tuples : List[(String,String)] = List()
          val cache = Await.result(windcTranslateService.listTranslations, 5 seconds)
          logger.info("length is " + cache.length)
          val listOfNames = data.words            
          listOfNames.foreach{ n =>
            val newWindcTranslateItem = cache.find(_.word == n).getOrElse(WindcTranslate(0, n, windcOperationService.gTranslate(n, data.actType)))
            if (newWindcTranslateItem.translatedWord == "gTranslate error while processing this word") { 
              logger.info(n + " gTranslates had an error while processing this word .. " + newWindcTranslateItem.translatedWord)
            } else if (newWindcTranslateItem.id == 0) { 
              windcTranslateService.addTranslation(newWindcTranslateItem)
              logger.info(n + " gTranslates to .. " + newWindcTranslateItem.translatedWord)
            } else {
              logger.info(n + " WAS found in cache.. it reads " + newWindcTranslateItem.translatedWord)
            }
            val tuple = (n, newWindcTranslateItem.translatedWord)
            tuples = tuples:+tuple
          }
          var res = tuples.map(s => Map(s._1 -> s._2))
            Future(Ok(Json.toJson(res)))
        })
    }   
}