import org.scalatest.funsuite.AnyFunSuite
import PartialApplicationTask._

class PartialApplicationTaskSpec extends AnyFunSuite {
  test("It should be possible to partially apply `filterList`") {
    val numbers = List(1,2,3,4,5,6)
    def isEven(x: Int) = x % 2 == 0
    val evenElements = filterList[Int](isEven, _: List[Int])
    assert(evenElements(numbers) == numbers.filter(isEven))

    val booleans = List(true, true, true, false, true, false, true)
    def isTrue(x: Boolean) = x
    val trueValues = filterList[Boolean](isTrue, _: List[Boolean])
    def isFalse(x: Boolean) = !x
    val falseValues = filterList[Boolean](isFalse, _: List[Boolean])
    assert(trueValues(booleans) == booleans.filter(isTrue))
    assert(falseValues(booleans) == booleans.filter(isFalse))
  }
}
