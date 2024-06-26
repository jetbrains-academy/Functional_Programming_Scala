The *newtype pattern* in Scala is a way of creating new types from existing ones that are distinct at compile time 
but share the same runtime representation. 
This approach can be useful for adding more meaning to simple types, enforcing type safety, and avoiding mistakes.

For  example, consider a scenario where you are dealing with user IDs and product IDs in your code. 
Both IDs are of type `Int`, but they represent  completely different concepts. 
Using `Int` for both may lead to bugs  where you accidentally pass a user ID where a product ID was expected, or vice versa. 
The compiler wouldn't catch these errors because both IDs are of the same type, `Int`.

With the newtype pattern, you can create distinct types for `UserId` and `ProductId` that wrap around `Int`, providing more safety:

```scala 3
case class UserId(value: Int) extends AnyVal
case class ProductId(value: Int) extends AnyVal
```

These are called value classes in Scala. `AnyVal` is a special trait in Scala — when extended by a case class 
that has only a single field, you're telling the compiler that you want to use the newtype pattern. 
The compiler uses this information to catch any bugs, such as confusing integers used 
for user IDs with those used for product IDs. However, at a later phase, it strips the type information from the data, 
leaving only a bare `Int`, so that your code incurs no runtime overhead.
Now, if you have a function that accepts a `UserId`, you can no longer mistakenly pass a `ProductId` to it:

```scala 3
case class UserId(value: Int) extends AnyVal
case class ProductId(value: Int) extends AnyVal
case class User(id: UserId, name: String)

def getUser(id: UserId): User = ???
val userId = UserId(123)
val productId = ProductId(456)

// getUser(productId) is a compile error
val user = getUser(userId) // This is fine
```

In Scala 3, a new syntax has been introduced for creating newtypes using *opaque type aliases*, although the concept remains the same. 
The above example would look as follows in Scala 3:

```scala 3
object Ids:
  opaque type UserId = Int
  object UserId:
    def apply(id: Int): UserId = id

  opaque type ProductId = Int
  object ProductId:
    def apply(id: Int): ProductId = id

import Ids.*
case class User(id: UserId, name: String)

def getUser(id: UserId): User = ???
val userId = UserId(123)
val productId = ProductId(456)

// getUser(productId) is a compile error
val user = getUser(userId) // This is fine
```

As you can see, some additional syntax is required. 
Since an opaque type is essentially a type alias and not a case class, we need to manually define `apply` methods 
for both `UserId` and `ProductId`. 
Also, it's essential to define these methods within an object or a class — they cannot be top-level definitions. 
On the other hand, opaque types integrate very well with extension methods, another new feature in Scala 3. 
We will discuss this in more detail later.

### Exercise 

One application of opaque types is expressing units of measure. 
For example, in a fitness tracker, users can input the distance either in feet or meters, 
based on their preferred measurement system.
Implement functions for tracking distance in different units and a `show` function to display 
the tracked distance in the preferred units. 
