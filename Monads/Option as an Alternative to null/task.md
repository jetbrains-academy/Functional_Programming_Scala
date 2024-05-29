Monad is a powerful concept popular in functional programming. 
It's a design pattern capable of describing failing computations, managing state, and handling arbitrary side effects. 
Unlike in Haskell, there is no specific Monad trait in the standard library of Scala. 
Instead, a monad is a wrapper class that has a static method `unit` and implements `flatMap`. 
The method `unit` accepts a value and creates a monad with it inside, while `flatMap` chains operations on the monad.
For a class to be a monad, it should satisfy a set of rules, called monadic laws. 
We'll cover them at a later stage. 
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

There is one more special case in Scala: if you pass `null` as an argument to the `Option` constructor, then you receive `None`. 
You should avoid doing this explicitly, but when you need to call a third-party Java library, which can return `nulls`: 

```scala 3
val result = javaLib.getSomethingOrNull(bar)
Option(result).foreach { res => 
    // will only be executed if the `result` is not null  
 }
```

In short, `None` indicates that something went wrong, and `flatMap` allows to chain function calls which do not fail. 

### Exercise

Let's consider users who are represented with a `User` class. 
Each user has a name, an age, and possibly a child.
`UserService` represents a database of users along with some functions to search for them. 

Your task is to implement `getGrandchild` which retrieve a grandchild of the user with the name given if the grandchild exists. 
Here we've already put two calls to `flatMap` to chain some functions together, your task is only fill in what functions these are. 

Then implement `getGrandchildAge` which returns the age of the grandchild if they exist. 
Use `flatMap` here, avoid pattern matching. 

