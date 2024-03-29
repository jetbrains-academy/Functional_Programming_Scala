import scala.io.StdIn

object PureVsImpureTask:
  var logs: List[String] = List.empty
  def calculateAndLogImpure(data: List[Int]): Int =
    // Modifies global state
    logs ::= s"Received data of size: ${data.size}"
    val result = data.sum
    // Modifies global state
    logs ::= s"Calculated sum: $result"
    result

  def calculateAndLogPure(data: List[Int], logs: List[String]): (Int, List[String]) =
    val logMsg1 = s"Received data of size: ${data.size}"
    val result = data.sum
    val logMsg2 = s"Calculated sum: $result"
    (result, logMsg2 :: logMsg1 :: logs)