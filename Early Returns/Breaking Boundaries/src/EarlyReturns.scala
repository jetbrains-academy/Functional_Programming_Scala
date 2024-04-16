import scala.util.boundary
import scala.util.boundary.break

object EarlyReturns:
  private type UserId = Int
  private type Email = String

  case class UserData(id: UserId, name: String, email: Email)

  /**
   * Pretend database of user data.
   */
  private val database = Seq(
    UserData(1, "Ayaan Sharma", "ayaan@gmail.com"),
    UserData(2, "Lei Zhang", "lei_zhang@yahoo.cn"),
    UserData(3, "Fatima Al-Fassi", "fatima.alfassi@outlook.sa"),
    UserData(4, "Ana Sofia Ruiz", "ana_sofia@icloud.es"),
    UserData(5, "Oluwaseun Adeyemi", "oluwaseun@hotmail.ng"),
    UserData(6, "Maria Ivanova", "maria.ivanova@aol.ru"),
    UserData(7, "Yuto Nakamura", "yuto.nakamura@mail.jp"),
    UserData(8, "Chiara Rossi", "chiara@live.it"),
    UserData(9, "Lucas Müller", "lucas@protonmail.de"),
    UserData(10, "Sara Al-Bahrani", "sara.albahrani@gmail.com"),
    UserData(11, "Min-Jun Kim", "minjun.kim@yahoo.kr")
  )

  private val identifiers = 1 to 11

  /**
   * This is our "complex conversion" method.
   * We assume that it is costly to retrieve user data, so we want to avoid
   * calling it unless it's absolutely necessary.
   *
   * This function takes into account that some IDs can be left out from the database
   */
  def safeComplexConversion(userId: UserId): Option[UserData] = database.find(_.id == userId)


  /**
   * Similar to `safeComplexConversion`, the validation of user data is costly
   * and we shouldn't do it too often.
   *
   * @param user user data
   * @return true if the user data is valid, false otherwise
   */
  def complexValidation(user: UserData): Boolean =
    !user.email.contains(' ') && user.email.count(_ == '@') == 1

  /**
   * Using `boundary` we create a computation context to which `break` returns the value.
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser10(userIds: Seq[UserId]): Option[UserData] =
    boundary:
      for userId <- userIds do
        safeComplexConversion(userId).foreach { userData =>
          if (complexValidation(userData)) break(Some(userData))
        }
      None
