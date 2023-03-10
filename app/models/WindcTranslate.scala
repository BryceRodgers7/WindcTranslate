package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._
 
case class WindcTranslate(id: Long, word: String, translatedWord: String)
 
case class WindcTranslateFormData(words: List[String], actType: Option[String])
 
object WindcTranslateForm {
  val form = Form(
    mapping(
      "words" -> list(nonEmptyText),
      "actType" -> optional(text)
    )(WindcTranslateFormData.apply)(WindcTranslateFormData.unapply)
  )
}
 
class WindcTranslateTableDef(tag: Tag) extends Table[WindcTranslate](tag, "windctranslate") {
 
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def word = column[String]("word")
  def translatedWord = column[String]("translatedWord")
 
  override def * = (id, word, translatedWord) <> (WindcTranslate.tupled, WindcTranslate.unapply)
}
 
class WindcTranslateList @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  var windcTranslateList = TableQuery[WindcTranslateTableDef]
 
  def add(windcTranslateItem: WindcTranslate): Future[String] = {
    dbConfig.db
      .run(windcTranslateList += windcTranslateItem)
      .map(res => "WindcTranslateItem successfully added")
      .recover {
        case ex: Exception => {
            printf(ex.getMessage())
            ex.getMessage
        }
      }
  }
 
  def listAll: Future[Seq[WindcTranslate]] = {
    dbConfig.db.run(windcTranslateList.result)
  }
}