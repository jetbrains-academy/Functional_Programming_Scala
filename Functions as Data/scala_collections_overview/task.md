# Scala collections overview

Scala collections are a large set of data structures that provide powerful and flexible ways to manipulate and organize data. 
The Scala collections framework is designed to be both easy to use and expressive, allowing you to perform complex operations with concise and readable code. 
To achieve this, many methods take functions as arguments.

Scala collections are divided into two main categories: mutable and immutable. 
Immutable collections cannot be changed after creation but can be copied with modifications, while mutable collections can be updated in place. 
By default, Scala encourages using immutable collections because they are easier to reason about and can help prevent bugs caused by unexpected side effects.

Here's an overview of the main traits and classes:
1. `Iterable`: All collections that can be iterated in a linear sequence extend `Iterable`. It provides methods like `iterator`, `map`, `flatMap`, `filter`, and others that we will discuss shortly.
2. `Seq`: This trait represents sequences, i.e., ordered collections of elements. It extends `Iterable` and provides methods like `apply(index: Int): T` (letting you get an element under the index) and `indexOf(element: T): Int` (returning the index of the first element in the sequence which matches the element you provided, or -1 if the element can't be found). Some essential classes implementing the `Seq` trait are `List`, `Array`, `Vector`, and `Queue`.
3. `Set`: Sets are unordered collections of unique elements. It extends Iterable but not `Seq` - you can't assign fixed indices to its elements. The most common implementation of `Set` is `HashSet`.
4. `Map`: A map is a collection of key-value pairs. It extends Iterable and provides methods like `get`, `keys`, `values`, `updated`, and more. It's unordered, just like `Set`. The most common implementation of `Map` is `HashMap`.

Now we will quickly go through some of the most often used methods of Scala collections: `filter`, `find`, `foreach`, `map`, `flatMap`, and `foldLeft`. 
In each case, you will see a code example and be asked to do an exercise using the given method. 
Please note that there are many others. We encourage you to visit [Scala collections documentation](https://www.scala-lang.org/api/current/scala/collection/index.html) and browse through them - knowing that they exist and that you can use them instead of writing some logic yourself may save you a lot of work.

