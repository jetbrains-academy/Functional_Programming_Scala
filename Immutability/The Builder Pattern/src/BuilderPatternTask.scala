object BuilderPatternTask:
  case class Message( sender: Option[String],
                      receiver: Option[String],
                      content: Option[String]
                    )

  class MessageBuilder():
    private var sender: Option[String] = None
    private var receiver: Option[String] = None
    private var content: Option[String] = None

    def setSender(s: String): MessageBuilder =
      sender = Some(s)
      this

    def setReceiver(r: String): MessageBuilder =
      receiver = Some(r)
      this

    def setContent(c: String): MessageBuilder =
      content = Some(c)
      this

    def build(): Message =
      Message(sender, receiver, content)
