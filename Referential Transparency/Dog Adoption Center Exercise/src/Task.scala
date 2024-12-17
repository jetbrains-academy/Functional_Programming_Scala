import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.*
import cats.effect.{IO, IOApp}
import cats.syntax.all.*

object Task extends IOApp.Simple:
  /**
   * The main function of our console application.
   * It should parse the adoption center CSV file and then call the mainMenu function.
   */
  override def run: IO[Unit] =
    for {
      lines   <- read(Dog.FilePath)
      shelter =  lines.map(Dog.fromLine)
      _       <- mainMenu(shelter)
    } yield ()
  
  /**
   * This function just reads the input file.
   * @param path -- the path to the input file
   * @return lines of the input file
   */
  private def read(path: Path): IO[List[String]] =
    IO { Files.readAllLines(path).asScala.toList }

  /**
   * Displays the menu, explaining available options.
   */
  private def displayMenu: IO[Unit] = IO.println (
    """
      |Welcome to the Virtual Dog Adoption Center!
      |------------------------------------------
      |1. View all available dogs
      |2. Adopt a dog
      |3. Surrender a dog
      |4. Exit
      |Please select an option (1-4):
      |""".stripMargin
  )

  /**
   * Main application loop.
   * It should display the menu, prompt the user to input an option they want to do, and execute that action.
   * If the user inputs "4", the application loop should be stopped, and the shelter saved into the file.
   * @param shelter -- a list of the available dogs
   */
  def mainMenu(shelter: List[Dog]): IO[Unit] = for {
      _          <- displayMenu
      choice     <- IO.readLine
      newShelter <- choice match {
        case "1" => viewDogs(shelter) *> IO.pure(shelter)
        case "2" => adoptDog(shelter)
        case "3" => surrenderDog(shelter)
        case "4" => exitShelter(shelter, Dog.FilePath) *> IO.pure(shelter)
        case _   => IO.println("Invalid option. Please try again.") *> IO.pure(shelter)
      }
      _          <- if (choice != "4") mainMenu(newShelter) else IO.unit
    } yield ()

  /**
   * Thanks the user for visiting, and store the current state of the shelter in the file.
   * @param shelter -- a list of the dogs available at the end of the session.
   * @param path -- a path to the file storing the data about the shelter.
   */
  private def exitShelter(shelter: List[Dog], path: Path): IO[Unit] = for {
    _ <- IO.println("Thank you for visiting!")
    _ <- IO { Files.deleteIfExists(path)
              Files.createFile(path)
              Files.writeString(path, shelter.sortBy(_.id).map(_.toLine).mkString("\n"))
            }
  } yield ()

  /**
   * Views all the dogs in the shelter.
   * If the list is empty, explain that there are no dogs available to adopt
   * @param shelter -- a list of the available dogs
   */
  private def viewDogs(shelter: List[Dog]): IO[Unit] = for {
    _    <-
      if (shelter.isEmpty)
        IO.println("No dogs are currently available for adoption.")
      else
        shelter.traverse_ { dog => IO.println(dog.toString()) }
  } yield ()

  /**
   * Surrenders a dog to the shelter.
   * It should prompt the user to input the dog's name, breed, and favorite toy.
   * The dog should be assigned a new unique identifier before adding to the list.
   * The function yields `newShelter`, which describes the shelter after surrendering the dog.
   * @param shelter -- a list of the available dogs
   */
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

  /**
   * Adopt a dog from the shelter.
   * Should prompt the user to input the dog's identifier.
   * If the ID doesn't exist, report the error to the user and exit to the main loop.
   * Otherwise, congratulate the user on adoption and remove the dog from the shelter.
   * The function yields `newShelter`, which describes the shelter after adopting the dog.
   *
   * @param shelter -- a list of the available dogs
   */
  private def adoptDog(shelter: List[Dog]): IO[List[Dog]] = for {
    _                 <- IO.print("Enter the ID of the dog you wish to adopt: ")
    idInput           <- IO.readLine
    id                <- IO.fromOption(idInput.toIntOption)(new NumberFormatException("Invalid ID input"))
    (dog, newShelter) =  shelter.partition(_.id == id)
    _                 <- if (dog.isEmpty) IO.println("No dog found with the provided ID.")
                         else IO.println(s"Congratulations! You have adopted ${dog.head.name}.")
  } yield newShelter