object Task:

  // We model colors as enums.
  enum Color:
    case Black
    case White
    case Ginger

  // We model a cat as a class. In this example we are interested only in color of the cat.
  class Cat(val color: Color, val age: Int)

  // We create our bag (a set) of cats. Each cat has a different color.
  val bagOfCats = Set(Cat(Color.Black, 0), Cat(Color.White, 1), Cat(Color.Ginger, 3))

  // We use the `filter` method to create a new bag of black cats.
  val numberOfWhiteOrGingerKittens =
    def isWhiteOrGinger(cat: Cat): Boolean = cat.color == Color.White || cat.color == Color.Ginger
    def isKitten(cat: Cat): Boolean = cat.age <= 1
    bagOfCats.filter(isWhiteOrGinger).count(isKitten)
//    bagOfCats.filter(cat => cat.color == Color.White || cat.color == Color.Ginger).count(cat => cat.age <= 1)

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
  @main
  def main() = {
    println(numberOfWhiteOrGingerKittens)
  }