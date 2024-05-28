import scala.io.StdIn.readLine

object Task :

  def readNumbers(x: String, y: String): Either[String, (Double, Double)] =
    (x.toDoubleOption, y.toDoubleOption) match
      case (Some(x), Some(y)) => Right (x, y)
      case (None, Some(y)) => Left("First string is not a number")
      case (Some(x), None) => Left("Second string is not a number")
      case (None, None) => Left("Both strings are not numbers")

  def safeDiv(x: Double, y: Double): Either[String, Double] =
    if (y == 0) Left("Division by zero")
    else Right(x / y)

//  def safeDiv(x: Double, y: Double): Either[Throwable, Double] =
//    if (y == 0) Left(new IllegalArgumentException("Division by zero"))
//    else Right(x / y)

  @main
  def main() =
    val x = readLine()
    val y = readLine()
    print(readNumbers(x, y).flatMap(safeDiv))


