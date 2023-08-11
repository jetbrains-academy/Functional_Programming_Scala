import org.scalatest.matchers.should.Matchers
import org.scalatest.refspec.RefSpec

class FilterTaskSpec extends RefSpec with Matchers {
  // Import the functions and classes from the exercise file
  import FilterTask._

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

  def `"isCatCalico" should "return true on calico cats"`(): Unit = {
    cats.filter(isCatCalico) shouldEqual Set(cat5, cat9)
  }

  def `"isCatAbyssinian" should "return true on Abyssinian cats"`(): Unit = {
    cats.filter(isCatAbyssinian) shouldEqual Set(cat1, cat7)
  }

  def `"isCatFluffy" should "return true on Fluffy cats"`(): Unit = {
    cats.filter(isCatFluffy) shouldEqual Set(cat3, cat4, cat5, cat8)
  }

  def `"filterCats" should "return cats which are calico, fluffy or Abyssinian"`(): Unit = {
    filterCats(cats) shouldEqual Set(cat1, cat3, cat4, cat5, cat7, cat8, cat9)
  }
}
