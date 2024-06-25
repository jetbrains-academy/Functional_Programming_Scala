import Database._
import Breed._
import FurCharacteristic._

object Task:
  /**
   * Implement the conversion method: find a cat's name by its ID in the adoptionStatusDatabase,
   * and then fetch the cat data from the catsDatabase.
   *
   * @param catId the identifier of a cat for whom we want to retrieve the data
   * @return the cat data
   */
  def catConversion(catId: CatId): Cat =
    print(s"Cat conversion: $catId\n")
    val status = adoptionStatusDatabase.find(_.id == catId).get
    catDatabase.find(_.name == status.name).get

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
   * Imperative approach that uses un-idiomatic `return`.
   *
   * @param catIds the sequence of all cat identifiers
   * @return `Some` of the first valid user data or `None` if no valid user data is found
   */
  def imperativeFindFirstValidCat(catIds: Seq[CatId]): Option[Cat] =
    for catId <- catIds do
      val catData = catConversion(catId)
      if (furCharacteristicValidation(catData)) return Some(catData)
    None

  /**
   * Implement the naive functional approach.
   *
   * @param catIds the sequence of all cat identifiers
   * @return `Some` of the first valid user data or `None` if no valid cat data is found
   */
  def functionalFindFirstValidCat(catIds: Seq[CatId]): Option[Cat] =
    catIds
      .find(catId => furCharacteristicValidation(catConversion(catId)))
      .map(catConversion)

  /**
   * Use `collectFirst` here.
   *
   * @param catIds the sequence of all cat identifiers
   * @return `Some` of the first valid cat data or `None` if no valid cat data is found
   */
  def collectFirstFindFirstValidCat(userIds: Seq[CatId]): Option[Cat] =
    userIds.collectFirst {
      case catId if furCharacteristicValidation(catConversion(catId)) => catConversion(catId)
    }