object ViewTask:
  def findLogMessage(messages: List[String], severity: String, errorCode: Int): Option[String] =
    val filtered =
      messages.view
        .map(_.split(','))
        .filter(x => x.length == 3 && x(0).trim == severity.trim)
        .map(x => (x(1).trim.toInt, x(2)))
        .filter((code, msg) => code == errorCode)
    if filtered.isEmpty then None
    else Some(filtered.head._2)

  @main
  def main() =
    val logMessages: List[String] = List(
      "Error, 1, this is an error log entry",
      "Error, 0, this is another error log entry",
      "Info, 0, this is an info log entry",
      "Fatal, 0, this is a fatal log entry")
    println(findLogMessage(logMessages, "Error", 0)) // Some( this is another error log entry)
    println(findLogMessage(logMessages, "Error", 1)) // Some( this is an error log entry)
    println(findLogMessage(logMessages, "Error", 2)) // None
    println(findLogMessage(logMessages, "Fatal", 0)) // Some( this is a fatal log entry)

