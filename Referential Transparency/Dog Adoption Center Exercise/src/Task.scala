import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.*
import cats.effect.{IO, IOApp}
import cats.effect.std.Console
import cats.effect.kernel.Ref
import cats.syntax.all.*

import java.io.{File, PrintWriter}

object VirtualCatAdoptionCenter extends IOApp.Simple:
  override def run: IO[Unit] =
    for {
      lines   <- read(Dog.FilePath)
      shelter =  lines.map(Dog.fromLine)
      _       <- mainMenu(shelter)
    } yield ()

  private def read(path: Path): IO[List[String]] =
    IO { Files.readAllLines(path).asScala.toList }

  private def displayMenu: IO[Unit] = IO.println (
    """
      |Welcome to the Virtual Cat Adoption Center!
      |------------------------------------------
      |1. View all available dogs
      |2. Adopt a dog
      |3. Surrender a dog
      |4. Exit
      |Please select an option (1-4):
      |""".stripMargin
  )

  private def saveShelter(shelter: List[Dog], path: Path): IO[Unit] =
    IO {
      Files.deleteIfExists(path)
      Files.createFile(path)
      Files.writeString(path, shelter.sortBy(_.id).map(_.toLine).mkString("\n"))
    }

  private def mainMenu(shelter: List[Dog]): IO[Unit] = for {
      _          <- displayMenu
      choice     <- IO.readLine
      newShelter <- choice match {
        case "1" => viewDogs(shelter) *> IO.pure(shelter)
        case "2" => adoptDog(shelter)
        case "3" => surrenderDog(shelter)
        case "4" => IO.println("Thank you for visiting!") *> saveShelter(shelter, Dog.FilePath) *> IO.pure(shelter)
        case _   => IO.println("Invalid option. Please try again.") *> IO.pure(shelter)
      }
      _          <- if (choice != "4") mainMenu(newShelter) else IO.unit
    } yield ()


  private def viewDogs(shelter: List[Dog]): IO[Unit] = for {
    _    <-
      if (shelter.isEmpty)
        IO.println("No dogs are currently available for adoption.")
      else
        shelter.traverse_ { dog => IO.println(dog.toString()) }
  } yield ()

  private def surrenderDog(shelter: List[Dog]): IO[List[Dog]] = for {
    _           <- IO.print("Enter dog's name: ")
    name        <- IO.readLine
    _           <- IO.print("Enter dog's breed: ")
    breed       <- IO.readLine
    _           <- IO.print("Enter dog's favorite toy: ")
    favoriteToy <- IO.readLine
    newId       =  if (shelter.isEmpty) 1 else shelter.map(_.id).max + 1
    newDog      =  Dog(newId, name, breed, favoriteToy)
    newShelter  =  newDog :: shelter
    _           <- IO.println(s"Dog ${newDog.name} has been surrendered with ID ${newDog.id}.")
  } yield newShelter

  private def adoptDog(shelter: List[Dog]): IO[List[Dog]] = for {
    _           <- IO.print("Enter the ID of the dog you wish to adopt: ")
    idInput     <- IO.readLine
    id          <- IO.fromOption(idInput.toIntOption)(new NumberFormatException("Invalid ID input"))
    (dog, rest) =  shelter.partition(_.id == id)
    _           <- if (dog.isEmpty) IO.println("No dog found with the provided ID.")
                   else IO.println(s"Congratulations! You have adopted ${dog.head.name}.")
  } yield rest