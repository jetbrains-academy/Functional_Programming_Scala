### `find`
(`def find(pred: A => Boolean): Option[A]`)

But imagine that instead of filtering for all black cats, we are happy with only one, no matter which. We still can use `filter` for that and then take the first cat from the resulting collection, but `filter` will iterate through the whole original collection of cats, no matter how big. There are better solutions. For example, we can use the `find` method, which works precisely like `filter` but stops at the first matching element.

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
val blackCat: Option[Cat] = cats.find { cat => cat.color == Black }

```

Note that the find method returns an `Option`. For now, you can think of an `Option` as a special type of collection that holds either zero or one element. If the predicate we used returned `false` for every element in the collection, the `find` method would return an empty `Option` (also known as `None`). We will talk more about `Option` in one of the following chapters.

Also, while we are here, look at the Scala documentation of methods `exists`, `forall`, and `contains`. They can be handy if you just want to check if an element in the collection fulfills specific requirements (or, in the case of `forall`, if all of them meet the requirements) but you are not interested in getting the matching element.