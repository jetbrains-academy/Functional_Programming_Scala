import org.scalatest.matchers.should.Matchers
import org.scalatest.refspec.RefSpec

class FunctionsAndMethodsSpec extends RefSpec with Matchers {
  // Import the functions and classes from the exercise file
  import FunctionsAndMethods._

  def `multiply1 should return the product of two integers`(): Unit = {
    multiply1(3, 4) shouldEqual 12
    multiply1(5, 6) shouldEqual 30
  }

  def `multiply2" should "return the product of two integers`(): Unit = {
    multiply2(3, 4) shouldEqual 12
    multiply2(5, 6) shouldEqual 30
  }

  def `Multiplier" should "return the product of two integers when calling the multiply method`(): Unit = {
    val multiplier = new Multiplier
    multiplier.multiply(3, 4) shouldEqual 12
    multiplier.multiply(5, 6) shouldEqual 30
  }

  def `MultiplierWithOffset" should "return the product of two integers plus the offset when calling the multiply method`(): Unit = {
    val multiplierWithOffset3 = new MultiplierWithOffset(3)
    multiplierWithOffset3.multiply(3, 4) shouldEqual 15 // (3 * 4) + 3
    multiplierWithOffset3.multiply(5, 6) shouldEqual 33 // (5 * 6) + 3

    val multiplierWithOffset5 = new MultiplierWithOffset(5)
    multiplierWithOffset5.multiply(3, 4) shouldEqual 17 // (3 * 4) + 5
    multiplierWithOffset5.multiply(5, 6) shouldEqual 35 // (5 * 6) + 5
  }
}
