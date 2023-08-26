# Sealed Traits Hierarchies

Sealed  traits in Scala are used to represent restricted class hierarchies that provide exhaustive type checking. When a trait is declared as sealed,  it can only be extended within the same file. This lets the compiler  know all of the subtypes, which allows for more precise compile-time checking.
With the introduction of enums in Scala 3, many use-cases of sealed traits are now covered by them, as their syntax is more concise. However, sealed traits are still more flexible than enums – they  allow for the addition of new behavior in each subtype. For instance, we can override the default implementation of a given method differently in each case class that extends  the parent trait. In enums, all enum cases share the same methods and fields.
``` 
sealed trait Tree[A]:
def whoAmI: String

case class Branch[A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]:
override def whoAmI: String = "I'm a branch!"

case class Leaf[A](value: A) extends Tree[A]:
override def whoAmI: String = "I'm a leaf!"

case object Stump extends Tree[Nothing]:
override def whoAmI: String = "I'm a stump!"
```

The code for creating the tree looks exactly the same:

```
import Tree.*

val tree: Tree[Int] =
Branch(
Branch(Leaf(1), 2, Stump),
3,
Branch(Leaf(4), 5, Leaf(6))
)
```