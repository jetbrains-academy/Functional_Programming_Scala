class Task {
  //put your task here
}

enum Tree[A]:
  case Branch(left: Tree[A], value: A, right: Tree[A])
  case Leaf(value: A)
  case Stump

/*
    3
   / \
  2   5
 /   / \
1   4   6
*/

import Tree.*

val tree: Tree[Int] =
  Branch(
    Branch(Leaf(1), 2, Stump),
    3,
    Branch(Leaf(4), 5, Leaf(6))
  )