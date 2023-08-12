import org.scalatest.funsuite.AnyFunSuite
import FlatMapTask._

class FlatMapTaskSpec extends AnyFunSuite {
  test("collectFurCharacteristics should aggregate all fur characteristics of all cats in a set") {
    assert(collectFurCharacteristics(cats) == Set(FurCharacteristic.Fluffy, FurCharacteristic.SleekHaired, FurCharacteristic.LongHaired, FurCharacteristic.ShortHaired, FurCharacteristic.Plush))
  }
}