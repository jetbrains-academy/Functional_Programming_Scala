class Task {
  lazy val fib: LazyList[BigInt] =
    BigInt(0) #::
      BigInt(1) #::
      fib.zip(fib.tail).map { case (a, b) => a + b }
  
  // Fetch and print the first 10 Fibonacci numbers
  fib.take(10).foreach(println)

}