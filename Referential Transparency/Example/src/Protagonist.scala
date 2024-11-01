import java.nio.file.{Path, Paths}

case class Protagonist(firstName: String, lastName: String, age: Int):
  def toLine: String = s"$firstName,$lastName,$age"

object Protagonist:
  val FilePath: Path = Paths.get("Referential Transparency/Example/src/main/resources/protagonists.csv")

  def fromLine(line: String): Protagonist =
    val arr = line.split(",")
    Protagonist(arr(0), arr(1), arr(2).toInt)

