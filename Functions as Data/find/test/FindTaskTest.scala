import org.scalatest.funsuite.AnyFunSuite
import FindTask._

class FindTaskSpec extends AnyFunSuite {
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

  test("findWhiteAndFluffyCat should return a cat which is white and fluffy") {
    val whiteAndFluffyCat = findWhiteAndFluffyCat(cats)
    whiteAndFluffyCat match {
      case Some(cat) => assert(Set(cat4, cat8).contains(cat))
      case None => assert(false)
    }
  }

  test("findCalicoAndPersian should return None") {
    assert(findCalicoAndPersian(cats) == None)
  }
}
