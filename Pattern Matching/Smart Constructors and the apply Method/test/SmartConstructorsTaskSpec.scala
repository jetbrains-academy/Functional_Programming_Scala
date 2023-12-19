import org.scalatest.funsuite.AnyFunSuite
import SmartConstructorsTask._

class SmartConstructorsTaskSpec extends AnyFunSuite {
  test("It should be possible to curry `filterList`") {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      val jack = Dog("Yuki", None, None)
      val yuki = Dog("Yuki", Some("Akita"), None)
      val hoops = Dog("Hoops", Some("Australian Shepherd"), Some("Alex"))

      List(jack, yuki, hoops).foreach(x => println(x.toString()))

      val expected = s"Dog(Yuki,${Dog.defaultBreed},${Dog.defaultOwner})\nDog(Yuki,Akita,${Dog.defaultOwner})\nDog(Hoops,Australian Shepherd,Alex)"
      val actual = stream.toString().replaceAll("\r\n", "\n").trim
      assert(actual == expected)
    }
  }
}
