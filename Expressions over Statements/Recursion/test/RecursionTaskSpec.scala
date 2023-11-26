import org.scalatest.funsuite.AnyFunSuite
import RecursionTask._

class RecursionTaskSpec extends AnyFunSuite {
  import Tree.*
  import Operation.*

  private val num0 = Leaf(0)
  private val num1 = Leaf(1)
  private val num2 = Leaf(2)
  private val num3 = Leaf(3)
  private val num4 = Leaf(4)

  private val expr0 = Node(Plus, num1, num2)
  private val expr1 = Node(Mult, num3, num4)
  private val expr2 = Node(Plus, expr0, expr1)
  private val expr3 = Node(Mult, num0, expr2)

  test("Evaluator should compute values of expressions") {
    assert(eval(num0) == 0)
    assert(eval(num1) == 1)
    assert(eval(num2) == 2)
    assert(eval(num3) == 3)
    assert(eval(num4) == 4)
    assert(eval(expr0) == 3)
    assert(eval(expr1) == 12)
    assert(eval(expr2) == 15)
    assert(eval(expr3) == 0)
  }

  test("prefixPrinter should print expressions in prefix form") {
    assert(prefixPrinter(num0) == "0")
    assert(prefixPrinter(num1) == "1")
    assert(prefixPrinter(num2) == "2")
    assert(prefixPrinter(num3) == "3")
    assert(prefixPrinter(num4) == "4")
    assert(prefixPrinter(expr0) == "+ 1 2")
    assert(prefixPrinter(expr1) == "* 3 4")
    assert(prefixPrinter(expr2) == "+ + 1 2 * 3 4")
    assert(prefixPrinter(expr3) == "* 0 + + 1 2 * 3 4")
  }
}