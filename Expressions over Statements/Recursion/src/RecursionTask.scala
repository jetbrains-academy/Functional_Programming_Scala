import scala.annotation.tailrec

object RecursionTask:
  enum Operation:
    case Plus
    case Mult

  enum Tree:
    case Node(val operation: Operation, val left: Tree, val right: Tree)
    case Leaf(val num: Int)

  import Tree.*
  import Operation.*

  def eval(ast: Tree): Int =
    ast match
      case Leaf(num) => num
      case Node(operation, left, right) =>
        operation match
          case Plus => eval(left) + eval(right)
          case Mult => eval(left) * eval(right)

  def prefixPrinter(ast: Tree): String =
    ast match
      case Leaf(num) => s"$num"
      case Node(operation, left, right) =>
        val stringOperation = operation match
          case Plus => "+"
          case Mult => "*"
        s"$stringOperation ${prefixPrinter(left)} ${prefixPrinter(right)}"

  @main
  def main(): Unit =
    /**
     *     *
     *    / \
     *   +  5
     *  / \
     * 1  3
     */
    val tree = Node(Mult, Node(Plus, Leaf(1), Leaf(3)), Leaf(5))
    println(eval(tree)) // 20
    println(prefixPrinter(tree)) // * + 1 3 5

