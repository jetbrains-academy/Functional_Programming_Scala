class Task {
  lazy val lazyComputedValue: Int = {
    println("Computing...")
    // Some heavy computation
    Thread.sleep(1000)
    42  // Computed value
  }

  val timeOffset = System.currentTimeMillis

  def now = System.currentTimeMillis - timeOffset

  println(s"lazyComputedValue declared at $now.")
  // The value is computed and printed only when it's accessed for the first time.
  println(s"Accessing: $lazyComputedValue")
  println(s"time now is $now") // takes more than 1000 milliseconds
  // This time it's not computed but fetched from memory.
  println(s"Accessing again: $lazyComputedValue")
  println(s"time now is $now") // should take only a few milliseconds at most

}