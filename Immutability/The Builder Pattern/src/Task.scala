class Task {
  case class User( firstName: String,
                   lastName: String,
                   email: Option[String] = None,
                   twitterHandle: Option[String] = None,
                   instagramHandle: Option[String] = None
                 )
  class UserBuilder(private var firstName: String, private var lastName: String):
    private var email: Option[String] = None
    private var twitterHandle: Option[String] = None
    private var instagramHandle: Option[String] = None
    def setEmail(e: String): UserBuilder =
      email = Some(e)
      this

    def setTwitterHandle(t: String): UserBuilder =
      twitterHandle = Some(t)
      this

    def setInstagramHandle(i: String): UserBuilder =
      instagramHandle = Some(i)
      this

    def build: User =
      User(firstName, lastName, email, twitterHandle, instagramHandle)

    // usage
    val user: User = new UserBuilder("John", "Doe")
      .setEmail("john.doe@example.com")
      .setTwitterHandle("@johnDoe")
      .setInstagramHandle("@johnDoe_insta")
      .build
    println(user)
    // prints out User("John", "Doe", Some("john.doe@example.com"), Some("@johndoe"), Some("@johnDoe_insta"))

}