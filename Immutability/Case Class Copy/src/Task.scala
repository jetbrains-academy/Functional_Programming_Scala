class Task {
  case class User( firstName: String,
                   lastName: String,
                   email: Option[String] = None,
                   twitterHandle: Option[String] = None,
                   instagramHandle: Option[String] = None
                 )

  // usage
  val originalUser = User("Jane", "Doe", Some("jane.doe@example.com"))

  // Create a copy of originalUser, changing the email and adding a twitter handle
  val updatedUser = originalUser.copy(
    email = Some("new.jane.doe@example.com"),
    twitterHandle = Some("@newJaneDoe")
  )

  println(s"Original user: $originalUser")
  // prints out User("Jane", "Doe", Some("jane.doe@example.com"), None, None)

  println(s"Updated user: $updatedUser")
  // prints out User("Jane", "Doe", Some("new.jane.doe@example.com"), Some("@newJaneDoe"), None)
}