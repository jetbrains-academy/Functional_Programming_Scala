import scala.annotation.tailrec

object TailRecursionTask:

  def reverseList[A](xs: List[A]): List[A] =
    @tailrec
    def go(xs: List[A], acc: List[A]): List[A] =
      xs match
        case Nil => acc
        case x :: xs => go(xs, x :: acc)
    go(xs, List.empty)

  def sumOfDigits(n: Int): Int =
    @tailrec
    def go(n: Int, acc: Int): Int =
      if n > 0 then
        go(n/10, acc + n % 10)
      else
        acc
    go(n, 0)

  @main
  def main(): Unit =
    val list = List(1,2,3,4)
    println(s"${reverseList(list).mkString(",")}") // 4,3,2,1
    println(s"${sumOfDigits(1234)}") // 10
    println(s"${sumOfDigits(9078)}") // 24


