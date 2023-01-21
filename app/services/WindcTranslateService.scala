
package services
 
import com.google.inject.Inject
import models.{WindcTranslate, WindcTranslateList}
 
import scala.concurrent.Future
 
class WindcTranslateService @Inject() (items: WindcTranslateList) {
 
  // def addItem(item: WindcTranslate): Future[String] = {
  //   items.add(item)
  // }

  def appendItem(item: WindcTranslate): Unit = {
    items.add(item)
  }
 
  // def deleteItem(id: Long): Future[Int] = {
  //   items.delete(id)
  // }
 
  // def updateItem(item: WindcTranslate): Future[Int] = {
  //   items.update(item)
  // }
 
  // def getItem(id: Long): Future[Option[WindcTranslate]] = {
  //   items.get(id)
  // }
 
  def listAllItems: Future[Seq[WindcTranslate]] = {
    items.listAll
  }
}