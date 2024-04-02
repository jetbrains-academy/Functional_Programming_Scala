object EarlyReturns:
  private type UserId = Int
  private type Email = String

  case class UserData(id: UserId, name: String, email: Email)

  /**
   * Pretend database of user data.
   */
  private val database = Seq(
    UserData(1, "John Doe", "john@@gmail.com"),
    UserData(2, "Jane Smith", "jane smith@yahoo.com"),
    UserData(3, "Michael Brown", "michaeloutlook.com"),
    UserData(4, "Emily Johnson", "emily at icloud.com"),
    UserData(5, "Daniel Wilson", "daniel@hotmail.com"),
    UserData(6, "Sophia Martinez", "sophia@aol.com"),
    UserData(7, "Christopher Taylor", "christopher@mail.com"),
    UserData(8, "Olivia Anderson", "olivia@live.com"),
    UserData(9, "James Thomas", "james@protonmail.com"),
    UserData(10, "Isabella Jackson", "isabella@gmail.com"),
    UserData(11, "Alexander White", "alexander@yahoo.com")
  )

  private val identifiers = 1 to 11

  /**
   * This is our pretend "complex conversion" method.
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

  /**
   * Imperative approach that uses un-idiomatic `return`. 
   *
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser1(userIds: Seq[UserId]): Option[UserData] =
    for userId <- userIds do
      val userData = complexConversion(userId)
      if (complexValidation(userData)) return Some(userData)
    None

  /**
   * Naive functional approach: calls `complexConversion`` twice on the selected ID.  
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser2(userIds: Seq[UserId]): Option[UserData] =
    userIds
      .find(userId => complexValidation(complexConversion(userId)))
      .map(complexConversion)

  /**
   * A more concise implementation which uses `collectFirst`. 
   *
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser3(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case userId if complexValidation(complexConversion(userId)) => complexConversion(userId)
    }