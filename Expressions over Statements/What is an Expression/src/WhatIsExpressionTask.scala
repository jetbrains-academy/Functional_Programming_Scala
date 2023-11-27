object WhatIsExpressionTask:
  def abs(n: Int): Int =
    if (n<0) then -n else n

  def concatStrings(strings: List[String]): String =
    strings.foldLeft("")((acc, str) => acc + str)

  def sumOfAbsoluteDifferences(xs: Array[Int], ys: Array[Int]): Int =
    xs.zip(ys).map { case (x, y) => abs(x - y) }.sum


  def longestCommonPrefix(strings: List[String]): String =
    if (strings.isEmpty) ""
    else
      strings.foldLeft(strings.head) { (prefix, str) =>
        prefix.zip(str).takeWhile { case (c1, c2) => c1 == c2 }.map(_._1).mkString
      }
