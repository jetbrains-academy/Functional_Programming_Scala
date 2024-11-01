import java.nio.file.{Path, Paths}

case class Dog(id: Int, name: String, breed: String, favoriteToy: String):
  def toLine: String = s"$id, $name, $breed, $favoriteToy"
  override def toString: String = s"ID: $id, Name: $name, Breed: $breed, Favorite toy: $favoriteToy"

object Dog:
  val FilePath: Path = Paths.get("Referential Transparency/Dog Adoption Center Exercise/src/main/resources/adoptionCenter.csv")

  def fromLine(line: String): Dog =
    val arr = line.split(",")
    Dog(arr(0).trim.toInt, arr(1).trim, arr(2).trim, arr(3).trim)

