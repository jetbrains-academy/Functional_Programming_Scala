import org.scalatest.funsuite.AnyFunSuite
import MapTask._

class MapTaskSpec extends AnyFunSuite {
  def checkOneCat(cat: Cat): Unit = {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      outputFurCharacteristics(cat)

      val expected = furCharacteristicsDescription(cat).mkString("\n").trim
      val actual = stream.toString().replaceAll("\r\n", "\n").trim

      assert(actual == expected)
    }
  }
  test("") {
    cats.foreach(checkOneCat)
  }
}
