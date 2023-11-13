import scala.io.StdIn

object Task:
  @main
  def main(): Unit =
    def g(x: Int): Int =
      val y = StdIn.readInt()
      x * y

    println(g(21)) // Input: 1 => printed 21
    println(g(21)) // Input: 3 => printed 63

