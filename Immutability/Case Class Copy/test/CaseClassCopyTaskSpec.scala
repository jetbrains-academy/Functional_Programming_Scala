import org.scalatest.funsuite.AnyFunSuite
import CaseClassCopyTask._

class CaseClassCopyTaskSpec extends AnyFunSuite {
  test("`myCopy` should function in the same manner `copy` does") {
    val user = User("John", "Doe", None, None, None)
    val userEmail = "john.doe@example.com"
    val userTwitter = "johndoe"
    val userInsta = "johndoe"
    val userEmail1 = "alice.smith@example.com"
    val userTwitter1 = "alicesmith"
    val userInsta1 = "alicesmith"
    val userFirstName1 = "Alice"
    val userLastName1 = "Smith"
    assert(user.myCopy(email = Some(userEmail))
          == user.copy(email = Some(userEmail)))
    assert(user.myCopy(email = Some(userEmail), twitterHandle = Some(userTwitter))
          == user.copy(email = Some(userEmail), twitterHandle = Some(userTwitter)))
    assert(user.myCopy(email = Some(userEmail), twitterHandle = Some(userTwitter), instagramHandle = Some(userInsta))
          == user.copy(email = Some(userEmail), twitterHandle = Some(userTwitter), instagramHandle = Some(userInsta)))
    assert(user.myCopy(firstName = userFirstName1, lastName = userLastName1, email = Some(userEmail1), twitterHandle = Some(userTwitter1), instagramHandle = Some(userInsta1))
          == user.copy(firstName = userFirstName1, lastName = userLastName1, email = Some(userEmail1), twitterHandle = Some(userTwitter1), instagramHandle = Some(userInsta1)))
  }
}