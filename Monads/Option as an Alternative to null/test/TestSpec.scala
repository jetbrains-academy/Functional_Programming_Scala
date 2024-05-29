import org.scalatest.funsuite.AnyFunSuite
import Task._

class TaskSpec extends AnyFunSuite {
  val daria = User("Daria", 13, None)
  val sasha = User("Sasha", 33, Some(daria))
  val masha = User("Masha", 60, Some(sasha))

  val users = UserService.users.concat(List(daria, sasha, masha))

  test("getGrandchild should retrieve a grandchild of the user with the given name if they exist") {
    users.foreach { u =>
      val name = u.name
      assertResult(UserService.loadUser(name).flatMap(_.child).flatMap(_.child))
                  (UserService.getGrandchild(name))
    }
  }

  test("getGrandchildAge should retrieve the age of a grandchild") {
    users.foreach { u =>
      val name = u.name
      assertResult(UserService.loadUser(name).flatMap(_.child).flatMap(_.child).flatMap(u => Option(u.age)))
                  (UserService.getGrandchildAge(name))
    }
  }
}
