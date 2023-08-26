
# Case Objects

You might have noticed in the example of a binary tree implemented with sealed trait hierarchies, we used case object to introduce the Stump type. In Scala, a case object is a special type of object that combines characteristics and benefits of both case class and object.
Similar to a case class, a case object comes with a number of generated methods like toString, hashCode,  and equals which are useful for using it in pattern matching, and it can be used directly in pattern matching. On the other hand, just as any regular object, a case object is a singleton, i.e. there's exactly one instance of it in the entire JVM. Case objects are used in place of case classes when there's no need for parametrizing, when you don't need  to carry data, but you still want to benefit from pattern matching capabilities of case classes. In Scala 2, case objects implementing a common trait were a default way of achieving the enum functionality. This is no longer necessary in Scala 3, which introduced enums, but case objects are still useful in more complex situations.
For example, you may have noticed that to use case objects as enums, we make them extend a shared sealed trait.

```scala 3
sealed trait AuthorizationStatus

case object Authorized   extends AuthorizationStatus
case object Unauthorized extends AuthorizationStatus

def authorize(userId: UserId): AuthorizationStatus = ...
```

Here AuthorizationStatus is a sealed trait and Authorized and Unauthorized  are the only two case objects extending it, which means that the result of calling the authorize method might be always only either Authorized or Unauthorized.  There is no other response possible.
But imagine that you work on code which uses a library or a module that you don't want to modify anymore. In that case, the original author of that library or module might have used case objects extending a non-sealed trait to make it easier for you to add your own functionality:
```scala 3
// original code
trait AuthorizationStatus

case object Authorized   extends AuthorizationStatus
case object Unauthorized extends AuthorizationStatus

def authorize(userId: UserId): AuthorizationStatus = ...

// our extension
case object LoggedOut extends AuthorizationStatus

override def authorize(userId: UserId): AuthorizationStatus =
if isLoggedOut(userId) then
LoggedOut
else
super.authorize(userId)
```

Here we extend the functionality of the original code by adding a possibility that the user is authorized to perform a given operation, but something went wrong in the meantime and the user was logged out. Now they need to log in again before they are able to continue. This is not the same as simply being Unauthorized so we add a third case object to the set of those extending AuthorizationStatus: we call it LoggedOut. If the original author used sealed trait to define AuthorizationStatus, or if they used enum, we wouldn't be able to do that.
