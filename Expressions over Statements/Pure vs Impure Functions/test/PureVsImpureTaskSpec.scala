import org.scalatest.funsuite.AnyFunSuite
import PureVsImpureTask._

class PureVsImpureTaskSpec extends AnyFunSuite {
  test("calculateAndLogPure should give the same result as the impure version") {
    val logsBefore = logs
    val inputList1 = List(1,2,3)
    val inputList2 = List(4,5)
    val resultImpure1 = calculateAndLogImpure(inputList1)
    val logsImpure1 = logs
    val resultImpure2 = calculateAndLogImpure(inputList2)
    val logsImpure2 = logs

    val (resultPure1, logsPure1) = calculateAndLogPure(inputList1, logsBefore)
    val (resultPure2, logsPure2) = calculateAndLogPure(inputList2, logsImpure1)

    assert(
      resultPure1 == resultImpure1 &&
        logsPure1 == logsImpure1
    )

    assert(
      resultPure2 == resultImpure2 &&
        logsPure2 == logsImpure2
    )
  }
}