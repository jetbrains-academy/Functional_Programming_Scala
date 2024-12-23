## The example

Let's start with the file. It's a short CSV (comma-separated values) file called `protagonists.csv`.

```csv
Jonas,Kahnwald,17
Hannah,Kahnwald,47
```

Feel free to add more protagonists to the file or modify their data.
To read the file, we will use Java NIO. The standard, non-RT way of doing it would look like this:

```scala
import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.*

def read(path: Path): List[String] =
  Files.readAllLines(path).asScala.toList
```

When we call this version of the read method with a path to the CSV file, it reads its contents immediately and returns a list of lines. Let's rewrite it using Cats Effect:

```scala
import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.*
import cats.effect.IO

def read(path: Path): IO[List[String]] =
  IO { Files.readAllLines(path).asScala.toList }
```

That's it: all we need to do is to wrap our file-reading logic in the IO monad. Now, when we call read, the file will not be read **yet**. Instead, the file-reading logic will be added to the scenario held in the monad. After we build the whole scenario, it will be executed only when we pass the monad to Cats Effect.

After reading the lines, we will map them to the `Protagonist` case class instances. We will use the `fromLine` method of the `Protagonist` companion object:

```scala
object Protagonist:
  def fromLine(line: String): Protagonist =
    val arr = line.split(",")
    Protagonist(arr(0), arr(1), arr(2).toInt)
```

This method is pure so there's no need to refactor it.

The next step will be to ask the user for the number with which we will update the age of our protagonists. Again, in its straightforward form, the code is not referentially transparent. Of course it's not: the user can give a different answer every time. But suppose we wrap the logic in the IO monad instead of immediately asking the user. In that case, we will only create an immutable value: an instance of the IO monad with the logic for asking the user inside it.

```scala
val askForUpdate: IO[Int] =
  for {
    _      <- IO.println("By how much should I update the age?")
    answer <- IO(scala.io.StdIn.readLine())
  } yield answer.toInt
```

Take a moment and look at what happens here. We get our answer in three steps. First, we want to print out the prompt. Since we don't want to do it immediately - but only add it to the scenario - instead of the standard `println`, we use a utility method of the IO monad. Then, we want to ask the user for the answer - again, not immediately, so we wrap the call to `readLine` with the IO monad, and at the end, we convert the answer, which comes as a string, to an integer. For the sake of simplicity, let's assume that the user will always give an answer that can be converted.

Note that since IO is a monad, we can connect all three steps using the Scala `for/yield` notation, just as we did in the previous chapters with `Option`, `Either`, and `Try`. We will stick to this notation in our example, but be aware that Cats Effect developers use a more idiomatic syntax. To learn more, visit [the Cats Effect webpage](https://typelevel.org/cats-effect/) and their Discord server.

Now that we have a list of protagonists and the number with which to update their age, we need to turn our `updateAge` function into a referentially transparent version.

```scala
def updateAge(p: Protagonist, n: Int): IO[Protagonist] =
    val newAge = p.age + n
    for {
      _       <- IO.println(s"The age of ${p.firstName} ${p.lastName} changes from ${p.age} to $newAge")
      updated =  p.copy(age = newAge)
    } yield updated
```

Now, we have to call our new `updateAge` function for every protagonist. But we can't just use `map` over a list of protagonists. `updateAge` returns `IO[Protagonist]` now, which means that if we mapped `List[Protagonist]` with it, we would get `List[IO[Protagonist]]`, while what we want is `IO[List[Protagonist]]`. Fortunately, Cats Effect provides us with an extension method called `traverse`.

```scala
import cats.syntax.all.*

val protagonists: List[Protagonist] = ...
val n: Int = ...
val updated: IO[List[Protagonist]] = protagonists.traverse(updateAge(_, n))
```

The only thing left to do is write the new data to the file. We serialize each protagonist using a `toLine` method on the `Protagonist` class.

```scala
case class Protagonist(firstName: String, lastName: String, age: Int):
  def toLine: String = s"$firstName,$lastName,$age"
```

To write to the file, we will again use Java NIO and wrap the code in the IO monad, just as we did when we read the data at the beginning.

```scala
def write(path: Path, lines: List[String]): IO[Unit] =
  IO { Files.writeString(path, lines.mkString("\n")) }
```

Finally, we need to join all the instances of the IO monad that our functions return and the calls to our pure functions. We will do it using the Scala `for/yield` notation. When that's done, we will have one IO monad holding the scenario we built:

- Read data from a CSV file
- Deserialize it
- Ask the user for the update
- Create an updated version of the data
- Write it down to the file again

```scala
import cats.effect.{IO, IOApp}
import cats.syntax.all.*

object CatsEffectVersion extends IOApp.Simple:
  override def run: IO[Unit] =
    for {
      lines        <- read(Protagonist.FilePath)
      protagonists =  lines.map(Protagonist.fromLine)
      n            <- askForUpdate
      updated      <- protagonists.traverse(updateAge(_, n))
      newLines     =  updated.map(_.toLine)
      _            <- write(Protagonist.FilePath, newLines)
    } yield ()
```

After the IO monad is built, we give it to Cats Effect to execute it. One way to do it is to make our main object extend the Cats Effect class `IOApp.Simple`, and implement its `run` method. Cats Effect then runs the method to obtain the IO monad that is, in turn, executed by the framework.

If you're curious, the example was inspired by a German sci-fi TV series, "Dark," in which protagonists travel through time often and meet older and younger versions of themselves.
