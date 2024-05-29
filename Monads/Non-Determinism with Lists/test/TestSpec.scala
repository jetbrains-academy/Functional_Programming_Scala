import org.scalatest.funsuite.AnyFunSuite
import Task._

class TaskSpec extends AnyFunSuite {

  val daria = User("Daria", 13, Set.empty)
  val sasha = User("Sasha", 33, Set(daria))
  val masha = User("Masha", 60, Set(sasha))

  val users = UserService.users.concat(List(daria, sasha, masha))

  def etalonGetGrandchildren(name: String): Set[User] =
    for {
      user <- UserService.loadUser(name).toSet
      child <- user.children
      grandchild <- child.children
    } yield grandchild

  def etalonGetGrandchildrenAges(name: String): List[Int] =
    for {
      grandchild <- etalonGetGrandchildren(name).toList
    } yield grandchild.age



  test("getGrandchildren should retrieve a set of grandchildren of the user with the given name") {
    users.foreach { u =>
      val name = u.name
      assertResult(etalonGetGrandchildren(name))
        (UserService.getGrandchildren(name))
    }
  }

  test("getGrandchildrenAges should retrieve the ages of grandchild") {
    users.foreach { u =>
      val name = u.name
      assertResult(etalonGetGrandchildrenAges(name))
        (UserService.getGrandchildrenAges(name))
    }
  }
}
