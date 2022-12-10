import scala.io.Source

object Day10 {

  def getJoltDifferenceMap(ls: List[Int]): Map[Int, Int] = {
    val sortedInputList = ls.map(_.toInt).sorted
    val builtInVoltage = sortedInputList.last + 3
    val adjacentPairZip = (sortedInputList :+ builtInVoltage).zip(0 :: sortedInputList)
    val voltageDiffs = adjacentPairZip.map(p => (p._1 - p._2).abs)
    val voltageDiffDistribMap = voltageDiffs.groupBy(x => x).map(p => (p._1, p._2.length))
    voltageDiffDistribMap
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day10input.txt").getLines.toList.map(_.toInt)
    val joltDiffMap = getJoltDifferenceMap(inputList)
    val oneJoltsTimesThreeJolts = joltDiffMap.getOrElse(1, 0) * joltDiffMap.getOrElse(3, 0)
    println("Part 1 - " + oneJoltsTimesThreeJolts) //2343
  }
}
