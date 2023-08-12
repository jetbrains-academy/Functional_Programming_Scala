object FoldLeftTask {
  def computeAverage(numbers: List[Int]): Double =
    val sum = numbers.foldLeft(0){ (acc, n) => acc + n }
    if (numbers.length <= 0) 0 else sum.toDouble/numbers.length

  def maximum(numbers: List[Int]) =
    numbers.foldLeft(Int.MinValue){ (acc, elem) => if (elem > acc) elem else acc }

  def reverse[A](numbers: List[A]) =
    numbers.foldLeft(List.empty[A]){ (acc, elem) => elem :: acc }

  @main
  def main() = {
    val numbers1 = List(1,2,3,4)
    val numbers2 = List(1,3,4)
    val numbers3 = List(1,3,0,4,5,2)
    println(computeAverage(List(1,2,3,4)))
    println(computeAverage(List(1,3,4)))
    println(maximum(numbers3))
    println(reverse(numbers3))
  }
}