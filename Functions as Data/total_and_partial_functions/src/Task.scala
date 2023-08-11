object PartialFunctionExample extends App {
  // Define a partial function to handle division by zero
  val division: PartialFunction[(Int, Int), Double] = {
    case (x, y) if y != 0 => x.toDouble / y
  }

  // Test the partial function
  val values = List((10, 2), (8, 0), (6, 3))

  values.foreach { case (x, y) =>
    if (division.isDefinedAt((x, y))) {
      println(s"Result of $x / $y = ${division((x, y))}")
    } else {
      println(s"Cannot divide $x by $y")
    }
  }
}