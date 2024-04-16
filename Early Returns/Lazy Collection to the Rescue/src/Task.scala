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
   * Implement the search by making the collection lazy.
   *
   * @param catIds the sequence of all user identifiers
   * @return `Some` of the first valid cat data or `None` if no valid cat data is found
   */
  def findFirstValidCat(catIds: Seq[CatId]): Option[Cat] =
    catIds
      .iterator
      .map(nonAdoptedCatConversion)
      .find(_.exists(furCharacteristicValidation))
      .flatten