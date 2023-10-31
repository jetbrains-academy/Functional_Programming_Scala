import org.scalatest.funsuite.AnyFunSuite
import BuilderPatternTask._

class BuilderPatternTaskSpec extends AnyFunSuite {
  test("it should be possible to create messages with a builder") {
    val msg0 = MessageBuilder().setSender("alice").setReceiver("bob").setContent("msg").build()
    val msg01 = MessageBuilder().setReceiver("bob").setSender("alice").setContent("msg").build()
    val msg1 = MessageBuilder().setReceiver("bob").setContent("msg").build()
    val msg2 = MessageBuilder().setSender("alice").setContent("msg").build()
    val msg3 = MessageBuilder().setSender("alice").setReceiver("bob").build()
    val msg4 = MessageBuilder().setContent("msg").build()
    val msg5 = MessageBuilder().build()

    assert(msg0 == Message(Some("alice"), Some("bob"), Some("msg")))
    assert(msg01 == Message(Some("alice"), Some("bob"), Some("msg")))
    assert(msg1 == Message(None, Some("bob"), Some("msg")))
    assert(msg2 == Message(Some("alice"), None, Some("msg")))
    assert(msg3 == Message(Some("alice"), Some("bob"), None))
    assert(msg4 == Message(None, None, Some("msg")))
    assert(msg5 == Message(None, None, None))

  }
}