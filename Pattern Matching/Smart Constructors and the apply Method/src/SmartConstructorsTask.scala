object SmartConstructorsTask:
  class Dog private (var name: String, var breed: String, var owner: String):
    override def toString(): String = s"Dog($name,$breed,$owner)"

  object Dog:
    val defaultBreed = "Domestic"
    val defaultOwner = "Looking for a forever home"

    // Implement the smart constructor which uses the default values
    // when the breed or the owner are not known
    def apply(name: String, breed: Option[String], owner: Option[String]): Dog =
      new Dog(name, breed.getOrElse(defaultBreed), owner.getOrElse(defaultOwner))

  @main
  def main(): Unit = {
    val jack = Dog("Yuki", None, None)
    val yuki = Dog("Yuki", Some("Akita"), None)
    val hoops = Dog("Hoops", Some("Australian Shepherd"), Some("Alex"))

    List(jack, yuki, hoops).foreach(x => println(x.toString()))
  }

























