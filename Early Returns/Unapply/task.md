## Unapply

Unapply methods form the basis of pattern matching.
Their goal is to extract data encapsulated in objects.
We can create a custom extractor object for user data validation with a suitable unapply method, for example:

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
   */
  def findFirstValidUser4(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser(user) => user
    }
```

At this point, an observant reader is likely to protest.
This solution is twice as long as the imperative one we started with, and it doesn't seem to do anything extra!  
One thing to notice here is that the imperative implementation is only concerned with the "happy" path.
But what if there are no records in the database for some of the user identifiers?
The conversion function becomes partial, and, adhering to the functional method, we need to return an optional value:

```scala 3
  /** 
   * This function takes into account that some IDs can be left out from the database
   */
  def safeComplexConversion(userId: UserId): Option[UserData] = database.find(_.id == userId)
```

The partiality of the conversion will unavoidably complicate the imperative search function.
The code still has the same shape, but it has to go through additional loops to accommodate partiality.
Note that every time a new complication arises in the business logic, it has to be reflected within
the `for` loop.

```scala 3
  /**
   * Partiality of `safeComplexConversion` trickles into the search function. 
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

In general, there might be several ways in which user data could be valid.
Imagine that there is a user who doesn't have an email.
In this case, `complexValidation` returns `false`, but the user might still be valid.
For example, it may be an account that belongs to a child of another user.
We don't need to message the child; instead, it's enough to reach out to their parent.
Even though this case is less common than the one we started with, we still need to keep it mind.
To account for it, we can create a different extractor object with its own `unapply` and pattern match against it
if the first validation fails.
We do run the conversion twice in this case, but its impact is less significant due to the rarity of this scenario.

```scala 3
  object ValidUserInADifferentWay:
    def otherValidation(userData: UserData): Boolean = false /* check that it's a child user */
    def unapply(userId: UserId): Option[UserData] = safeComplexConversion(userId).find(otherValidation)
  
  def findFirstValidUser7(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser6(user) => user
      case ValidUserInADifferentWay(user) => user
    }
```

Both extractor objects work in the same way.
They run a conversion method, which may or may not succeed.
If the conversion succeeds, its result is validated and returned when it is valid.
All of this is done with the `unapply` method, whose implementation stays the same regardless of the other methods.
This forms a nice framework which can be abstracted as a trait we call `Deconstruct`.
It has the `unapply` method that calls two abstract methods, `convert` and `validate`, which operate on generic
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
the `Deconstruct` trait during pattern matching:

```scala 3
  def findFirstValidUser8(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser8(user) => user
    }
```

### Exercise

You have noticed that the first cat found with a valid fur pattern has already been adopted. 
Now you need to include a check in the validation to ensure that the cat is still in the shelter. 

* Implement `nonAdoptedCatConversion` to only select cats that are still up for adoption.
* Copy your implementation of the `furCharacteristicValidation` function from the previous task. 
* Implement your custom `unapply` method for the `ValidCat` object, and use it to write the `unapplyFindFirstValidCat` function. The validation of fur characteristics should not be run on cats who have been adopted. 

Next, you notice that there are some inaccuracies in the coat patterns: no Bengal cat can be of a solid color! 

* Implement the validation of the coat pattern using a custom `unapply` method. 
* Use the `ValidPattern` object that extends the `Deconstruct` trait.
* Use the custom `unapply` method in the `findFirstCatWithValidPattern` function. 

