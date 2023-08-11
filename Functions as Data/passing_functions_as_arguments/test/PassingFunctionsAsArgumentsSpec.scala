import org.scalatest.matchers.should.Matchers
import org.scalatest.refspec.RefSpec

class PassingFunctionsAsArgumentsSpec extends RefSpec with Matchers {
  // Import the functions and classes from the exercise file
  import PassingFunctionsAsArguments._

  def `"bagOfBlackCats" should "only contain black cats"`(): Unit = {
    assert(bagOfBlackCats.forall(x => x.color == Color.Black))
  }

  def `"bagOfWhiteCats" should "only contain white cats"`(): Unit = {
    assert(bagOfWhiteCats.forall(x => x.color == Color.White))
  }
}
