import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.Assertion
import CustomUnapplyTask._

class CustomUnapplyTaskSpec extends AnyFunSuite {
  def checkRgbDesctructuring(color: RGB, alpha: Int, red: Int, green: Int, blue: Int): Assertion = {
    assert(color match
      case RGB(a, r, g, b) =>
        a == alpha && r == red && g == green && b == blue)
  }

  test("An RGB colour should be correctly destructured with unapply") {
    checkRgbDesctructuring(RGB(0x4E7DFA31), 0x4E, 0x7D, 0xFA, 0x31)
    checkRgbDesctructuring(RGB(0x9A2B0C4F), 0x9A, 0x2B, 0x0C, 0x4F)
    checkRgbDesctructuring(RGB(0xC8E95A76), 0xC8, 0xE9, 0x5A, 0x76)
    checkRgbDesctructuring(RGB(0x5F813D2A), 0x5F, 0x81, 0x3D, 0x2A)
    checkRgbDesctructuring(RGB(0xA46BC39E), 0xA4, 0x6B, 0xC3, 0x9E)
    checkRgbDesctructuring(RGB(0x71FD8E62), 0x71, 0xFD, 0x8E, 0x62)
    checkRgbDesctructuring(RGB(0xD07A5E98), 0xD0, 0x7A, 0x5E, 0x98)
    checkRgbDesctructuring(RGB(0x38B691FD), 0x38, 0xB6, 0x91, 0xFD)
    checkRgbDesctructuring(RGB(0xE2D4A78C), 0xE2, 0xD4, 0xA7, 0x8C)
    checkRgbDesctructuring(RGB(0x1B593AF7), 0x1B, 0x59, 0x3A, 0xF7)
  }
}











