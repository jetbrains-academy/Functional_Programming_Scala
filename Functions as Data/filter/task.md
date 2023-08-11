### `filter`
(`def filter(pred: A => Boolean): Iterable[A]`)

The `filter` method works on any Scala collection that implements `Iterable`. 
It takes a predicate that returns `true` or `false` for every element in the collection, and it produces a new 
collection that consists only of elements for which the predicate returns `true`.

We already used `filter` in an example before, but, for consistency, let's do it again, this time with a slightly 
different implementation of that example. We will use a version of this example when discussing other Scala collections 
methods.

Take a look at the `Cat` class in `src/Cat.scala`. 
Now a cat has multiple characteristics: its breed, color, pattern and a set of additional fur characteristics, such as 
`Fluffy` or `SleekHaired`. 
Familiarize yourself with the corresponding definitions in other files in `src/`. 



## Exercise

Imagine you went into an animal shelter with the intent to adopt a cat from there.
There are multiple cats there and you wish to adopt a cat with one of the following characteristics:

* The cat is calico.
* The cat is fluffy.
* The cat's breed is Abyssinian.

To make it easier to make a decision, you first find all cats which have at least one of the characteristics above.  
Your task is to implement the necessary functions and then apply the filter. 
