object PassingFunctionsAsArguments {
  // We model colors as enums.
  enum Color:
    case Black
    case White
    case Ginger

  // We model a cat as a class. In this example we are interested only in color of the cat.
  class Cat(val color: Color)
  
  // We create our bag (a set) of cats. Each cat has a different color.
  val bagOfCats = Set(Cat(Color.Black), Cat(Color.White), Cat(Color.Ginger))

  def isCatBlack(cat: Cat): Boolean = cat.color == Color.Black

  // We use the `filter` method to create a new bag of black cats.
  val bagOfBlackCats = bagOfCats.filter(isCatBlack)

  def isCatWhite(cat: Cat): Boolean = cat.color == Color.White

  val bagOfWhiteCats = bagOfCats.filter(isCatWhite)
}