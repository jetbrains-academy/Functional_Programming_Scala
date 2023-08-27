class Task {
  //put your task here
}
object Ids:
  opaque type UserId = Int
object UserId:
  def apply(id: Int): UserId = id

opaque type ProductId = Int
object ProductId:
  def apply(id: Int): ProductId = id

import Ids.*
case class User(id: UserId, name: String)

def getUser(id: UserId): User = ???
val userId = UserId(123)
val productId = ProductId(456)

// getUser(productId) would be a compile error
val user = getUser(userId) // This is fine