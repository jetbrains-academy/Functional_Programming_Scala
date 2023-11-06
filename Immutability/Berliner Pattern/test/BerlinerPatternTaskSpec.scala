import org.scalatest.funsuite.AnyFunSuite
import berlinerPattern.BerlinerPatternTask.*

class BerlinerPatternTaskSpec extends AnyFunSuite {
  test("running the `run` script should produce Right") {
    assert(runScript() == Right(()))
  }
}