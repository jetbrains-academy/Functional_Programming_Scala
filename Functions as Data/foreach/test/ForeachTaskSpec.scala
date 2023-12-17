import org.scalatest.funsuite.AnyFunSuite
import ForeachTask._

class ForeachTaskSpec extends AnyFunSuite {
  test("") {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      val catsInList = cats.toList
      outputShortInfo(catsInList)

      val expected = catsInList.map(shortInfo).mkString("\n").trim
      val actual = stream.toString().replaceAll("\r\n", "\n").trim

      assert(actual == expected)
    }
  }
}
