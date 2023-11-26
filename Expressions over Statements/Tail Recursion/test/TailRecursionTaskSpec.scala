import org.scalatest.funsuite.AnyFunSuite
import TailRecursionTask._

class TailRecursionTaskSpec extends AnyFunSuite {
  test("List should be reversed with reverseList") {
    assert(reverseList(List.empty) == List.empty)
    assert(reverseList(List(0,1,2,3,4)) == List(4,3,2,1,0))
  }

  test("sumOfDigits should compute the sum of digits correctly") {
    assert(sumOfDigits(-10) == 0)
    assert(sumOfDigits(0) == 0)
    assert(sumOfDigits(1234567890) == 45)
  }
}