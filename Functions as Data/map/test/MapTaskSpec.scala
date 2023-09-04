import org.scalatest.funsuite.AnyFunSuite
import MapTask._

class MapTaskSpec extends AnyFunSuite {
  def checkOneCat(cat: Cat): Unit = {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      outputFurCharacteristics(cat)

      assert(stream.toString().trim == furCharacteristicsDescription(cat).mkString("\n").trim)
    }
  }
  test("") {
    cats.foreach(checkOneCat)
  }
}
