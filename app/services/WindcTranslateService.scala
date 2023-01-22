package services

import scala.concurrent.Future 
import com.google.inject.Inject
import models.{WindcTranslate, WindcTranslateList}

class WindcTranslateService @Inject() (items: WindcTranslateList) {

  def addTranslation(item: WindcTranslate): Unit = {
    items.add(item)
  }
 
  def listTranslations: Future[Seq[WindcTranslate]] = {
    items.listAll
  }
}