object Task:
  class User(val name: String, val age: Int, val children: Set[User])

  object UserService {
    /**
     * Retrieves the user by their name.
     * @param name the name of the user
     * @return the user wrapped in `Right` if they exist, and `Left UserNotExist` otherwise.
     */
    def loadUser(name: String): Option[User] =
      users.find(u => u.name == name)

    /**
     * Retrieves the grandchild of the user with the given name.
     * @param name the name of the user
     * @return the set of user's grandchildren.
     */
    def getGrandchildren(name: String): Set[User] =
      for {
        user       <- loadUser(name).toSet
        child      <- user.children
        grandchild <- child.children
      } yield grandchild

    /**
     * Retrieves the age of a grandchild of the user with the given name.
     * @param name the name of the user
     * @return the list of all ages of the the user's grandchildren.
     */
    def getGrandchildrenAges(name: String): List[Int] =
      for {
        grandchild <- getGrandchildren(name).toList
      } yield grandchild.age

    val users = {
      val sofia = new User("Sofia", 10, Set.empty)
      val takumi = new User("Takumi", 8, Set.empty)
      val aisha = new User("Aisha", 6, Set.empty)
      val anastasia = new User("Anastasia", 14, Set.empty)
      val maria = new User("Maria", 12, Set.empty)
      val ivan = new User("Ivan", 45, Set(anastasia, maria))
      val ife = new User("Ife", 7, Set.empty)
      val omar = new User("Omar", 16, Set.empty)
      val youssef = new User("Youssef", 50, Set(omar))
      val luca = new User("Luca", 11, Set.empty)
      val karin = new User("Karin", 9, Set.empty)
      val bao = new User("Bao", 4, Set.empty)
      val priya = new User("Priya", 5, Set.empty)
      val arjun = new User("Arjun", 12, Set.empty)
      val milos = new User("Milos", 10, Set.empty)
      val milica = new User("Milica", 10, Set.empty)
      val zoran = new User("Zoran", 8, Set.empty)
      val jelena = new User("Jelena", 36, Set(milos, milica, zoran))
      val dragoljub = new User("Dragoljub", 6, Set.empty)
      val miroslav = new User("Miroslav", 35, Set(dragoljub))
      val leila = new User("Leila", 15, Set.empty)
      val eva = new User("Eva", 6, Set.empty)
      val mateo = new User("Mateo", 13, Set.empty)
      val nia = new User("Nia", 5, Set.empty)
      val viktor = new User("Viktor", 11, Set.empty)
      val tomas = new User("Tomas", 8, Set.empty)
      val mei = new User("Mei", 9, Set.empty)
      val jack = new User("Jack", 7, Set.empty)
      val alejandro = new User("Alejandro", 40, Set(sofia))
      val hiroshi = new User("Hiroshi", 35, Set(takumi))
      val fatima = new User("Fatima", 28, Set(aisha))
      val marfa = new User("Marfa", 65, Set(ivan))
      val chinwe = new User("Chinwe", 32, Set(ife))
      val thu = new User("Thu", 82, Set(youssef))
      val giulia = new User("Giulia", 38, Set(luca))
      val sven = new User("Sven", 42, Set(karin))
      val linh = new User("Linh", 27, Set.empty)
      val ravi = new User("Ravi", 33, Set.empty)
      val maya = new User("Maya", 41, Set(arjun))
      val marko = new User("Marko", 59, Set(jelena, miroslav))
      val mohammed = new User("Mohammed", 44, Set(leila))
      val anna = new User("Anna", 30, Set(eva))
      val carlos = new User("Carlos", 47, Set(mateo))
      val amina = new User("Amina", 29, Set.empty)
      val igor = new User("Igor", 39, Set(viktor))
      val julia = new User("Julia", 34, Set(tomas))
      val chen = new User("Chen", 37, Set(mei))
      val emily = new User("Emily", 31, Set(jack))

      List(alejandro, hiroshi, fatima, marfa, chinwe, thu, giulia, sven, linh, ravi, maya, marko, mohammed, anna,
        carlos, amina, igor, julia, chen, emily, sofia, takumi, aisha, anastasia, ivan, ife, omar, youssef, luca,
        karin, bao, priya, arjun, milos, jelena, leila, eva, mateo, nia, viktor, tomas, mei, jack
      )
    }
  }