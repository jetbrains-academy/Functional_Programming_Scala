import org.scalatest.funsuite.AnyFunSuite
import Task._
import Database._

class TaskSpec extends AnyFunSuite {
  def runTest(expectedCatId: Int, additionalMsg: String, findCat: Seq[CatId] => Option[Cat]): Unit =
    val stream = new java.io.ByteArrayOutputStream()

    def logMessage(id: CatId) =
      s"Cat conversion: $id\nValidation of fur characteristics: ${catDatabase(id - 1).name}"

    Console.withOut(stream) {
      val cat = findCat(identifiers)

      assert(cat === Some(catDatabase(expectedCatId - 1)))

      val expected = (1 to expectedCatId).map(id => logMessage(id)).mkString("\n").concat(additionalMsg).trim
      val actual = stream.toString().replaceAll("\r\n", "\n").trim

      assert(actual == expected)
    }

  test("Imperative Find First Valid Cat returns the valid cat and doesn't traverse the whole collection") {
    runTest(7, "", imperativeFindFirstValidCat)
  }

  test("Functional Find First Valid Cat returns the valid cat and doesn't traverse the whole collection") {
    val expectedCatId = 7
    runTest(expectedCatId, s"\nCat conversion: $expectedCatId", functionalFindFirstValidCat)
  }

  test("CollectFirst Find First Valid Cat returns the valid cat and doesn't traverse the whole collection") {
    val expectedCatId = 7
    runTest(expectedCatId, s"\nCat conversion: $expectedCatId", collectFirstFindFirstValidCat)
  }
}
