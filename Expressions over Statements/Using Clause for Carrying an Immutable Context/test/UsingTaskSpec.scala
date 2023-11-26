import org.scalatest.funsuite.AnyFunSuite
import UsingTask._

class UsingTaskSpec extends AnyFunSuite {
  test("Sort should sort strings based on the comparator provided") {
    val stringArray = Array("ca", "acc", "cc", "c", "b", "ac", "bb", "ab", "aac", "bab", "aab", "bba", "bc", "a", "aa", "abc", "cab", "ba", "cb", "aba")

    assert(UsingTask.sort(stringArray) sameElements Array("a", "aa", "aab", "aac", "ab", "aba", "abc", "ac", "acc", "b", "ba", "bab", "bb", "bba", "bc", "c", "ca", "cab", "cb", "cc"))
    assert(UsingTask.sort(stringArray)(using StringLengthComparator) sameElements Array("a", "b", "c", "aa", "ab", "ac", "ba", "bb", "bc", "ca", "cb", "cc", "aab", "aac", "aba", "abc", "acc", "bab", "bba", "cab"))
  }
}