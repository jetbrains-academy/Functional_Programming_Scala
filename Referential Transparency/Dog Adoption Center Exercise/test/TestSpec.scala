import scala.jdk.CollectionConverters.*

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}
import munit.CatsEffectSuite

import java.nio.file.{Files, Path}

enum Action:
  case View
  case Adopt(dogId: Int)
  case Surrender(dog: Dog)
  case Exit
  
def generateInputStream(actions: List[Action]): ByteArrayInputStream =
  new ByteArrayInputStream(
    actions.map {
      case Action.View => "1"
      case Action.Adopt(dogId) => s"2\n$dogId"
      case Action.Surrender(dog) => s"3\n${dog.name}\n${dog.breed}\n${dog.favoriteToy}"
      case Action.Exit => "4"
    }.mkString("\n").getBytes
  )

def cleanupDatabase(initialDB: String): Unit =
  val contents = Files.readString(Path.of(initialDB))
  Files.deleteIfExists(Dog.FilePath)
  Files.createFile(Dog.FilePath)
  Files.writeString(Dog.FilePath, contents)

case class DogNoId(name: String, breed: String, favoriteToy: String):
  override def toString: String = s"Name: $name, Breed: $breed, Favorite toy: $favoriteToy"

object DogNoId:
  implicit val defaultOrdering: Ordering[DogNoId] = Ordering.by(d => (d.name, d.breed, d.favoriteToy))
  def dropId(dog : Dog): DogNoId = DogNoId(dog.name, dog.breed, dog.favoriteToy)

def parseDatabase(path: Path): (List[Int], List[DogNoId]) =
  val lines = Files.readAllLines(path).asScala.toList
  val dogs = lines.map(Dog.fromLine)
  (dogs.map(_.id), dogs.map(DogNoId.dropId))

def checkDatabase(expectedDBPath: String): Unit =
  val (actualIds, actualDogs) = parseDatabase(Dog.FilePath)
  val (expectedIds, expectedDogs) = parseDatabase(Path.of(expectedDBPath))
  assert(actualIds.size == expectedIds.size, "The database should contain the same number of dogs as in the expected database")
  assert(actualIds.distinct.size == actualIds.size, "The database should contain unique IDs")
  assert(actualDogs.sorted == expectedDogs.sorted, "The database should contain the same dogs as in the expected database")

class TaskSpec extends CatsEffectSuite {

  def adoptionCenterTest(actions: List[Action], expectedOutput: String): Unit = {
    val initialDB = "Referential Transparency/Dog Adoption Center Exercise/test/resources/initial.csv"
    cleanupDatabase(initialDB)

    val inputStream = generateInputStream(actions)

    // Prepare an output stream to capture program output
    val outputStream = new ByteArrayOutputStream()
    val printStream = new PrintStream(outputStream)

    // Save references to the real System.in and System.out
    val realIn = System.in
    val realOut = System.out

    try {
      // Override System.in and System.out
      System.setIn(inputStream)
      System.setOut(printStream)

      Task.run.unsafeRunSync()

      checkDatabase(expectedOutput)
    } finally {
      // Restore the real System.in/out
      System.setIn(realIn)
      System.setOut(realOut)
      cleanupDatabase(initialDB)
    }
  }

  test("Scenario [View] should not change the database") {
    adoptionCenterTest(
      List(Action.View, Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/initial.csv")
  }

  test("Scenario [Surrender Dog(4, \"Luna\", \"Labrador\", \"frisbee\"), Adopt(4)] should not change the database") {
    adoptionCenterTest(
      List(Action.Surrender(Dog(4, "Luna", "Labrador", "frisbee")), Action.Adopt(4), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/initial.csv")
  }

  test("Scenario [Surrender Dog(4, \"Luna\", \"Labrador\", \"frisbee\")] should result in the new dog added to the database") {
    adoptionCenterTest(
      List(Action.Surrender(Dog(4, "Luna", "Labrador", "frisbee")), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/out1.csv")
  }

  test("Scenario [Surrender Dog(4, \"Luna\", \"Labrador\", \"frisbee\"), Surrender Dog(5, \"Bella\", \"Beagle\", \"tennis ball\")] should result in two new dogs added to the database") {
    adoptionCenterTest(
      List(Action.Surrender(Dog(4, "Luna", "Labrador", "frisbee")), Action.Surrender(Dog(5, "Bella", "Beagle", "tennis ball")), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/out2.csv")
  }

  test("Scenario [Surrender Dog(4, \"Luna\", \"Labrador\", \"frisbee\"), Surrender Dog(5, \"Bella\", \"Beagle\", \"tennis ball\"), Adopt 3] should result in two new dogs added to the database and one adopted") {
    adoptionCenterTest(
      List(Action.Surrender(Dog(4, "Luna", "Labrador", "frisbee")), Action.Surrender(Dog(5, "Bella", "Beagle", "tennis ball")), Action.Adopt(3), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/out3.csv")
  }

  test("Scenario [Surrender Dog(4, \"Luna\", \"Labrador\", \"frisbee\"), Adopt 3, Surrender Dog(5, \"Bella\", \"Beagle\", \"tennis ball\"), Adopt 4] should result in two new dogs added to the database and two adopted") {
    adoptionCenterTest(
      List(Action.Surrender(Dog(4, "Luna", "Labrador", "frisbee")), Action.Adopt(3), Action.Surrender(Dog(5, "Bella", "Beagle", "tennis ball")), Action.Adopt(4), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/out4.csv")
  }

  test("Scenario [Surrender Dog(4, \"Luna\", \"Labrador\", \"frisbee\"), Adopt 3, Surrender Dog(5, \"Bella\", \"Beagle\", \"tennis ball\"), Adopt 4, Surrender(Dog(6, \"Max\", \"Golden Retriever\", \"rope toy\"))] should result in three new dogs added to the database and two adopted") {
    adoptionCenterTest(
      List(Action.Surrender(Dog(4, "Luna", "Labrador", "frisbee")), Action.Adopt(3), Action.Surrender(Dog(5, "Bella", "Beagle", "tennis ball")), Action.Adopt(4), Action.Surrender(Dog(6, "Max", "Golden Retriever", "rope toy")), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/out5.csv")
  }

  test("Exiting the program should not change the database") {
    adoptionCenterTest(
      List(Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/initial.csv")
  }

  test("Exiting the program twice should not change the database") {
    adoptionCenterTest(
      List(Action.Exit, Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/initial.csv")
  }

  test("Adoption the same dog twice should only result in one adopted dog") {
    adoptionCenterTest(
      List(Action.Adopt(2), Action.Adopt(2), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/out6.csv")
  }

  test("Surrendering the same dog twice should result in two new dogs with different ids, but the same name, breed, and favorite toy") {
    val dog = Dog(5, "Bella", "Beagle", "tennis ball")
    adoptionCenterTest(
      List(Action.Surrender(dog), Action.Surrender(dog), Action.Exit),
      "Referential Transparency/Dog Adoption Center Exercise/test/resources/out7.csv")
  }
}