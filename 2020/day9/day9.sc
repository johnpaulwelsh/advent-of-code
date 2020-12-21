import scala.io.Source

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

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day9input.txt").getLines.toList.map(BigInt(_))
    val n = 25
    val firstInvalidNumInXMAS = findFirstInvalidNumInXMAS(inputList.take(n), n, inputList.drop(n))
    println("Part 1 - " + firstInvalidNumInXMAS) //85848519
  }
}
