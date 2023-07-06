### Anonymous functions

An anonymous function is a function that, quite literally, does not have a name. It is defined only by its arguments list, and computations. Anonymous functions are also known as lambda functions, or simply lambdas.

Anonymous functions are particularly useful when we need to pass a function as an argument to another function, or when we want to create a function that is only used once and is not worth defining separately.

Here's an example:

```scala
// We create a sequence of numbers.
val numbers = Seq(1, 2, 3, 4, 5)

// We use the Seq.map method to double each number in the sequence, using an anonymous function.
val doubled = numbers.map(x => x * 2)
```

Here we create a sequence of `numbers` and we want to double each of them. To do that we use the `map` method. We define an anonymous function `x => x * 2` and give it to the `map` method as its only argument. The `map` method applies this anonymous function to each element of `numbers` and returns a new list, which we call `doubled`, containing the doubled values.

Anonymous functions can access to variables which are in scope at their 
definition.
Consider `multiplyList` function which multiplies every number in a list 
by `multiplier`. 
The parameter `multiplier` can be used inside `map` with no issues.  

```scala
def multiplyList(multiplier: Int, numbers: List[Int]): List[Int] = {
  // We can use multiplier inside map 
  numbers.map(x => multiplier * x)
}
```

## Exercise

Implement `multiplyAndOffsetList` function which multiplies and offsets each 
element of the list. Use map and an anonymous function. 
