import org.scalatest.funsuite.AnyFunSuite
import ForeachTask._

class ForeachTaskSpec extends AnyFunSuite {
  test("") {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      val catsInList = cats.toList
      outputShortInfo(catsInList)

      assert(stream.toString().trim == catsInList.map(shortInfo).mkString("\n").trim)
    }
  }
}
