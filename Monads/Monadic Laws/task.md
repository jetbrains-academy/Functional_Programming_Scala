There are multiple monads not covered in this course. 
Monad is an abstract concept, and any code that fulfills certain criteria can be viewed as one. 
What are the criteria we are talking about, you may ask. 
They are called monadic laws, namely left and right identity, and associativity. 

## Identity Laws

The first two properties are concerned with `unit`, the constructor to create monads. 
Identity laws mean that there is a special value that does nothing when a binary operator is applied to it. 
For example, `0 + x == x + 0 == x` for any possible number `x`. 
Such an element may not exist for some operations, or it may only work on one side of the operator. 
Consider subtraction, for which `x - 0 == x`, but `0 - x != x`. 
As it happens, the unit is supposed to be the identity of the `flatMap` method. 
Let's take a look at what it means exactly. 

The left identity law says that if we create a monad from a value `v` with a unit method `Monad` and then `flatMap` a function `f` over it, it is equivalent to passing the value `v` straight to the function `f`: 

```scala 3
def f(value: V): Monad[V]

Monad(v).flatMap(f) == f(v)
```
The right identity law states that by passing the unit method into a `flatMap` is equivalent to not doing that at all. 
This reflects the idea that unit only wraps whatever value it receives and produces no effect. 

```scala 3
val monad: Monad[_] = ...

monad.flatMap(Monad(_)) == monad
```

## Associativity 

Associativity is a property that says that you can put parentheses in a whatever way in an expression and get the same result. 
For example, `(1 + 2) + (3 + 4)` is the same as `1 + (2 + 3) + 4` and `1 + 2 + 3 + 4`, since addition is associative. 
At the same time, subtraction is not associative, and `(1 - 2) - (3 - 4)` is different from `1 - (2 - 3) - 4` and `1 - 2 - 3 - 4`.

Associativity is desirable for `flatMap` because it means that we can unnest them and use for-comprehensions safely. 
In particular, let's consider two monadic actions `mA` and `mB` followed by some running `doSomething` function over the resulting values.
This code fragment is equivalent to putting parentheses around the pipelined `mB` and `doSomething`. 

```scala 3
mA.flatMap( a =>
  mB.flatMap( b =>
    doSomething(a, b)
  )
)
```

This can be refactored in the following form, using the unit of the corresponding monad. 
Here we parenthesise the chaining of the two first monadic actions, and only then flatMap `doSomething` over the result.  

```scala 3
mA.flatMap { a => 
  mB.flatMap(b => Monad((a, b)))  
}.flatMap { case (a, b) =>  
  doSomething(a, b)
}
```

We can make this code pretty by sprinkling some syntactic sugar. 

```scala 3
for {
  a <- mA 
  b <- mB 
  res <- doSomething(a, b)
} yield res 
```

## Do Option and Either Follow the Laws? 

Now that we know what the rules are, we can check whether the monads we are familiar with play by them.
The unit of `Option` is `{ x => Some(x) }`, while `flatMap` can be implemented in the following way. 

```scala 3
def flatMap[B](f: A => Option[B]): Option[B] = this match {
  case Some(b) => f(b)
  case _       => None
}
```

The left identity law is straightforward: `Some(x).flatMap(f)` just runs `f(x)`. 

To prove the right identity, let's consider the two possibilities for `monad` in `monad.flatMap(Monad(_))`. 
The first is `None`, and `monad.flatMap(Option(_)) == None.flatMap(Option(_)) == None`. 
The second is `Some(x)` for some `x`. Then, `monad.flatMap(Option(_)) == Some(x).flatMap(Option(_)) == Some(x)`. 
In both cases, we arrived to the value that is the save as the one we started with. 

Carefully considering the cases is how we prove associativity. 

1. If `mA == None`, both expressions are immediately `None`.  
2. If `mA == Some(x)` and `mB == None`, then both expressions are eventually `None`. 
3. If `mA == Some(x)` and `mB == Some(y)`, then the first expression results in `doSomething(x, y)`. Let's prove that the second expression is evaluated to the same value.

```scala 3
Some(x).flatMap { a => 
  Some(y).flatMap(b => Some((a, b)))  
}.flatMap { case (a, b) => doSomething(a, b) }
```

This expression gets evaluated to:  

```scala
Some(b).flatMap(b => Some((x, b)))  
  .flatMap { case (a, b) => doSomething(a, b) }
```

Which is evaluated to: 

```scala 3
Some(x, y).flatMap { case (a, b) => doSomething(a, b) }
```

Finally, we get `doSomething(x, y)` which is exactly what we wanted. 

If you want to make sure you grasp the concepts of monadic laws, go ahead and prove that `Either` is also a monad.  

## Beyond Failure

We only covered monads capable of describing failures and non-determinism. 
There are many other *computational effects* that are expressed via monads. 
They include logging, reading from a global memory, state manipulation, different flavours of non-determinism and many more. 
We encourage you to explore these monads on your own.
Once you feel comfortable with the basics, take a look at the [scalaz](https://scalaz.github.io/7/) and [cats](https://typelevel.org/cats/) libraries. 








