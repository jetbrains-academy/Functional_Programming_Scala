import org.scalatest.funsuite.AnyFunSuite
import Task._

class TaskSpec extends AnyFunSuite {

  val daria = User("Daria", 13, None)
  val sasha = User("Sasha", 33, Some(daria))
  val masha = User("Masha", 60, Some(sasha))

  val users = UserService.users.concat(List(daria, sasha, masha))

  def etalonGetGrandchild(name: String): Either[UserService.SearchError, User] =
    def getChild(user: User, error: UserService.SearchError): Either[UserService.SearchError, User] =
      user.child match
        case None => Left(error)
        case Some(ch) => Right(ch)

    UserService.loadUser(name)
      .flatMap(u => getChild(u, UserService.SearchError.NoChildFound(name)))
      .flatMap(ch => getChild(ch, UserService.SearchError.NoGrandchildFound(name, ch.name)))

  def etalonGetGrandchildAge(name: String): Either[UserService.SearchError, Int] =
    etalonGetGrandchild(name).flatMap(u => Right(u.age))

  test("getGrandchild should retrieve a grandchild of the user with the given name if they exist") {
    users.foreach { u =>
      val name = u.name
      assertResult(etalonGetGrandchild(name))
        (UserService.getGrandchild(name))
    }
  }

  test("getGrandchildAge should retrieve the age of a grandchild") {
    users.foreach { u =>
      val name = u.name
      assertResult(etalonGetGrandchildAge(name))
        (UserService.getGrandchildAge(name))
    }
  }
}
