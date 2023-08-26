import org.scalatest.funsuite.AnyFunSuite
import FunctionsReturningFunctionsTask._

class FunctionsReturningFunctionsTaskSpec extends AnyFunSuite {
  test("It should be possible to curry `filterList`") {
    val numbers = List(1,2,3,4,5,6)
    def isEven(x: Int) = x % 2 == 0
    val evenElements = filterList[Int](isEven)
    assert(evenElements(numbers) == numbers.filter(isEven))

    val booleans = List(true, true, true, false, true, false, true)
    def isTrue(x: Boolean) = x
    val trueValues = filterList[Boolean](isTrue)
    def isFalse(x: Boolean) = !x
    val falseValues = filterList[Boolean](isFalse)
    assert(trueValues(booleans) == booleans.filter(isTrue))
    assert(falseValues(booleans) == booleans.filter(isFalse))
  }
}
