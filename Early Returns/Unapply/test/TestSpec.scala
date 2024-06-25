import org.scalatest.funsuite.AnyFunSuite
import Task._
import Database._

class TaskSpec extends AnyFunSuite {
  def runTest(expectedCatId: Int, validationMsg: String, findCat: Seq[CatId] => Option[Cat]): Unit =
    val stream = new java.io.ByteArrayOutputStream()

    def logMessage(id: CatId) =
      val validationMessage = if !adoptionStatusDatabase(id-1).adopted then s"\n$validationMsg: ${catDatabase(id - 1).name}" else ""
      s"Non-adopted cat conversion: $id$validationMessage"

    Console.withOut(stream) {
      val cat = findCat(identifiers)

      assert(cat === Some(catDatabase(expectedCatId - 1)))

      val expected = (1 to expectedCatId).map(id => logMessage(id)).mkString("\n").trim
      val actual = stream.toString().replaceAll("\r\n", "\n").trim

      assert(actual == expected)
    }

  test("Unapply Find First Valid Cat returns the valid cat and doesn't traverse the whole collection. Validation is only run when a cat is in the database and has not been adopted") {
    runTest(13, "Validation of fur characteristics", unapplyFindFirstValidCat)
  }

  test("Find First Cat With Valid Pattern returns the valid cat and doesn't traverse the whole collection. Validation is only run when a cat is in the database and has not been adopted") {
    runTest(4, "Validation of fur pattern", findFirstCatWithValidPattern)
  }
}
