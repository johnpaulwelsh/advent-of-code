import scala.io.Source

object Day5 {

  def getSeatID(boardingPass: String): Int = {
    val rowInstructions = boardingPass.take(7)
    val seatInstructions = boardingPass.drop(7)

    val rowBinary = rowInstructions.replace('F', '0').replace('B', '1')
    val rowInt = Integer.parseInt(rowBinary, 2)

    val seatBinary = seatInstructions.replace('L', '0').replace('R', '1')
    val seatInt = Integer.parseInt(seatBinary, 2)

    rowInt * 8 + seatInt
  }

  def findGapInList(ls: List[Int]): Int = {
    val ls1 = ls.sorted
    val ls2 = ls1
    val zipped = (ls1.head :: ls1) zip (ls2 :+ ls2.last)

    val neighbors = zipped.find(pair => (pair._1 - pair._2).abs == 2)
    neighbors match {
      case Some (pair) => pair._1 + 1
      case None        => -1
    }
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day5input.txt").getLines.toList
    val seatIDs = inputList.map(getSeatID(_))
    println("Part 1 - " + seatIDs.max) //951

    println("Part 2 - " + findGapInList(seatIDs)) //653
  }
}
