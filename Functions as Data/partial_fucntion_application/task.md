# Partial function application
Returning functions from functions is related to, but not the same as [partial application](https://en.wikipedia.org/wiki/Partial_application).
The former lets you create functions which behave as if they had a "hidden" list of arguments that you provide at the moment of their creation, instead of at the moment of usage.
Each function returns a new function that takes the following argument until all arguments are collected, and the final function returns the result.

On the other hand, partial function application is the process of giving fixed values to some of the arguments of a function and returning a new function that takes only the remaining arguments.
The new function is a specialized version of the original function with some arguments already provided.
This technique allows us to reuse code - we can write one more generic function and then construct its specialized versions to use in different contexts.
Here's an example:

```scala
// Define a function that takes three arguments
def addN(x: Int, y: Int, n: Int) = x + y + n
// Partially apply the 'addN' function to set the last argument to 3
val add3 = addN(_: Int, _: Int, 3)
// Call the partially applied function with the remaining arguments
val result = add3(1, 2) // the result is 6 (1 + 2 + 3)
```

In the example above, we define a function `addN` that takes three arguments, `x`, `y`, and `n`, and returns their sum.
We then partially apply the `addN` function to set the last argument to 3, using the `_` placeholder for the first two.
This way, we create a new function, `add3`, that takes only two arguments, `x`, and `y`.
Finally, we call `add3` with only two arguments, and we get the same result as in the case of the function returning a function from the previous chapter and the `CalculatorPlusN` example from the first task.

## Exercise 

Implement a function `filterList` which then can be partially applied.
You can use `filter` method in the implementation.

