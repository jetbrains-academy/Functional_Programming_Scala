import scala.util.*

object Task:

  case class Result(text: String)

  val t: Try[Result] =
    Try(javaLib.getSomethingOrThrowException(data))

  t.recover {
    case ex: IOException => defaultResult
  }

  t.recoverWith {
    case ex: IOException =>
      if (ignoreErrors) Success(defaultResult)
      else Failure(ex)
  }