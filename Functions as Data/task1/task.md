
<iframe width="560" height="315" src="https://www.youtube.com/embed/RX1_EJp9Vxk" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
A function is a standalone block of code that takes arguments, performs some calculations, and returns a result. It may or may not have side effects; that is, it may have access to the data in the program, and if that data is modifiable, the function may change it. If it doesn't — meaning, if the function operates only on its arguments — we say that the function is pure. In functional programming, we use pure functions when possible, although this rule has important exceptions. We will talk about them later.
The main difference between a function and a method is that a method is associated with a class or an object. In contrast, a function is treated as any other value in the program: it can be created in any place in the code, passed as an argument, returned from another function or method, etc.

Consider the following code:
```Scala

// A function defined with `def`
def add1(x: Int, y: Int): Int = x + y

// A function defined as a value
val add2: (Int, Int) => Int = (x, y) => x + y

// A method associated with a class
class Calculator:
  def add(x: Int, y: Int): Int = x + y

```

Both `add` functions take two input parameters, `x` and `y`, perform a pure computation of adding them together, and return the result. They do not modify any external state.
In the first case, we define a function with the `def` keyword. After def comes the function's name, then the list of arguments with their types, then the result type of the function, and then the function's calculations, that is, `x + y`.

Compare it with the second way to define a function, with the `val` keyword, which we use as well for all other kinds of data. Here, after val comes the function's name, then the type of the function, `(Int, Int) => Int`, which consists of both the argument types and the result type, then come the arguments, this time without the types, and then the implementation. You will probably find the first way to define functions as more readable, and you will use it more often. But it is important to remember that in Scala, a function is data, just like integers, strings, and instances of case classes — and that it can be defined as data if needed.

The third example shows a method. We call it simply add. Its definition looks the same as the definition of the function `add1`, but we refer to add as a method because it is associated with the class `Calculator`. This way, if we create an instance of `Calculator`, we can call add on it, and it will have access to the internal state of the instance. It is also possible, for example, to override it in a subclass of `Calculator`.

```scala
// An instance of the class Calculator. The instance has no internal state.
val calc = new Calculator
// We call add(1, 2) on calc. It returns 3 (1 + 2).
calc.add(1, 2)

// A subclass of Calculator that has the internal state: the integer n.
class CalculatorPlusN(n: Int) extends Calculator:
  // The overridden method `add3` that adds n from the internal state to the result of addition.
  override def add(x: Int, y: Int): Int = super.add(x, y) + n

// An instance of CalculatorPlusN with the internal state, n == 3.
val calc3 = new CalculatorPlusN(3)
// We call add3 on calc3. It returns 6 (1 + 2 + 3)
calc3.add(1 , 2)

```
<div class="hint" title="See the blog post">

You can find it <a href="https://makingthematrix.wordpress.com/2020/12/15/programming-with-functions-2-functions-as-data/>here</a>">here</a>.

</div>


