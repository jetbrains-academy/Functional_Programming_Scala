import org.scalatest.funsuite.AnyFunSuite
import CaseObjectTask._

class CaseObjectTaskSpec extends AnyFunSuite {
  test("`move` in the `North` direction should move north") {
    assert(move(TurtleBot, North, InitialCoordinates) == Coordinates(0, 1))
    assert(move(TurtleBot, North, Coordinates(0, 1)) == Coordinates(0, 2))
    assert(move(HareBot, North, Coordinates(0, 1)) == Coordinates(0, 4))
  }

  test("`move` in the `West` direction should move west") {
    assert(move(TurtleBot, West, InitialCoordinates) == Coordinates(1, 0))
    assert(move(TurtleBot, West, Coordinates(0, 1)) == Coordinates(1, 1))
    assert(move(HareBot, West, Coordinates(0, 1)) == Coordinates(3, 1))
  }

  test("`move` in the `South` direction should move south") {
    assert(move(TurtleBot, South, InitialCoordinates) == Coordinates(0, -1))
    assert(move(TurtleBot, South, Coordinates(0, 1)) == Coordinates(0, 0))
    assert(move(HareBot, South, Coordinates(0, 1)) == Coordinates(0, -2))
  }

  test("`move` in the `East` direction should move east") {
    assert(move(TurtleBot, East, InitialCoordinates) == Coordinates(-1, 0))
    assert(move(TurtleBot, East, Coordinates(0, 1)) == Coordinates(-1, 1))
    assert(move(HareBot, East, Coordinates(0, 1)) == Coordinates(-3, 1))
  }
}