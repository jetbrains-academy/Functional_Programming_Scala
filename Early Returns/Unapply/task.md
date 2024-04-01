## Unapply

Unapply methods form a basis of pattern matching.
Its goal is to extract data compacted in objects.
We can create a custom extractor object for user data validation with the suitable unapply method, for example:

```scala 3
  object ValidUser:
    def unapply(userId: UserId): Option[UserData] =
      val userData = complexConversion(userId)
      if complexValidation(userData) then Some(userData) else None
```

When we pattern match on `ValidUser`, its `unapply` method is called.
It runs the conversion and validation and only returns valid user data.
As a result, we get this short definition of our search function.

```scala 3
  /**
   * The custom `unapply` method runs conversion and validation and only returns valid user data.
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser4(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser(user) => user
    }
```

It's at this point that an observant reader is likely to protest.
This solution is twice as long as the imperative one we started with, and it doesn't seem to do anything extra!  
One thing to notice here is that the imperative implementation is only concerned with the "happy" path.
What if there are no records in the database for some of the user identifiers?
The conversion function becomes partial, and, being true to the functional method, we need to return optional value:

```scala 3
  /** 
   * This function takes into account that some IDs can be left out from the database
   */
  def safeComplexConversion(userId: UserId): Option[UserData] = database.find(_.id == userId)
```

The partiality of the conversion will unavoidably complicate the imperative search function.
The code still has the same shape, but it has to go through additional hoops to accommodate partiality.
Note that every time a new complication arises in the business logic, it has to be reflected inside
the `for` loop.

```scala 3
  /**
   * Partiality of `safeComplexConversion` trickles into the search function. 
   * @param userIds the sequence of all user identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def findFirstValidUser5(userIds: Seq[UserId]): Option[UserData] =
    for userId <- userIds do
      safeComplexConversion(userId) match
        case Some(user) if complexValidation(user) => return Some(user)
        case _ =>
    None
```

Unlike the imperative approach, the functional implementation separates the logic of conversion and validation
from the sequence traversal, which results in more readable code.
Taking care of possible missing records in the database amounts to modifying the unapply method, while the
search function stays the same.

```scala 3
  /**
   * This custom `unapply` method performs the safe conversion and then validation.
   */
  object ValidUser6:
    def unapply(userId: UserId): Option[UserData] =
      safeComplexConversion(userId).find(complexValidation)
  
  def findFirstValidUser6(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser6(user) => user
    }
```

In general, there might be several ways in which user data might be valid.
Imagine that there is a user who doesn't have an email.
In this case `complexValidation` returns `false`, but the user may still be valid.
For example, it may be an account that belongs to a child of another user.
We don't need to message the child, instead it's enough to reach out to their parent.
Even though this case is less common than the one we started with, we still need to keep it mind.
To do it, we can create a different extractor object with its own `unapply` and pattern match against it
if the first validation failed.
We do run the conversion twice in this case, but it is less important because of how rare this case is.

```scala 3
  object ValidUserInADifferentWay:
    def otherValidation(userData: UserData): Boolean = /* check that it's a child user */
    def unapply(userId: UserId): Option[UserData] = safeComplexConversion(userId).find(otherValidation)
  
  def findFirstValidUser7(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser6(user) => user
      case ValidUserInADifferentWay(user) => user
    }
```

Both extractor objects work in the same way.
They run a conversion method, which may or may not succeed.
If conversion succeeds, its result is validated and returned when valid.
All this is done with the `unapply` method whose implementation stays the same regardless of the other methods.
This forms a nice framework which can be abstracted as a trait we call `Deconstruct`.
It has the `unapply` method which calls two abstract methods `convert` and `validate` that operate on generic
types `From` and `To`.

```scala 3
  /**
   * @tparam From The type we initially operate on
   * @tparam To The type of the data we want to retrieve if it's valid
   */
  trait Deconstruct[From, To]:
    def convert(from: From): Option[To]
    def validate(to: To): Boolean
    def unapply(from: From): Option[To] = convert(from).find(validate)
```

In our case, the concrete implementation of the `Deconstruct` trait works on types `From` = `UserId` and
`To` = `UserData`.
It uses `safeComplexConversion` and `complexValidation` respectively.

```scala 3
  object ValidUser8 extends Deconstruct[UserId, UserData]:
    override def convert(userId: UserId): Option[UserData] = safeComplexConversion(userId)
    override def validate(user: UserData): Boolean = complexValidation(user)
```

Finally, the search function stays the same, but now it uses the `unapply` method defined in
the `Deconstruct` trait while pattern matching:

```scala 3
  def findFirstValidUser8(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser8(user) => user
    }
```










