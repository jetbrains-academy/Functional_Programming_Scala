import java.nio.file.{Path, Paths}

case class Dog(id: Int, name: String, breed: String, favoriteToy: String):
  def toLine: String = s"$id, $name, $breed, $favoriteToy"
  override def toString: String = s"ID: $id, Name: $name, Breed: $breed, Favorite toy: $favoriteToy"

object Dog:
  val FilePath: Path = Paths.get("Referential Transparency/Dog Adoption Center Exercise/src/main/resources/adoptionCenter.csv")

  def fromLine(line: String): Dog =
    val arr = line.split(",").map(_.trim)
    Dog(arr(0).toInt, arr(1), arr(2), arr(3))

