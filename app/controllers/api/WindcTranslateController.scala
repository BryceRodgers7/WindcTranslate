package controllers.api
 
import scala.concurrent.Future
import scala.concurrent.duration._
import javax.inject._
import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity
import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.libs.json._
import models.{WindcTranslate, WindcTranslateForm}
import play.api.data.FormError
 
import services.WindcTranslateService
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
 
class WindcTranslateController @Inject()(
    cc: ControllerComponents,
    windcTranslateService: WindcTranslateService,
    ws: WSClient
) extends AbstractController(cc) {
 
    implicit val windcTranslateFormat = Json.format[WindcTranslate]
 
    def getAll() = Action.async { implicit request: Request[AnyContent] =>
        windcTranslateService.listAllItems map { items =>
          Ok(Json.toJson(items))
        }
      }

      def getById(id: Long) = Action.async { implicit request: Request[AnyContent] =>
        windcTranslateService.getItem(id) map { item =>
          Ok(Json.toJson(item))
        }
      }
     
      def add() = Action.async { implicit request: Request[AnyContent] =>
        WindcTranslateForm.form.bindFromRequest.fold(
          // if any error in submitted data
          errorForm => {
            errorForm.errors.foreach(println)
            Future.successful(BadRequest("Error!"))
          },
          data => {
            val newWindcTranslateItem = WindcTranslate(0, data.name, data.isComplete)
            windcTranslateService.addItem(newWindcTranslateItem).map( _ => Redirect(routes.WindcTranslateController.getAll))
          })
      }
     
      def update(id: Long) = Action.async { implicit request: Request[AnyContent] =>
        WindcTranslateForm.form.bindFromRequest.fold(
          // if any error in submitted data
          errorForm => {
            errorForm.errors.foreach(println)
            Future.successful(BadRequest("Error!"))
          },
          data => {
            val windcTranslateItem = WindcTranslate(id, data.name, data.isComplete)
            windcTranslateService.updateItem(windcTranslateItem).map( _ => Redirect(routes.WindcTranslateController.getAll))
          })
      }
     
      def delete(id: Long) = Action.async { implicit request: Request[AnyContent] =>
        windcTranslateService.deleteItem(id) map { res =>
          Redirect(routes.WindcTranslateController.getAll)
        }
      }
}



//        val request: WSRequest = ws.url("https://translate.google.com/?sl=en&tl=fr&text=translate%20these%20words%20please&op=translate")
//        val complexRequest: WSRequest =
//            request
//                .addHttpHeaders("Accept" -> "application/json")
//                .addQueryStringParameters("search" -> "play")
//                .withRequestTimeout(10000.millis)
//        val futureResponse: Future[WSResponse] = complexRequest.get()
