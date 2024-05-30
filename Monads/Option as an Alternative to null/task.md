Monad is a powerful concept widely used in functional programming. 
It's a design pattern capable of describing failing computations, managing state, and handling arbitrary side effects. 
Unlike Haskell, Scala's standard library doesn't include a specific Monad trait. 
Instead, a monad is a wrapper class `M[A]` that implements `flatMap`, the method for chaining several operations together.
Simplified, this method has the following type: 

`def flatMap[B](f: A => M[B]): M[B]` 

It executes a monadic computation that yields some value of type `A`, and then applies the function `f` to this value, resulting in a new monadic computation.
This process enables sequential computations in a concise manner. 

In addition to this, there should be a way to create the simplest instance of a monad.
Many monad tutorials written for Scala call it `unit`, but it may be misleading due to existence of `Unit`, the class with only one instance.
A better name for this method is `identity`, `pure` or `return`. 
We will be calling it `identity` for reasons that will become clear when we talk about monadic laws, a set of rules each monad should satisfy.
Its type is `def identity[A](x: A): M[A]`, meaning that it just wraps its argument into a monad, and in most cases it is just the `apply` methods of the corresponding class. 
In this lesson, we'll consider our first monad that should already be familiar to you. 

As you've probably already noticed, many real world functions are partial. 
For example, when dividing by 0, you get an error, and it fully aligns with our view of the world.
To make division a total function, we can use `Double.Infinity` or `Double.NaN` but this is only valid for this narrow case. 
More often, a `null` is returned from a partial function or, even worse, an exception is thrown.
Using `null` is called a billion-dollar mistake for a reason and should be avoided. 
Throwing exceptions is the same as throwing your hands in the air and giving up trying to solve a problem, passing it to someone else instead. 
These practices were once common, but now that better ways to handle failing computations have been developed, it's good to use them instead. 

`Option[A]` is the simplest way to express a computation, which can fail. 
It has two subclasses: `None` and `Some[A]`. 
The former corresponds to an absence of a result, or a failure, while the latter wraps a successful result. 
A safe, total, division can be implemented as follows:  

```scala 3
def div(x: Double, y: Double): Option[Double] =
  if (y == 0) None
  else Some(x / y)
```

Now, let's consider that you need to make a series of divisions in a chain. 
For example, you want to calculate how many visits your website gets per user per day.
You should first divide the total number of visits by number of users and then by number of days during which you collected the data. 
This calculation can fail twice, and pattern matching each intermediate results gets boring quickly. 
Instead, you can chain the operations using `flatMap`. 
If any of the divisions fail, then the whole chain stops.

```scala 3
div(totalVisits, numberOfUsers).flatMap { div(_, numberOfDays) }
```

Now let's see how `identity` and `flatMap` can be implemented. 
This is not exactly their implementation from the standard library, but it reflects the main idea. 

```scala 3
def identity[A](x: A): Option[A] = Some(x)

def flatMap[B](f: A => Option[B]): Option[B] = this match {
  case Some(x) => f(x)  
  case _       => None 
}
```

There is one more special case in Scala: if you pass `null` as an argument to the `Option` constructor, then you receive `None`. 
You should avoid doing this explicitly, but when you need to call a third-party Java library, which can return `nulls`: 

```scala 3
val result = javaLib.getSomethingOrNull(bar)
Option(result).foreach { res => 
    // will only be executed if the `result` is not null  
 }
```

In short, `None` indicates that something went wrong, and `flatMap` allows to chain function calls which do not fail. 

## Exercise

Let's consider users who are represented with the `User` class. 
Each user has a name, an age, and, sometimes, a child.
`UserService` represents a database of users along with some functions to search for them. 

Your task is to implement `getGrandchild` which retrieve a grandchild of the user with the name given if the grandchild exists. 
Here we've already put two calls to `flatMap` to chain some functions together, your task is to fill in what functions they are. 

Then implement `getGrandchildAge` which returns the age of the grandchild if they exist. 
Use `flatMap` here and avoid pattern matching. 

