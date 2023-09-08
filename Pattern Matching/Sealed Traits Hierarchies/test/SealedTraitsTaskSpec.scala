import org.scalatest.funsuite.AnyFunSuite
import SealedTraitsTask._

class SealedTraitsTaskSpec extends AnyFunSuite {
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

  val stump = Stump

  val leaf = Leaf(1)

  test("`isBalanced` should be true for a balanced tree") {
    assert(stump.isBalanced)
    assert(leaf.isBalanced)
    assert(balancedTree.isBalanced)
    assert(fullTree.isBalanced)
  }

  test("`isBalanced` should return false on an unbalanced tree") {
    assert(!unbalancedTree.isBalanced)
    assert(!degenerateTree.isBalanced)
  }

  test("`height` should be equal to the height of the tree") {
    assert(stump.height == 0)
    assert(leaf.height == 1)
    assert(balancedTree.height == 3)
    assert(fullTree.height == 3)
    assert(unbalancedTree.height == 3)
    assert(degenerateTree.height == 3)
  }
}
