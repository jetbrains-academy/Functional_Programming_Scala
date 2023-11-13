## Nested Methods 

In Scala, it's possible to define methods within other methods. 
This is useful when you have a function which is only supposed to be used once. 
For example, you may want to implement the factorial function in a tail recursive style, but do not want to allow 
the user to call the function with an arbitrary accumulator parameter. 
In this case you expose a normal one-parameter function `factorial` which calls the nested tail-recursive implementation 
`fact` with the appropriate accumulator: 

```scala 3
def factorial(x: Int): Int =
  def fact(x: Int, accumulator: Int): Int =
    if x <= 1 then accumulator
    else fact(x - 1, x * accumulator)
  fact(x, 1)
```

The alternative option is to put the `fact` function on the same level as `factorial` and make it `private`. 
This will still allow other functions in the same module to access `fact`, whereas nesting it makes it only available 
from inside the `factorial` function. 
You can also have nested methods inside other nested methods, the rules of scoping are the same: the nested function is 
only accessible from within its outer function: 

```scala 3
def foo() = {
  def bar() = {
    def baz() = { }
    baz()
  }
  def qux() = {
    def corge() = { }
    corge() // A nested function can be called
    bar() // A function on the same level can be called
    // A function nested in the other function cannot: 
    // baz() // not found: baz
  }
  // Functions on this level can be called...
  bar()
  qux()
  // ... but their nested functions cannot: 
  // baz() // not found: baz
  // corge() // not found: corge
}
```

P.S. we used curly braces to show the scopes more clearly, but you do not need them in Scala 3. 

Another example of where nested methods are especially useful is when you create a chain of calls to higher order 
functions and use nested methods to give their arguments meaningful names. 
Consider the example where we count the number of kittens who are either white or ginger. 

```scala 3
enum Color:
  case Black
  case White
  case Ginger

// We model that any cat has a color and its age (in years)
class Cat(val color: Color, val age: Int)

val bagOfCats = Set(Cat(Color.Black, 0), Cat(Color.White, 1), Cat(Color.Ginger, 3))

// Count the number of white or ginger kittens (cats who are not older than 1 year old) 
val numberOfWhiteOrGingerKittens =
  def isWhiteOrGinger(cat: Cat): Boolean = cat.color == Color.White || cat.color == Color.Ginger
  def isKitten(cat: Cat): Boolean = cat.age <= 1
  bagOfCats.filter(isWhiteOrGinger).count(isKitten)
```

We could have written the latter function as shown below, but anyone can agree that it is less readable: 

```scala 3
val numberOfWhiteOrGingerKittens =
  bagOfCats
    .filter(cat => cat.color == Color.White || cat.color == Color.Ginger)
    .count(cat => cat.age <= 1)
```