package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._
 
case class WindcTranslate(id: Long, name: String, isComplete: Boolean)
 
case class WindcTranslateFormData(name: String, isComplete: Boolean)
 
object WindcTranslateForm {
  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "isComplete" -> boolean
    )(WindcTranslateFormData.apply)(WindcTranslateFormData.unapply)
  )
}
 
class WindcTranslateTableDef(tag: Tag) extends Table[WindcTranslate](tag, "windctranslate") {
 
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def isComplete = column[Boolean]("isComplete")
 
  override def * = (id, name, isComplete) <> (WindcTranslate.tupled, WindcTranslate.unapply)
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
 
  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(windcTranslateList.filter(_.id === id).delete)
  }
 
  def update(windcTranslateItem: WindcTranslate): Future[Int] = {
    dbConfig.db
      .run(windcTranslateList.filter(_.id === windcTranslateItem.id)
            .map(x => (x.name, x.isComplete))
            .update(windcTranslateItem.name, windcTranslateItem.isComplete)
      )
  }
 
  def get(id: Long): Future[Option[WindcTranslate]] = {
    dbConfig.db.run(windcTranslateList.filter(_.id === id).result.headOption)
  }
 
  def listAll: Future[Seq[WindcTranslate]] = {
    dbConfig.db.run(windcTranslateList.result)
  }
}