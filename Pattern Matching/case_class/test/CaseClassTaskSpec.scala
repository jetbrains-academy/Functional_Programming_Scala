import org.scalatest.funsuite.AnyFunSuite
import CaseClassTask._

class CaseClassTaskSpec extends AnyFunSuite {
  test("It should be possible to curry `filterList`") {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      val yuki = Dog("Yuki", "Akita", "ball")
      val hoops = Dog("Hoops", "Australian Shepherd", "squicky pig")
      val bowser = Dog("Bowser", "Chow Chow", "dinosaur bone")

      List(yuki, hoops, bowser).foreach(introduceDog)

      assert(stream.toString() == "This dog's name is Yuki, it's a(n) Akita, and its favorite toy is a(n) ball.\nThis dog's name is Hoops, it's a(n) Australian Shepherd, and its favorite toy is a(n) squicky pig.\nThis dog's name is Bowser, it's a(n) Chow Chow, and its favorite toy is a(n) dinosaur bone.\n")
    }
  }
}
