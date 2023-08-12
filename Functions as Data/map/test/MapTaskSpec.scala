import org.scalatest.funsuite.AnyFunSuite
import MapTask._

class MapTaskSpec extends AnyFunSuite {

  test("furCharacteristicsDescription should return a list of string representations of fur characteristics of a cat") {
    assert(furCharacteristicsDescription(cat1) == Set("SleekHaired"))
    assert(furCharacteristicsDescription(cat3) == Set("Fluffy", "Plush"))
    assert(furCharacteristicsDescription(cat9) == Set())
  }
}
