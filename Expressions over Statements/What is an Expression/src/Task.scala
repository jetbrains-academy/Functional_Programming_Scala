object Task:

  def printIfEven(number: Int): Unit = {
    if (number % 2 == 0)
      println("Number is even")
    else
      println("Number is odd")
  }

  def even(number: Int): String = {
    if (number % 2 == 0) "even" else "odd"
  }

  @main
  def main(): Unit = {
    val number = 42
    val result = even(12)
    println(s"The number is $result")
  }