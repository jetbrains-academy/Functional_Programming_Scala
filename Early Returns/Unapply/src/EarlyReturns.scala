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
   * This version of the method assumes that the user data always exists for a given user id.
   *
   * @param userId the identifier of a user for whom we want to retrieve the data
   * @return the user data
   */
  def complexConversion(userId: UserId): UserData =
    database.find(_.id == userId).get

  /**
   * Similar to `complexConversion`, the validation of user data is costly
   * and we shouldn't do it too often.
   *
   * @param user user data
   * @return true if the user data is valid, false otherwise
   */
  def complexValidation(user: UserData): Boolean =
    !user.email.contains(' ') && user.email.count(_ == '@') == 1

  object ValidUser:
    def unapply(userId: UserId): Option[UserData] =
      val userData = complexConversion(userId)
      if complexValidation(userData) then Some(userData) else None

  /**
   * The custom `unapply` method runs conversion and validation and only returns valid user data.
   *
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser4(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser(user) => user
    }

  /**
   * This function takes into account that some IDs can be left out from the database
   */
  def safeComplexConversion(userId: UserId): Option[UserData] = database.find(_.id == userId)

  /**
   * Partiality of `safeComplexConversion` trickles into the search function.
   *
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser5(userIds: Seq[UserId]): Option[UserData] =
    for userId <- userIds do
      safeComplexConversion(userId) match
        case Some(user) if complexValidation(user) => return Some(user)
        case _ =>
    None

  /**
   * This custom `unapply` method performs the safe conversion and then validation.
   */
  object ValidUser6:
    def unapply(userId: UserId): Option[UserData] =
      safeComplexConversion(userId).find(complexValidation)

  /**
   * This custom `unapply` method performs the safe conversion and then validation.
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser6(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser6(user) => user
    }

  /**
   * There might be multiple ways to validate the same user.
   * Adding a new object with a different validation function unapply allows supporting both validations.
   */
  object ValidUserInADifferentWay:
    def otherValidation(userData: UserData): Boolean = false /* check that it's a child user */
    def unapply(userId: UserId): Option[UserData] = safeComplexConversion(userId).find(otherValidation)

  /**
   * The two possible ways to validate a user are used one after another in this method.
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser7(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser6(user) => user
      case ValidUserInADifferentWay(user) => user
    }

  /**
   * This trait neatly abstracts the described process.
   * Now the programmer only need to supply the `conversion` and `validation` methods, while `unapply` is standard.
   * @tparam From The type we initially operate on
   * @tparam To   The type of the data we want to retrieve if it's valid
   */
  trait Deconstruct[From, To]:
    def convert(from: From): Option[To]

    def validate(to: To): Boolean

    def unapply(from: From): Option[To] = convert(from).find(validate)

  /**
   * By extending `Deconstruct` we don't need to bother with `unapply`.
   */
  object ValidUser8 extends Deconstruct[UserId, UserData]:
    override def convert(userId: UserId): Option[UserData] = safeComplexConversion(userId)

    override def validate(user: UserData): Boolean = complexValidation(user)

  /**
   * The `unapply` method works as it did before.
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser8(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser8(user) => user
    }