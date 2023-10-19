import org.scalatest.funsuite.AnyFunSuite
import DestructuringTask._

class DestructuringTaskSpec extends AnyFunSuite {
  test("`colorDescription` should accurately describe colors") {
    assert(colorDescription(RGB(0, 0, 0, 0)) == "White")
    assert(colorDescription(RGB(1, 255, 0, 0)) == "Red")
    assert(colorDescription(RGB(7 ,0, 255, 0)) == "Green")
    assert(colorDescription(RGB(13, 0, 0, 255)) == "Blue")
    assert(colorDescription(RGB(42, 255, 255, 0)) == "Yellow")
    assert(colorDescription(RGB(255, 0, 255, 255)) == "Cyan")
    assert(colorDescription(RGB(255, 255, 0, 255)) == "Magenta")
    assert(colorDescription(RGB(255, 255, 255, 255)) == "Black")
    assert(colorDescription(RGB(255, 128, 17, 0)) == "RGB(255,128,17,0)")
    assert(colorDescription(RGB(255, 0, 191, 255)) == "RGB(255,0,191,255)")
  }
}