import org.scalatest.funsuite.AnyFunSuite
import PartialFunctionExample._

class TotalAndPartialFunctionsTaskSpec extends AnyFunSuite {
  test("`division` should not be defined when the second argument is zero") {
    assert(!division.isDefinedAt((0,0)))
    assert(!division.isDefinedAt((1,0)))
    assert(!division.isDefinedAt((42,0)))
    assert(!division.isDefinedAt((777,0)))
  }

  test("`division` should be defined when the second argument is not zero") {
    assert(division.isDefinedAt((0, 1)))
    assert(division.isDefinedAt((1, 2)))
    assert(division.isDefinedAt((42, 3)))
    assert(division.isDefinedAt((777, 4)))
  }
}
