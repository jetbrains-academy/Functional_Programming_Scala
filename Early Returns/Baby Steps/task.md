## Baby Steps

First, let's consider a concrete example of a program in need of early returns.
Let's assume we have a database of user entries.
The access to the database is resource-heavy, and the user data is large.
Because of this, we only operate on user identifiers and retrieve the user data from the database only if needed.

Now, imagine that many of those user entries are invalid in one way or the other.
For the brevity of the example code, we'll confine our attention to incorrect emails: those that either
contain a space character or have the number of `@` symbols which is different from `1`.
In the latter tasks, we'll also discuss the case when the user with the given ID does not exist in the database.

We'll start with a sequence of user identifiers.
Given an identifier, we first retrieve the user data from the database.
This operation corresponds to the *conversion* in the previous lesson: we convert an integer number into an
instance of class `UserData`.
Following this step, we run *validation* to check if the email is correct.
Once we found the first valid instance of `UserData`, we should return it immediately without processing
of the rest of the sequence.

```scala 3
object EarlyReturns:
  type UserId = Int
  type Email = String

  case class UserData(id: UserId, name: String, email: Email)

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
   * This is our "complex conversion" method. 
   * We assume that it is costly to retrieve user data, so we want to avoid
   * calling it unless it's absolutely necessary.
   *
   * This version of the method assumes that the user data always exists for a given user id. 
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
```

The typical imperative approach is to use an early return from a `for` loop.
We perform the conversion followed by validation and, if the data is valid, we return the data, wrapped in `Some`.
If no valid user data has been found, then we return None after going through the whole sequence of identifiers.

```scala 3
 /**
  * Imperative approach that uses un-idiomatic `return`. 
  * @param userIds the sequence of all user identifiers
  * @return `Some` of the first valid user data or `None` if no valid user data is found
  */
  def findFirstValidUser1(userIds: Seq[UserId]): Option[UserData] =
    for userId <- userIds do
      val userData = complexConversion(userId)
      if (complexValidation(userData)) return Some(userData)
    None
```

This solution is underwhelming because it uses `return` which is not idiomatic in Scala.

A more functional approach is to use higher-order functions over collections.
We can `find` a `userId` in the collection, for which `userData` is valid.
But this necessitates calling `complexConversion` twice, because `find` returns the original identifier instead
of the `userData`.

```scala 3
 /**
  * Naive functional approach: calls `complexConversion`` twice on the selected ID.  
  * @param userIds the sequence of all user identifiers
  * @return `Some` of the first valid user data or `None` if no valid user data is found
  */
  def findFirstValidUser2(userIds: Seq[UserId]): Option[UserData] =
    userIds
      .find(userId => complexValidation(complexConversion(userId)))
      .map(complexConversion)
```

Or course, we can run `collectFirst` instead of `find` and `map`.
This implementation is more concise than the previous, but we still cannot avoid running the conversion twice.
In the next lesson, we'll use a custom `unapply` method to get rid of the repeated computations.

```scala 3
  /** 
   * A more concise implementation which uses `collectFirst`. 
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser3(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case userId if complexValidation(complexConversion(userId)) => complexConversion(userId)
    }
    
```
