# Currying functions

In Functional Programming, it is possible to construct functions dynamically using function currying.
Currying transforms a function that takes multiple arguments into a series of functions that each take a subset of the arguments (ideally, they should each take only one).
This technique allows us to create new functions based on the arguments given to the original function and return them as the result of that function.


Currying is useful when we need to create specialized functions based on a common pattern or behavior.
For example, consider the class `CalculatorPlusN` we wrote in the chapter "What is a function?".
In that example, we created a class that took a number `n` in its constructor and then used that number in the method `add(x: Int, y: Int)` by adding it to the sum of `x` and `y`.

```scala
class CalculatorPlusN(n: Int) extends Calculator:
// The overridden method `add` adds `n` from the internal state to the result of the addition.
override def add(x: Int, y: Int): Int = super.add(x, y) + n
// An instance of `CalculatorPlusN` with the internal state, `n`, equal to 3.
val calc = new CalculatorPlusN(3)
// We call `add` on `calc`. It returns 6 (1 + 2 + 3)
calc.add(1 , 2)
```

Now, instead of having a class that holds this additional number `n`, we can use currying to achieve the same result:

```scala
// Define a curried function that takes a fixed number and returns a new function that adds it to its input
def addFixedNumber(n: Int): (Int, Int) => Int =
  def adder(x: Int, y: Int): Int = x + y + n
  adder
// Create a specialized function that adds 3 to its input
val add = addFixedNumber(3)
// We call add. It returns 6 (1 + 2 + 3)
add(1, 2)
```

In the above example, we define a curried function `addFixedNumber` that takes an integer `n` and returns a new function that takes two integers, `x` and `y`, and returns the sum of `n` and `x` and `y`.
Note how the return type of `addFixedNumber` looks like - it's a function type `(Int, Int) => Int`.
Then, we define the new function adder inside `addFixedNumber`, which captures the value of `n` and adds it to its own two arguments, `x` and `y`.
The `adder` function is then returned as the result of `addFixedNumber`.

We then construct a specialized function add by calling `addFixedNumber(n: Int)` with `n equal to 3.
Now, we can call add on any two integers; as a result, we will get the sum of these integers plus 3.

Scala provides special syntax for functions which can be curried, shown below:  

``` 
def addFixedNumber(n: Int)(x: Int, y:Int) =
  x + y + n

val add = addFixedNumber(3)
```

The first argument of the function `addFixedNumber` is put in its own parentheses while the second and the third are put into another pair of parentheses.
The function `addFixedNumber` can then be carried, when it's supplied only with the first argument. 
You can also call the function with all three arguments, but they should be put in separate parentheses: `addFixedNumber1(3)(4, 5)` instead of `addFixedNumber(3,4,5)`. 
Notice that you cannot pass two arguments into the function written in this syntax: `addFixedNumber1(3)(4)` is not allowed. 



## Exercise 

// Implement a function `filterList` which can be carried.
// You can use `filter` method in the implementation.

