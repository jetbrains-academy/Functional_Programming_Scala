import cats.effect.IO
import cats.effect.unsafe.implicits.global
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

def checkDatabase(expectedDBPath: String): Unit =
  val actualDB = Files.readString(Dog.FilePath)
  val expectedDB = Files.readString(Path.of(expectedDBPath))
  assert(actualDB.trim == expectedDB.trim)

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
}