import Database._
import Breed._
import FurCharacteristic._
import Pattern._
import TabbySubtype._
import ShadingSubtype._
import BicolorSubtype._
import TricolorSubtype._

object Task:
  private val breedCharacteristics: Map[Breed, Set[FurCharacteristic]] = Map(
    Siamese -> Set(ShortHaired, SleekHaired),
    Persian -> Set(LongHaired, Fluffy, DoubleCoated),
    MaineCoon -> Set(LongHaired, Fluffy, DoubleCoated),
    Ragdoll -> Set(LongHaired, Fluffy, DoubleCoated),
    Bengal -> Set(ShortHaired),
    Abyssinian -> Set(ShortHaired, SleekHaired),
    Birman -> Set(LongHaired, Fluffy, DoubleCoated),
    OrientalShorthair -> Set(ShortHaired, SleekHaired),
    Sphynx -> Set(WireHaired, Plush),
    DevonRex -> Set(ShortHaired, Plush, WireHaired),
    ScottishFold -> Set(ShortHaired, LongHaired, DoubleCoated),
    Metis -> Set(LongHaired, ShortHaired, Fluffy, Plush, SleekHaired, WireHaired, DoubleCoated) // Assuming Metis can have any characteristics
  )

  /**
   * Implement the validation: the characteristics of the cat's fur should feed their breed.
   *
   * @param cat cat data
   * @return true if the user data is valid, false otherwise
   */
  def furCharacteristicValidation(cat: Cat): Boolean =
    print(s"Validation of fur characteristics: ${cat.name}\n")
    val validCharacteristics = breedCharacteristics(cat.breed)
    cat.furCharacteristics.forall(validCharacteristics.contains)

  /**
   * This function takes into account that some IDs can be left out from the database
   * and only selects a cat who has not been adopted.
   */
  def nonAdoptedCatConversion(catId: CatId): Option[Cat] =
    print(s"Non-adopted cat conversion: $catId\n")
    val status = adoptionStatusDatabase.find(cat => cat.id == catId && !cat.adopted)
    status.flatMap(status => catDatabase.find(_.name == status.name))

  /**
   * Implement a custom unapply method that uses nonAdoptedCatConversion and firCharacteristicValidation.
   */
  object ValidCat:
    def unapply(catId: CatId): Option[Cat] =
      nonAdoptedCatConversion(catId).find(furCharacteristicValidation)


  /**
   * Use the custom `unapply` method.
   * @param catIds the sequence of all cat identifiers
   * @return `Some` of the first valid cat data or `None` if no valid cat data is found
   */
  def unapplyFindFirstValidCat(catIds: Seq[CatId]): Option[Cat] =
    catIds.collectFirst {
      case ValidCat(cat) => cat
    }

  val breedPatterns: Map[Breed, Set[Pattern]] = Map(
    Siamese -> Set(Pattern.Pointed),
    Persian -> Set(Pattern.SolidColor),
    MaineCoon -> Set(Pattern.Tabby(Classic), Pattern.Tabby(Mackerel), Pattern.Tabby(Patched)),
    Ragdoll -> Set(Pattern.Pointed, Pattern.Bicolor(Van)),
    Bengal -> Set(Pattern.Spots),
    Abyssinian -> Set(Pattern.Tabby(Ticked)),
    Birman -> Set(Pattern.Pointed, Pattern.SolidColor),
    OrientalShorthair -> Set(Pattern.SolidColor, Pattern.Pointed),
    Sphynx -> Set(Pattern.SolidColor),
    DevonRex -> Set(Pattern.SolidColor, Pattern.Tabby(Classic)),
    ScottishFold -> Set(Pattern.SolidColor, Pattern.Tabby(Classic), Pattern.Tabby(Mackerel)),
    Metis -> Set(Tabby(Classic), Tabby(Ticked), Tabby(Mackerel), Tabby(Spotted), Tabby(Patched), Pointed,
                 Shading(Chinchilla), Shading(Shaded), Shading(Smoke), SolidColor,
                 Bicolor(Tuxedo), Bicolor(Van), Tricolor(Calico), Tricolor(Tortie), Spots)
  )

  def validatePattern(cat: Cat): Boolean =
    print(s"Validation of fur pattern: ${cat.name}\n")
    breedPatterns(cat.breed).contains(cat.pattern)

  /**
   * Now use Deconstruct idiom for the validation of the pattern of the cat
   * @tparam From The type we initially operate on
   * @tparam To   The type of the data we want to retrieve if it's valid
   */
  trait Deconstruct[From, To]:
    def convert(from: From): Option[To]

    def validate(to: To): Boolean

    def unapply(from: From): Option[To] = convert(from).find(validate)

  /**
   * Now validate that a coat pattern corresponds to the cat's breed by extending `Deconstruct`.
   */
  object ValidPattern extends Deconstruct[CatId, Cat]:
    override def convert(catId: CatId): Option[Cat] =
      nonAdoptedCatConversion(catId)

    override def validate(cat: Cat): Boolean =
      validatePattern(cat)

  /**
   * Find the first cat with a valid pattern using the Deconstruct idiom.
   * @param catIds the sequence of all cat identifiers
   * @return `Some` of the first valid cat data or `None` if no valid cat data is found
   */
  def findFirstCatWithValidPattern(catIds: Seq[CatId]): Option[Cat] =
    catIds.collectFirst {
      case ValidPattern(cat) => cat
    }