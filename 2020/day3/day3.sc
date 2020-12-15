import scala.io.Source

object Day3 {

  def countTrees(ls: List[String], run: Int, rise: Int): BigInt = {
    var treesHit = 0
    var xCoord = 0
    var yCoord = 0

    while (yCoord < ls.length-rise) {
      yCoord = yCoord + rise
      xCoord = (xCoord + run) % ls(yCoord).length
      val currentSpot = ls(yCoord).charAt(xCoord)
      if (currentSpot == '#') treesHit+=1
    }

    BigInt(treesHit)
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day3input.txt").getLines.toList
    val treeCount = countTrees(inputList, 3, 1)
    println("Part 1 - " + treeCount) //169

    val allTreeCounts = List(countTrees(inputList, 3, 1),
                             countTrees(inputList, 1, 1),
                             countTrees(inputList, 5, 1),
                             countTrees(inputList, 7, 1),
                             countTrees(inputList, 1, 2))
    println("Part 2 - " + allTreeCounts.product) //7560370818
  }
}
