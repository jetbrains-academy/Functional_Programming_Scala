class Task {
  //put your task here
}

sealed trait Tree[A]:
  def whoAmI: String

case class Branch[A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]:
  override def whoAmI: String = "I'm a branch!"

case class Leaf[A](value: A) extends Tree[A]:
  override def whoAmI: String = "I'm a leaf!"

case object Stump extends Tree[Nothing]:
  override def whoAmI: String = "I'm a stump!"

import Tree.*

val tree: Tree[Int] =
  Branch(
    Branch(Leaf(1), 2, Stump),
    3,
    Branch(Leaf(4), 5, Leaf(6))
  )