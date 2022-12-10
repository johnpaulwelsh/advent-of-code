import scala.io.Source
import util.control.Breaks._

object Day9 {

  def findFirstInvalidNumInXMAS(preamble: List[BigInt], considerPrevious: BigInt, nums: List[BigInt]): BigInt = {
    def loop(x: BigInt, precedingNums: List[BigInt], remainingNums: List[BigInt]): BigInt = {
      val validNumExists = precedingNums.exists(i =>
                             precedingNums.filterNot(_ == i).exists(j =>
                               j == (i - x).abs
                             )
                           )
      if (remainingNums.isEmpty) -1
      else if (!validNumExists)  x
      else                       loop(remainingNums.head, precedingNums.drop(1) :+ x, remainingNums.tail)
    }

    loop(nums.head, preamble, nums.tail)
  }

  def findRangeThatSumsToInvalidXMAS(invalidNum: BigInt, nums: List[BigInt]): List[BigInt] = {
    val filteredNums = nums.filter(_ < invalidNum).toArray

    var subList = Array[BigInt]()
    breakable {
      for (i <- 0 until filteredNums.length - 1) {
        breakable {
          for (j <- 0 until filteredNums.length) {
            subList = filteredNums.slice(i, j)
            if (subList.sum >= invalidNum) break
          }
        }
        if (subList.sum == invalidNum) break
      }
    }

    subList.toList
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day9input.txt").getLines.toList.map(BigInt(_))
    val n = 25
    val firstInvalidNumInXMAS = findFirstInvalidNumInXMAS(inputList.take(n), n, inputList.drop(n))
    println("Part 1 - " + firstInvalidNumInXMAS) //85848519

    val rangeThatSumsToInvalidXMAS = findRangeThatSumsToInvalidXMAS(firstInvalidNumInXMAS, inputList)
    val encryptionWeakness = rangeThatSumsToInvalidXMAS.min + rangeThatSumsToInvalidXMAS.max
    println("Part 2 - " + encryptionWeakness) //13414198
  }
}
