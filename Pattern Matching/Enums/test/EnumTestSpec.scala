import org.scalatest.funsuite.AnyFunSuite
import EnumTask._

class EnumTaskSpec extends AnyFunSuite {
  import Tree.*
  /*
      3
     / \
    2   5
   /   / \
  1   4   6
  */
  val balancedTree: Tree[Int] =
    Branch(
      Branch(Leaf(1), 2, Stump),
      3,
      Branch(Leaf(4), 5, Leaf(6)))

  /*
      3
     / \
    2   5
   /|  | \
  1 7  4  6
  */
  val fullTree: Tree[Int] =
    Branch(
      Branch(Leaf(1), 2, Leaf(7)),
      3,
      Branch(Leaf(4), 5, Leaf(6)))

  /*
      3
       \
        5
       / \
      4   6
  */
  val unbalancedTree: Tree[Int] =
    Branch(
      Stump,
      3,
      Branch(Leaf(4), 5, Leaf(6)))

  /*
      3
     /
    2
   /
  1
  */
  val degenerateTree: Tree[Int] =
    Branch(
      Branch(Leaf(1), 2, Stump),
      3,
      Stump)

  test("isTreeBalanced should return true on a balanced tree") {
    assert(isTreeBalanced(Stump))
    assert(isTreeBalanced(Leaf(7)))
    assert(isTreeBalanced(balancedTree))
    assert(isTreeBalanced(fullTree))
  }

  test("isTreeBalanced should return false on an unbalanced tree") {
    assert(!isTreeBalanced(unbalancedTree))
    assert(!isTreeBalanced(degenerateTree))
  }
}
