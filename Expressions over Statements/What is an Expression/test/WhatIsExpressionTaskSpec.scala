import org.scalatest.funsuite.AnyFunSuite
import WhatIsExpressionTask._

class WhatIsExpressionTaskSpec extends AnyFunSuite {
  test("`abs` should compute absolute values") {
    assert(abs(0) == 0)
    assert(abs(1) == 1)
    assert(abs(-1) == 1)
    assert(abs(-8078) == 8078)
    assert(abs(9347) == 9347)
  }

  test("`concatStrings` should concat strings") {
    assert(concatStrings(List.empty) == "")
    assert(concatStrings(List("")) == "")
    assert(concatStrings(List("a", "b", "", "c", "")) == "abc")
    assert(concatStrings(List("Hello", ", ", "expressions")) == "Hello, expressions")
  }

  test("`sumOfAbsoluteDifferences` should compute the sum of absolute differences of pairs of corresponding elements of two arrays") {
    assert(sumOfAbsoluteDifferences(Array(), Array()) == 0)
    assert(sumOfAbsoluteDifferences(Array(1,2,3), Array(1,2,3)) == 0)
    assert(sumOfAbsoluteDifferences(Array(1,2,3), Array(-1,-2,-3)) == 12)
    assert(sumOfAbsoluteDifferences(Array(-1,-2,-3), Array(1,2,3)) == 12)
  }

  test("`longestCommonPrefix` should compute the longest common prefix of the given strings") {
    assert(longestCommonPrefix(List.empty) == "")
    assert(longestCommonPrefix(List("asdkja;slf")) == "asdkja;slf")
    assert(longestCommonPrefix(List("asdkja;slf", "asdkja;slfasdkja;slf", "asdkja;slfslf")) == "asdkja;slf")
    assert(longestCommonPrefix(List("asdkja;slfasdkja;slf", "asdkja;slf", "asdkja;slfasdkja")) == "asdkja;slf")
    assert(longestCommonPrefix(List("asdkja;slf", "asdkja;slfasdkja;slf", "asdkja")) == "asdkja")
    assert(longestCommonPrefix(List("asdkja;slf", "", "asdkja;slfasdkja;slf", "asdkja")) == "")
    assert(longestCommonPrefix(List("abc", "bcd", "cde", "def")) == "")
    assert(longestCommonPrefix(List("abc", "abcd", "acde", "adef")) == "a")
  }
}