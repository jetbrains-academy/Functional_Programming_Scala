object CaseClassTask:
  // Exercise: create a case class which represents a dog.
  // Each dog should have a name, a breed, and a favorite toy.
  // Model these features as Strings for now.
  case class Dog(name: String, breed: String, favoriteToy: String)

  def introduceDog(dog: Dog): Unit =
    dog match
      case Dog(name, breed, favoriteToy) =>
        println(s"This dog's name is $name, it's a(n) $breed, and its favorite toy is a(n) $favoriteToy.")


  @main
  def main(): Unit =
    val yuki = Dog("Yuki", "Akita", "ball")
    val hoops = Dog("Hoops", "Australian Shepherd", "squeaky pig")
    val bowser = Dog("Bowser", "Chow Chow", "dinosaur bone")

    List(yuki, hoops, bowser).foreach(introduceDog)

