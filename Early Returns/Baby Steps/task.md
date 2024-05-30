First, let's consider a concrete example of a program in need of early returns.
Let's assume we have a database of user entries.
Accessing this database is resource-intensive, and the user data is extensive.
As a result, we only operate on user identifiers and retrieve the user data from the database only when necessary.

Now, imagine that many of those user entries are invalid in some way.
For the brevity of the example code, we'll confine our attention to incorrect emails: those that either
contain a space character or have a count of `@` symbols different from `1`.
In subsequent tasks, we'll also discuss the case when the user with the given ID does not exist in the database.

We'll start with a sequence of user identifiers.
Given an identifier, we first retrieve the user data from the database.
This operation corresponds to the *conversion* in the previous lesson: we convert an integer number into an
instance of the `UserData` class.
Following this step, we run *validation* to check if the email is correct.
Upon locating the first valid instance of `UserData`, we should return it immediately, ceasing any further processing
of the remaining sequence.

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
   */
  def complexConversion(userId: UserId): UserData = 
    database.find(_.id == userId).get

  /**
   * Similar to `complexConversion`, the validation of user data is costly 
   * and we shouldn't do it too often.
   */
  def complexValidation(user: UserData): Boolean = 
    !user.email.contains(' ') && user.email.count(_ == '@') == 1
```

The typical imperative approach is to use an early return from a `for` loop.
We perform the conversion, followed by validation, and if the data is found valid, we return it, wrapped in `Some`.
If no valid user data has been found, we return `None` after traversing the entire sequence of identifiers.

```scala 3
 /**
  * Imperative approach that uses un-idiomatic `return`.
  */
  def findFirstValidUser1(userIds: Seq[UserId]): Option[UserData] =
    for userId <- userIds do
      val userData = complexConversion(userId)
      if (complexValidation(userData)) return Some(userData)
    None
```

This solution is underwhelming because it uses `return`, which is not idiomatic in Scala.

A more functional approach is to use higher-order functions over collections.
We can `find` a `userId` in the collection for which the `userData` is valid.
However, this necessitates calling `complexConversion` twice, as `find` returns the original identifier rather
than the `userData`.

```scala 3
 /**
  * Naive functional approach: calls `complexConversion` twice on the selected ID.
  */
  def findFirstValidUser2(userIds: Seq[UserId]): Option[UserData] =
    userIds
      .find(userId => complexValidation(complexConversion(userId)))
      .map(complexConversion)
```

Or course, we can run `collectFirst` instead of `find` and `map`.
This implementation is more concise than the previous one, but it still doesn't allow us to avoid running the conversion twice.
In the next lesson, we'll use a custom `unapply` method to eliminate the need for these repeated computations.

```scala 3
  /** 
   * A more concise implementation, which uses `collectFirst`.
   */
  def findFirstValidUser3(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case userId if complexValidation(complexConversion(userId)) => complexConversion(userId)
    }
    
```

## Exercise

Let's revisit one of our examples from an earlier module. 
You are managing a cat shelter and keeping track of cats, their breeds, and coat types in a database.

You notice numerous mistakes in the database made by a previous employee: there are short-haired Maine Coons, long-haired Sphynxes, and other inconsistencies. 
You don't have the time to fix the database right now because you see a potential adopter coming into the shelter. 
Your task is to find the first valid entry in the database and present the potential adopter with a cat. 

Implement the `catConversion` method, which fetches a cat from the `catDatabase` in the `Database.scala` file by its identifier. 
To do this, you will first need to consult another database "table", `adoptionStatusDatabase`, to find out the cat's name. 

Then, implement the `furCharacteristicValidation` that checks if the fur characteristics in the database entry make sense for the cat's particular breed. 
Consult the `breedCharacteristics` map for the appropriate fur characteristics for each breed. 

Finally, implement the search using the conversion and validation methods:  
* `imperativeFindFirstValidCat`, which works in an imperative fashion.
* `functionalFindFirstValidCat`, utilizing an functional style. 
* `collectFirstFindFirstValidCat`, using the `collectFirst` method. 

Ensure that your search does not traverse the entire database. 
We've put some simple logging within the conversion and validation methods so that you can verify this. 
