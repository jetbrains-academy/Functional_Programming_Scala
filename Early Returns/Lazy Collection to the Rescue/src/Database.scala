import Breed._
import Color._
import FurCharacteristic._
import Pattern._
import TabbySubtype._
import ShadingSubtype._
import BicolorSubtype._
import TricolorSubtype._

object Database:
  type CatId = Int

  /**
   * @param id a unique cat identifier
   * @param name a cat's name
   * @param adopted a flag that is true if the cat has been adopted
   */
  case class CatAdoptionStatus(id: CatId, name: String, adopted: Boolean)

  val identifiers: Seq[CatId] = 1 to 30

  /**
   * This database "table" tracks whether a cat has already been adopted.
   */
  val adoptionStatusDatabase: Seq[CatAdoptionStatus] = Seq(
    CatAdoptionStatus(1, "Luna", true),
    CatAdoptionStatus(2, "Max", false),
    CatAdoptionStatus(3, "Charlie", true),
    CatAdoptionStatus(4, "Daisy", false),
    CatAdoptionStatus(5, "Simba", true),
    CatAdoptionStatus(6, "Oliver", false),
    CatAdoptionStatus(7, "Molly", true),
    CatAdoptionStatus(8, "Lucy", true),
    CatAdoptionStatus(9, "Buddy", false),
    CatAdoptionStatus(10, "Rocky", true),
    CatAdoptionStatus(11, "Jack", false),
    CatAdoptionStatus(12, "Sadie", true),
    CatAdoptionStatus(13, "Ginger", false),
    CatAdoptionStatus(14, "Leo", true),
    CatAdoptionStatus(15, "Misty", false),
    CatAdoptionStatus(16, "Rex", true),
    CatAdoptionStatus(17, "Bella", true),
    CatAdoptionStatus(18, "Tiger", true),
    CatAdoptionStatus(19, "Zara", false),
    CatAdoptionStatus(20, "Sophie", true),
    CatAdoptionStatus(21, "Ollie", false),
    CatAdoptionStatus(22, "Pixie", true),
    CatAdoptionStatus(23, "Fuzz", false),
    CatAdoptionStatus(24, "Scotty", true),
    CatAdoptionStatus(25, "Mixie", true),
    CatAdoptionStatus(26, "Cleo", true),
    CatAdoptionStatus(27, "Milo", false),
    CatAdoptionStatus(28, "Nala", true),
    CatAdoptionStatus(29, "Loki", false),
    CatAdoptionStatus(30, "Shadow", true)
  )

  /**
   * This database "table" contains the basic information about each cat.
   */
  val catDatabase: Seq[Cat] = Seq(
    Cat("Luna", Siamese, Blue, Pattern.SolidColor, Set(Fluffy)), // Invalid
    Cat("Max", Persian, Black, Pattern.Tabby(Mackerel), Set(ShortHaired)), // Invalid
    Cat("Charlie", MaineCoon, Orange, Pattern.SolidColor, Set(SleekHaired)), // Invalid
    Cat("Daisy", Ragdoll, Cream, Pattern.Bicolor(Van), Set(ShortHaired)), // Invalid
    Cat("Simba", Bengal, Blue, Pattern.SolidColor, Set(LongHaired)), // Invalid
    Cat("Oliver", ScottishFold, Cinnamon, Pattern.Spots, Set(WireHaired)), // Invalid
    Cat("Molly", Persian, White, Pattern.Shading(Shaded), Set(Fluffy, DoubleCoated)), // Valid
    Cat("Lucy", Metis, Blue, Pattern.Pointed, Set(SleekHaired, Fluffy)), // Metis can be anything
    Cat("Buddy", Siamese, Cream, Pattern.Tabby(Mackerel), Set(LongHaired)), // Invalid
    Cat("Rocky", MaineCoon, Black, Pattern.Tricolor(Tortie), Set(ShortHaired)), // Invalid
    Cat("Jack", Bengal, Chocolate, Pattern.Tabby(Spotted), Set(ShortHaired, Plush)), // Invalid
    Cat("Sadie", Birman, White, Pattern.Bicolor(Van), Set(LongHaired, Fluffy)), // Valid
    Cat("Ginger", Abyssinian, Orange, Pattern.Tabby(Ticked), Set(ShortHaired, SleekHaired)), // Valid
    Cat("Leo", Siamese, Cream, Pattern.SolidColor, Set(ShortHaired, SleekHaired)), // Valid
    Cat("Misty", Persian, White, Pattern.SolidColor, Set(LongHaired, Fluffy)), // Valid
    Cat("Rex", MaineCoon, Black, Pattern.Tabby(Classic), Set(LongHaired, DoubleCoated)), // Valid
    Cat("Bella", Ragdoll, Blue, Pattern.Pointed, Set(Fluffy)), // Valid
    Cat("Tiger", Bengal, Orange, Pattern.Spots, Set(ShortHaired)), // Valid
    Cat("Zara", Abyssinian, Cinnamon, Pattern.Tabby(Ticked), Set(ShortHaired)), // Valid
    Cat("Sophie", Birman, Lilac, Pattern.Pointed, Set(LongHaired, Fluffy)), // Valid
    Cat("Ollie", OrientalShorthair, Lavender, Pattern.SolidColor, Set(SleekHaired)), // Valid
    Cat("Pixie", Sphynx, Lilac, Pattern.SolidColor, Set(WireHaired)), // Valid
    Cat("Fuzz", DevonRex, Cream, Pattern.SolidColor, Set(ShortHaired, WireHaired)), // Valid
    Cat("Scotty", ScottishFold, White, Pattern.Bicolor(Tuxedo), Set(ShortHaired)), // Valid
    Cat("Mixie", Metis, Chocolate, Pattern.Tricolor(Calico), Set(DoubleCoated)), // Metis can be anything
    Cat("Cleo", Abyssinian, Fawn, Pattern.Tricolor(Tortie), Set(Fluffy)), // Invalid
    Cat("Milo", Birman, Lavender, Pattern.SolidColor, Set(SleekHaired)), // Invalid
    Cat("Nala", OrientalShorthair, Black, Pattern.Shading(Chinchilla), Set(Fluffy)), // Invalid
    Cat("Loki", Sphynx, White, Pattern.Shading(Shaded), Set(LongHaired)), // Invalid
    Cat("Shadow", DevonRex, Lilac, Pattern.SolidColor, Set(LongHaired)) // Invalid
  )