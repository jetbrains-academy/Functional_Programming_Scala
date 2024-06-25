object Task:
  class User(val name: String, val age: Int, val child: Option[User])

  object UserService {
    enum SearchError:
      case NoUserFound(name: String)
      case NoChildFound(name: String)
      case NoGrandchildFound(userName: String, childName: String)

    import SearchError.*

    /**
     * Retrieves the user by their name.
     * @param name the name of the user
     * @return the user wrapped in `Right` if they exist, and `Left UserNotExist` otherwise.
     */
    def loadUser(name: String): Either[SearchError, User] =
      users.find(u => u.name == name) match
        case None    => Left(NoUserFound(name))
        case Some(u) => Right(u)

    /**
     * Retrieves the grandchild of the user with the given name.
     * @param name the name of the user
     * @return the user's grandchild `Right` if they exist, and `Left` with an error otherwise.
     */
    def getGrandchild(name: String): Either[SearchError, User] =
      def getChild(user: User, error: SearchError): Either[SearchError, User] =
        user.child match
          case None     => Left(error)
          case Some(ch) => Right(ch)

      loadUser(name)
        .flatMap(u => getChild(u, NoChildFound(name)))
        .flatMap(ch => getChild(ch, NoGrandchildFound(name, ch.name)))

    /**
     * Retrieves the age of a grandchild of the user with the given name.
     * @param name the name of the user
     * @return the age of the user's grandchild wrapped in `Some` if they exist, and `None` otherwise
     */
    def getGrandchildAge(name: String): Either[SearchError, Int] =
      getGrandchild(name).flatMap( u => Right(u.age) )

    val users = {
      val sofia = new User("Sofia", 10, None)
      val takumi = new User("Takumi", 8, None)
      val aisha = new User("Aisha", 6, None)
      val anastasia = new User("Anastasia", 14, None)
      val ivan = new User("Ivan", 45, Some(anastasia))
      val ife = new User("Ife", 7, None)
      val omar = new User("Omar", 16, None)
      val youssef = new User("Youssef", 50, Some(omar))
      val luca = new User("Luca", 11, None)
      val karin = new User("Karin", 9, None)
      val bao = new User("Bao", 4, None)
      val priya = new User("Priya", 5, None)
      val arjun = new User("Arjun", 12, None)
      val milos = new User("Milos", 10, None)
      val jelena = new User("Jelena", 36, Some(milos))
      val leila = new User("Leila", 15, None)
      val eva = new User("Eva", 6, None)
      val mateo = new User("Mateo", 13, None)
      val nia = new User("Nia", 5, None)
      val viktor = new User("Viktor", 11, None)
      val tomas = new User("Tomas", 8, None)
      val mei = new User("Mei", 9, None)
      val jack = new User("Jack", 7, None)
      val alejandro = new User("Alejandro", 40, Some(sofia))
      val hiroshi = new User("Hiroshi", 35, Some(takumi))
      val fatima = new User("Fatima", 28, Some(aisha))
      val marfa = new User("Marfa", 65, Some(ivan))
      val chinwe = new User("Chinwe", 32, Some(ife))
      val thu = new User("Thu", 82, Some(youssef))
      val giulia = new User("Giulia", 38, Some(luca))
      val sven = new User("Sven", 42, Some(karin))
      val linh = new User("Linh", 27, None)
      val ravi = new User("Ravi", 33, None)
      val maya = new User("Maya", 41, Some(arjun))
      val marko = new User("Marko", 59, Some(jelena))
      val mohammed = new User("Mohammed", 44, Some(leila))
      val anna = new User("Anna", 30, Some(eva))
      val carlos = new User("Carlos", 47, Some(mateo))
      val amina = new User("Amina", 29, None)
      val igor = new User("Igor", 39, Some(viktor))
      val julia = new User("Julia", 34, Some(tomas))
      val chen = new User("Chen", 37, Some(mei))
      val emily = new User("Emily", 31, Some(jack))

      List(alejandro, hiroshi, fatima, marfa, chinwe, thu, giulia, sven, linh, ravi, maya, marko, mohammed, anna,
        carlos, amina, igor, julia, chen, emily, sofia, takumi, aisha, anastasia, ivan, ife, omar, youssef, luca,
        karin, bao, priya, arjun, milos, jelena, leila, eva, mateo, nia, viktor, tomas, mei, jack
      )
    }
  }
