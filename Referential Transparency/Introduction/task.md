# Referential Transparency

**Note**: You can run both versions of the example from your terminal, with `sbt run`.

We will finish the course with a module about Referential Transparency (RT, in short), a concept tightly connected to the idea of a pure function that we discussed before. A pure function operates only on its input parameters and has no side effects, i.e., the only result of the function call should be the value it returns. The function will not modify data in any data collection, write to a file, access any object it wasn't given a reference to in the form of an input parameter, etc.
Referential Transparency takes pure functions even one step further. For a function to be referentially transparent, it must hold that replacing the function call with the value it returns will not affect the program's behaviour.
Imagine that we have a case class of a protagonist, with their first and last name and their age:

```scala
case class Protagonist(firstName: String,
                       lastName: String, 
                       age: Int)
```

and we have a function called `updateAge` that, for a given protagonist instance and a number, returns a copy of the protagonist with the updated age:

```scala
def updateAge(protagonist: Protagonist, n: Int): Protagonist =
  protagonist.copy(age = protagonist.age + n)
```

The function is pure. It operates only on its parameters, `protagonist` and `n`, and its only result is the value it returns, namely a new instance of the case class `Protagonist`. Referential Transparency holds as well. The code which looks like this:

``` scala
// program version #1
val protagonist = Protagonist("Jonas", "Kahnwald", 17)

// returns Protagonist("Jonas", "Kahnwald", 50)
val newProtagonist = updateAge(protagonist, 33) 

// returns Protagonist("Jonas", "Kahnwald", 83)
val newerProtagonist = updateAge(newProtagonist, 33)
```

can be replaced with:

``` scala
// program version #2
val protagonist = Protagonist("Jonas", "Kahnwald", 17)
val newProtagonist = Protagonist("Jonas", "Kahnwald", 50)
val newerProtagonist = Protagonist("Jonas", "Kahnwald", 83)
```

and it won't change the program's behaviour. Also, the following program will behave the same:

```scala
// program version #3
val protagonist = Protagonist("Jonas", "Kahnwald", 17)
val newProtagonist = updateAge(protagonist, 33)
val newerProtagonist = updateAge(updateAge(protagonist, 33), 33)
```

The function call and the value are virtually interchangeable. But let's say we want to update the protagonist's age and print out that we made the update. We could do this only for debugging purposes or write it down in a file to track how the protagonist's age changes.

```scala
def updateAge(p: Protagonist, n: Int): Protagonist =
  val newAge = p.age + n
  println(s"The age of ${p.firstName} ${p.lastName} changes from ${p.age} to $newAge")
  p.copy(age = newAge)
```

Now, all three versions of the program still give use the same values for `protagonist`, `newProtagonist` and `newerProtagonist` but what we see in the logs is different:

```
// program version #1:
Age of Jonas Kahnwald changes from 17 to 50
Age of Jonas Kahnwald changes from 50 to 83

// program version #2:
[no logs]

// program version #3:
Age of Jonas Kahnwald changes from 17 to 50
Age of Jonas Kahnwald changes from 17 to 50
Age of Jonas Kahnwald changes from 50 to 83
```

What happened? Logging is a side-effect. Our new `updateAge` function is not pure, and so it is also not referentially transparent anymore. But does it mean we should avoid logging in Functional Programming? Of course not. If we think about it, logging is just the most evident and straightforward example of when side-effects are helpful. Reading from a file, writing to a file, displaying new information on the screen, asking the user for information and waiting for it, accessing and modifying a database, sending an HTTP request, generating a random number, or even caching a result of CPU-heavy computations - these are all side-effects. If we were to avoid them all, Functional Programming would be useless.

One way to overcome this conundrum is to apply the Berliner pattern (discussed in a previous chapter) or a similar strategy to divide the codebase into purely functional, and "effectful" parts. The pure-FP part will never perform side-effects alone but will delegate that role to non-pure-FP parts of your code.

But this solution might feel insufficient. It means that there will always be a part of your code that is not as safe as it could be. Fortunately, there is a way to achieve referential transparency and the ability to perform effectful operations like the ones mentioned above simultaneously. We can do it with the IO monad and an effect system.

We discussed monads before and saw examples of a few simple ones: `Option`,` Either`, and `Try`. They all hold some data inside and let us transform it by calling `flatMap` and other methods. The IO monad works precisely like that but holds the program as a value. That program is a list of pure and effectful operations we want to perform in order. We can add new operations to the list by calling methods on the IO monad. In other words, we may say that the IO monad lets us create a scenario of what we want our program to do. Instead of making the program perform all those operations immediately, we add them individually to the IO monad, creating a scenario that will be played out later.

An effect system is a framework in which we write our code. In it, we build our scenario in the IO monad, and when it's ready, we give the IO monad to the effect system to run it. It is an additional layer of abstraction between us and the execution of our program: instead of writing code that is executed by our machine, we write code, give it to the effect system, and the effect system runs it for us. This approach enables better separation of concerns (planning vs. execution), helps with testing and debugging, as pure functions are easier to reason about, and also makes it way easier to design logic that uses concurrency - both because pure functions are safe to call concurrently, and because the effect system supervises the execution of the program.

The effect system we will use in the following example is Cats Effect 3. In the example, we will use it not only for logging but also for reading and writing to the file, as well as asking the user for the value we will then use to update the protagonists' age. The example needs to be simple enough to be usable in the context of the course, and, as a result, it may look overengineered, as it requires some boilerplate. However, when you start to write larger projects in Cats Effect 3, the amount of boilerplate will stay similar or even diminish as you learn to write idiomatic Cats Effect code, while the advantages of using Cats Effect get more visible while the project grows.
