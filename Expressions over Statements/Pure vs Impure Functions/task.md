## Pure vs Impure Functions

Not all functions are created equal, some of them are better than the others. 
One big group of these better functions are called *pure*. 
A pure function always results in the same value if given the same inputs. 
For example, the mathematical function of doubling the number `double(x) = 2 * x` always returns `42` when given `21` as an 
argument. 
Whereas the function `g` which gets one number as an argument, reads another from the standard input and then multiplies them,
does not always compute the same result when called on `21`. 

```scala 3
def g(x: Int): Int =
  val y = StdIn.readInt()
  x * y

println(g(21)) // Input: 1 => printed 21
println(g(21)) // Input: 3 => printed 63
```

One other consequence of having to always produce the same result is that a pure function cannot do any side effects. 
For instance, a pure function cannot output anything, it cannot interact with a database or write into a file. 
It also cannot read from the console, database or a file, modify its arguments, or throw an exception. 
The result only depends on the arguments and the implementation of the function itself. 
The external world should never influence the function or be influenced by it.

You may protest that pure functions are completely useless. 
If they cannot interact with the outer world or mutate anything, how are we supposed to get any value out of them? 
Why even use pure functions? 
The thing is that they are much better behaved than impure ones.
Since there are no hidden interactions, it's much easier to see and prove that your pure function does what it 
is supposed to do and nothing else. 
Moreover, they are much easier to test: you do not need to mock a database if the function never interacts with one. 

Some programming languages, such as Haskell, restrict impurity and reflect any side effects in types. 
However, it can be quite restricting and is not an approach used in Scala.  
The idiomatic way is to write your code in such a way that as much of it as possible is pure, and use impurity only 
where it is absolutely necessary, similarly to what we did with mutable data. 
For example, the function `g` can be split into two: the one which reads the number from the standard input, and the one 
responsible for multiplication: 

```scala 3
def gPure(x: Int, y: Int): Int =
  x * y 
  
def g(x: Int): Int =
  val y = StdIn.readInt()
  gPure(x, y)
```


