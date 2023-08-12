import org.scalatest.funsuite.AnyFunSuite
import PassingFunctionsAsArguments._

class PassingFunctionsAsArgumentsSpec extends AnyFunSuite {
  test("bagOfWhiteOrGingerCats should only contain white cats") {
    assert(bagOfWhiteOrGingerCats.forall(x => x.color == Color.White || x.color == Color.Ginger))
  }
}