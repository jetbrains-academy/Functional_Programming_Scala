import org.scalatest.Matchers
import org.scalatest.refspec.RefSpec

class AnonymousFunctionsSpec extends RefSpec with Matchers {
  // Import the functions and classes from the exercise file
  import AnonymousFunctions._

  def `"multiplyAndOffsetList" should "multiply and offset every element of the list when calling the multiplyAndOffsetList function"`(): Unit = {
    multiplyAndOffsetList(3, 4, List(1, 2, 3, 4)) shouldEqual List(7, 10, 13, 16)
  }
  def `"multiplyAndOffsetList" should "return empty list when applied to the empty list"`(): Unit = {
    multiplyAndOffsetList(13, 42, List()) shouldEqual List()
  }
}
