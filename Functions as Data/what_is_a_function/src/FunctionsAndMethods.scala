object FunctionsAndMethods {

  // Exercise: Define a function named `multiply1` that takes two integers and returns their product.def multiply1(x: Int, y: Int): Int = x * y
  // Use the `def` keyword.
  // [start: multiply1]
  def multiply1(x: Int, y: Int): Int = x * y
  // [end]

  // Exercise: Define a function named `multiply2` that takes two integers and returns their product.
  // Use the `val` keyword.
  // [start: multiply2]
  val multiply2: (Int, Int) => Int = (x, y) => x * y
  // [end]

  // Exercise: Define a class named `Multiplier` with a method named `multiply` that takes two integers and returns their product.
  // [start: Multiplier]
  class Multiplier {
    def multiply(x: Int, y: Int): Int = x * y
  }
  // [end]

  // Exercise: Create a subclass of `Multiplier` named `MultiplierWithOffset` that has an internal state `offset`.
  // Override the `multiply` method to add the `offset` to the product of the two input integers.
  // [start: MultiplierWithOffset]
  class MultiplierWithOffset(offset: Int) extends Multiplier {
    override def multiply(x: Int, y: Int): Int = super.multiply(x, y) + offset
  }
  // [end]
}
