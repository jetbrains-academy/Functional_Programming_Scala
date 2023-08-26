# `foldLeft`

`def foldLeft[B](acc: B)(f: (B, A) => B): B`

The `foldLeft` method is another method in Scala collections that can be explained as a generalized version of `map` but generalized differently than `flatMap`. 
Let's say that we call `foldLeft` on a collection of elements of type `A`. 
`foldLeft` takes two arguments: the initial "accumulator" of type `B` (usually different from `A`) and a function `f`, which again takes two arguments: the accumulator (of type `B`) and the element from the original collection (of type `A`). 
`foldLeft` starts its work by taking the initial accumulator and the first element of the original collection and giving them to `f`. 
The `f` function uses these two to produce a new version of the accumulator - i.e., a new value of type `B`. 
That new value, the new accumulator, is given to `f` again, this time together with the second element in the collection. 
The process repeats itself until all elements of the original collection are iterated over. 
The final result of `foldLeft` is the accumulator value after processing the last element of the original collection.

The "fold" part of the `foldLeft` method's name comes from the idea the way `foldLeft` works might be imagined as "folding" a collection of elements, one into another, and finally into a single value - the final result. 
The suffix "left" is there to indicate that in the case of ordered collections, we are going from the beginning of the collection (left) to its end (right). 
There is also `foldRight` which works in the reverse direction.

Let's discuss how `foldLeft` on the popular coding exercise, *FizzBuzz*. 
In *FizzBuzz*, we are supposed to print out a sequence of numbers from 1 to a given number (let's say 100). 
But, every time the number we are to print out is divisible by 3, we print out "Fizz" instead; if it's divisible by 5, we print out "Buzz"; and if it's divisible by 15, we print out "FizzBuzz". 
Here is how we can achieve it with foldLeft in Scala 3:

```scala
def fizzBuzz(n: Int): Int | String = n match
 case _ if n % 15 == 0 => "FizzBuzz"
 case _ if n % 3  == 0 => "Fizz"
 case _ if n % 5  == 0 => "Buzz"
 case _ => n

// Generate a range of numbers from 1 to 100
val numbers = 1 to 100

// Use foldLeft to iterate through the numbers and apply the fizzBuzz function
val fizzBuzzList = numbers.foldLeft[List[Int | String]](Nil) { (acc, n) => acc :+ fizzBuzz(n) }

println(fizzBuzzList)
```

First, we write the `fizzBuzz` method, which takes an `Int` and returns either an `Int` (the number that it took) or a `String: "Fizz", "Buzz", or "FizzBuzz". 
Scala 3 introduced the concept of union types. 
With them, we can declare that our method can return any of two or more unrelated types, but it will surely be one of them.

Then we create a range of numbers from 1 to 100 using 1 to 100.

We call the `foldLeft` method on the numbers range, declaring that the accumulator will be of the type `List[String | Int]` and that initially, it will be empty (`Nil`).

The second argument to `foldLeft` is a function that takes the current accumulator value (`acc`) and an element from the numbers range (`n`). 
This function calls our `fizzBuzz` method with the number and attaches the result to the accumulator list using the `:+` operator.

When all the elements have been processed, `foldLeft returns the final accumulator value, which is the complete list of numbers and strings "Fizz", "Buzz", and "FizzBuzz", in places where the numbers were divisible by 3, 5, and 15, respectively.

Finally, we print out the results.

## Exercise 

Implement the following functions using `foldLeft`: 
* `computeAverage` function which computes the average of the list of numbers;
* `maximum` finds the maximum number of the list;
* `reverse` reverses the list.