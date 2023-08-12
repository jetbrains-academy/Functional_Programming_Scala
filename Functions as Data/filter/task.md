# `filter`

```def filter(pred: A => Boolean): Iterable[A]```

The `filter` method works on any Scala collection that implements `Iterable`. 
It takes a predicate that returns `true` or `false` for every element in the collection, and it produces a new 
collection that consists only of elements for which the predicate returns `true`.
The `filter` method returns and empty collection, if predicate does not succeed on any element.

We already used `filter` in an example before, but, for consistency, consider the example below one more time. 

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


## Exercise

In the exercises we will be working with a more detailed representation of cats, than in the lessons. 
Check out the `Cat` class in `src/Cat.scala`.
A cat has multiple characteristics: its name, breed, color, pattern, and a set of additional fur characteristics, such as
`Fluffy` or `SleekHaired`.
Familiarize yourself with the corresponding definitions in other files in `src/`.

Imagine you went into an animal shelter with the intent to adopt a cat.
There are multiple cats there, and you wish to adopt a cat with one of the following characteristics:

* The cat is calico.
* The cat is fluffy.
* The cat's breed is Abyssinian.

To make it easier to make a decision, you first find all cats which have at least one of the characteristics above. Your task is to implement the necessary functions and then apply the filter. 
