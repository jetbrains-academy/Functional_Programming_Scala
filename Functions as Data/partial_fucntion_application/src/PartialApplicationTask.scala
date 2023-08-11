object PartialApplicationTask {
  def filterList[A](f: A => Boolean, xs: List[A]) =
    xs.filter(f)

  @main
  def main(): Unit = {
    val numbers1 = List()
    val numbers2 = List(-1,0,1)
    val numbers3 = List(1,2,3,4,5,6)
    val evenElements = filterList({ (x: Int) => x % 2 == 0 }, _: List[Int])
    println(evenElements(numbers1))
    println(evenElements(numbers2))
    println(evenElements(numbers3))

    List(numbers1, numbers2, numbers3)
      .map(evenElements)
      .foreach(println)
  }
}