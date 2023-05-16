### `filter`
(`def filter(pred: A => Boolean): Iterable[A]`)

The `filter` method works on any Scala collection that implements `Iterable`. It takes a predicate that returns `true` or `false` for every element in the collection, and it produces a new collection that consists only of elements for which the predicate returns `true`.

We already used `filter` in an example before, but, for consistency, let's do it again, this time with a slightly different implementation of that example. We will use a version of this example when discussing other Scala collections methods.

```scala
// We define the Color enum
enum Color:
 case Black, White, Ginger

// We define the Cat case class with two fields: name and color
case class Cat(name: String, color: Color)

// Let’s import the Color enum values for better readability
import Color._

// We create four cats, two black, one white, and one ginger
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// We put them all in a set
val cats = Set(felix, snowball, garfield, shadow)

// We filter the set to keep only black cats
val blackCats = cats.filter { cat => cat.color == Black }


```
