object Task:

  def div(x: Double, y: Double): Option[Double] =
    if (y == 0) None
    else Some(x / y)

  @main
  def main() =
    print(div(100, 2).flatMap { div(_, 4) })

    Option(null).foreach { res => print(res) }
    Option(42).foreach { res => print(res) }
