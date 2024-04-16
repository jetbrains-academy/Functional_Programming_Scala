## Lazy Collection to the Resque

One more way to achieve the same effect of an early return is to use the concept of a lazy collection. 
A lazy collection doesn't store all its elements computed and ready to access. 
Instead, it stores a way to compute an element once it's needed somewhere. 
This makes it possible to simply traverse the collection until we encounter the element which fulfills the conditions. 
Since we aren't interested in the rest of the collection, its elements won't be computed. 

As we've already seen a couple of modules ago, there are several ways to make a collection lazy.
The first one is by using [iterators](https://www.scala-lang.org/api/current/scala/collection/Iterator.html): we can call the `iterator` method on our sequence of identifiers. 
Another way is to use [views](https://www.scala-lang.org/api/current/scala/collection/View.html) as we've done in one of the previous modules. 
Try comparing the two approaches on your own.

```scala 3
  def findFirstValidUser9(userIds: Seq[UserId]): Option[UserData] =
    userIds
      .iterator
      .map(safeComplexConversion)
      .find(_.exists(complexValidation))
      .flatten
```

### Exercise

Let's try using lazy collection to achieve the same goal as in the previous tasks. 

* Use a lazy collection to implement `findFirstValidCat`.
* Copy the implementations of `furCharacteristicValidation` and `nonAdoptedCatConversion` from the previous task. 
