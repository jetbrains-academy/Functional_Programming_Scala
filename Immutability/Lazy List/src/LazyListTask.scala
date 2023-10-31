object LazyListTask:
  def sieve(xs: LazyList[BigInt]): LazyList[BigInt] =
    if !xs.isEmpty then
      val h = xs.head
      h #:: sieve(xs.tail).filter { x => x.mod(h) != 0 }
    else LazyList.empty

  def firstNPrimes(n: Int): LazyList[BigInt] =
    sieve(LazyList.iterate(BigInt(2)){x => x + 1}).take(n)

  @main
  def main() =
    firstNPrimes(500).foreach(println)