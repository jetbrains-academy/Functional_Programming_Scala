import org.scalatest.funsuite.AnyFunSuite
import ViewTask._

class ViewTaskSpec extends AnyFunSuite {
  test("findLogMessage should find the first message with the given severity and error code") {
    val logMessages: List[String] = List(
      "Error, 1, this is an error log entry",
      "Error, 0, this is another error log entry",
      "Info, 0, this is an info log entry",
      "Fatal, 0, this is a fatal log entry")
    assert(findLogMessage(logMessages, "Error", 0).contains(" this is another error log entry"))
    assert(findLogMessage(logMessages, "Error", 1).contains(" this is an error log entry"))
    assert(findLogMessage(logMessages, "Error", 2).isEmpty)
    assert(findLogMessage(logMessages, "Fatal", 0).contains(" this is a fatal log entry"))
  }
}