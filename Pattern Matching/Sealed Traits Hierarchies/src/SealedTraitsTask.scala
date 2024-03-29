object SealedTraitsTask:
  sealed trait Tree[+A]:
    val height: Int
    val isBalanced: Boolean
    def whoAmI: String

  case class Branch[A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]:
    override val height = Math.max(left.height, right.height) + 1
    override val isBalanced = Math.abs(left.height - right.height) <= 1
    override def whoAmI: String = "I'm a branch!"

  case class Leaf[A](value: A) extends Tree[A]:
    override val height = 1
    override val isBalanced = true
    override def whoAmI: String = "I'm a leaf!"

  case object Stump extends Tree[Nothing]:
    override val height = 0
    override val isBalanced = true
    override def whoAmI: String = "I'm a stump!"

  val tree =
    Branch(
      Branch(Leaf(1), 2, Stump),
      3,
      Branch(Leaf(4), 5, Leaf(6))
    )

  @main
  def main(): Unit =
    println(tree.isBalanced)
    println(tree.height)