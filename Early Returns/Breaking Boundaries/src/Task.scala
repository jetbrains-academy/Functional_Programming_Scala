import scala.util.boundary
import scala.util.boundary.break

object EarlyReturns:
  type UserId = Int
  type Email = String

  case class UserData(id: UserId, name: String, email: Email)
  def findFirstValidUser10(userIds: Seq[UserId]): Option[UserData] =
    boundary:
      for userId <- userIds do
        safeComplexConversion(userId).foreach { userData =>
          if (complexValidation(userData)) break(Some(userData))
        }
      None

