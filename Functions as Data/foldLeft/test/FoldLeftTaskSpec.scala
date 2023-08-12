import org.scalatest.funsuite.AnyFunSuite
import FoldLeftTask._

class FoldLeftTaskSpec extends AnyFunSuite {
  val numbers0 = List()
  val numbers1 = List(1, 2, 3, 4)
  val numbers2 = List(1, 3, 4)
  val numbers3 = List(1, 3, 0, 4, 5, 2)
  println(computeAverage(List(1, 2, 3, 4)))
  println(computeAverage(List(1, 3, 4)))
  println(maximum(numbers3))
  println(reverse(numbers3))

  test("`computeAverage` should compute the average of the list of numbers") {
    assert(computeAverage(numbers0) == 0)
    assert(computeAverage(numbers1) == 10.0 / 4)
    assert(computeAverage(numbers2) == 8.0 / 3)
    assert(computeAverage(numbers3) == 15.0 / 6)
  }

  test("`maximum` should find the maximum of the list of numbers") {
    assert(maximum(numbers0) == Int.MinValue)
    assert(maximum(numbers1) == 4)
    assert(maximum(numbers2) == 4)
    assert(maximum(numbers3) == 5)
  }

  test("`reverse` should reveres the list") {
    assert(reverse(numbers0) == List.empty)
    assert(reverse(numbers1) == List(4,3,2,1))
    assert(reverse(numbers2) == List(4,3,1))
    assert(reverse(numbers3) == List(2,5,4,0,3,1))
  }
}
