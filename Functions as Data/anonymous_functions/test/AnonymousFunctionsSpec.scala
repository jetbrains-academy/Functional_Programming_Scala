import org.scalatest.funsuite.AnyFunSuite
import AnonymousFunctions._

class AnonymousFunctionsSpec extends AnyFunSuite {
  test("multiplyAndOffsetList should multiply and offset every element of the list when calling the multiplyAndOffsetList function") {
    assert(multiplyAndOffsetList(3, 4, List(1, 2, 3, 4)) == List(7, 10, 13, 16))
  }

  test("multiplyAndOffsetList should return empty list when applied to the empty list") {
    assert(multiplyAndOffsetList(3, 4, List()) == List())
  }
}