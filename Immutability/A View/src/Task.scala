class Task {
  val numbers = (1 to 100).toList

  // Without using view
  val firstEvenSquareGreaterThan100_NoView = numbers
    .map(n => n * n)
    .filter(n => n > 100 && n % 2 == 0)
    .head
  println(firstEvenSquareGreaterThan100_NoView)


  // Using view
  val firstEvenSquareGreaterThan100_View = numbers.view
    .map(n => {
      println(s"Square of $n being calculated.")  // To observe the lazy evaluation
      n * n
    })
    .filter(n => n > 100 && n % 2 == 0)
    .head
  println(firstEvenSquareGreaterThan100_View)
}