import org.scalatest.funsuite.AnyFunSuite
import FunctionsAndMethods._

class FunctionsAndMethodsSpec extends AnyFunSuite {

  test("multiplyAsFunction should return the product of two integers") {
    assert(multiplyAsFunction(3, 4) == 12)
    assert(multiplyAsFunction(5, 6) == 30)
  }

  test("multiplyAsValue should return the product of two integers") {
    assert(multiplyAsValue(3, 4) == 12)
    assert(multiplyAsValue(5, 6) == 30)
  }

  test("Multiplier should return the product of two integers when calling the multiply method") {
    val multiplier = new Multiplier
    assert(multiplier.multiply(3, 4) == 12)
    assert(multiplier.multiply(5, 6) == 30)
  }

  test("MultiplierWithOffset should return the product of two integers plus the offset when calling the multiply method") {
    val multiplierWithOffset3 = new MultiplierWithOffset(3)
    assert(multiplierWithOffset3.multiply(3, 4) == 15)
    assert(multiplierWithOffset3.multiply(5, 6) == 33)

    val multiplierWithOffset5 = new MultiplierWithOffset(5)
    assert(multiplierWithOffset5.multiply(3, 4) == 17)
    assert(multiplierWithOffset5.multiply(5, 6) == 35)
  }
}