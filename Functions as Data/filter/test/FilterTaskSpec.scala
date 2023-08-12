import org.scalatest.funsuite.AnyFunSuite
import FilterTask._

class FilterTaskSpec extends AnyFunSuite {
  val cat1 = Cat("Pumpkin", Breed.Abyssinian, Color.Orange, Pattern.SolidColor, Set(FurCharacteristic.SleekHaired))
  val cat2 = Cat("Cheetah", Breed.Bengal, Color.Cream, Pattern.Spots, Set(FurCharacteristic.ShortHaired))
  val cat3 = Cat("Pepper", Breed.Metis, Color.Black, Pattern.Bicolor(BicolorSubtype.Tuxedo), Set(FurCharacteristic.Fluffy, FurCharacteristic.Plush))
  val cat4 = Cat("Jet", Breed.Metis, Color.White, Pattern.Tricolor(TricolorSubtype.Tortie), Set(FurCharacteristic.Fluffy))
  val cat5 = Cat("Cinnamon", Breed.Metis, Color.Cinnamon, Pattern.Tricolor(TricolorSubtype.Calico), Set(FurCharacteristic.Fluffy))
  val cat6 = Cat("Pearl", Breed.DevonRex, Color.Cream, Pattern.Bicolor(BicolorSubtype.Van), Set())
  val cat7 = Cat("Dusty", Breed.Abyssinian, Color.Lilac, Pattern.SolidColor, Set(FurCharacteristic.SleekHaired))
  val cat8 = Cat("Princess", Breed.Ragdoll, Color.White, Pattern.SolidColor, Set(FurCharacteristic.LongHaired, FurCharacteristic.Fluffy))
  val cat9 = Cat("Cairo", Breed.Sphynx, Color.White, Pattern.Tricolor(TricolorSubtype.Calico), Set())
  val cat10 = Cat("Stardust", Breed.MaineCoon, Color.Black, Pattern.Bicolor(BicolorSubtype.Tuxedo), Set(FurCharacteristic.LongHaired))

  val cats = Set(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10)

  test("isCatCalico should return true on calico cats") {
    assert(cats.filter(isCatCalico) == Set(cat5, cat9))
  }

  test("isCatAbyssinian should return true on Abyssinian cats") {
    assert(cats.filter(isCatAbyssinian) == Set(cat1, cat7))
  }

  test("isCatFluffy should return true on Fluffy cats") {
    assert(cats.filter(isCatFluffy) == Set(cat3, cat4, cat5, cat8))
  }

  test("filterCats should return cats which are calico, fluffy or Abyssinian") {
    assert(filterCats(cats) == Set(cat1, cat3, cat4, cat5, cat7, cat8, cat9))
  }
}
