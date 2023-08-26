class Task {
  case class Cat(name: String, color: String)

  val myCat = Cat("Whiskers", "white")
  myCat match {
    case Cat(name, color) => println(s"I have a $color cat named $name.")
  }
  //put your task here
}

