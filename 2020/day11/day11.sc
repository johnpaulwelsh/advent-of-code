import scala.io.Source

object Day11 {

  def populateMultiDimensionalArray(rows: Int, cols: Int, a: Array[String]): Array[Array[Char]] = {
    var outputGrid = Array.ofDim[Char](rows, cols)
    for (i <- 0 until rows) {
      for (j <- 0 until cols) {
        outputGrid(i)(j) = a(i).charAt(j)
      }
    }
    outputGrid
  }

  def findAdjacentSeat(adjX: Int, adjY: Int, a: Array[Array[Char]]): Char = {
    val lowBoundX = 0
    val highBoundX = a.length
    val lowBoundY = 0
    val highBoundY = a(0).length
    if (adjX < lowBoundX || adjY < lowBoundY ||
        adjX >= highBoundX || adjY >= highBoundY) 'x'
    else                                          a(adjX)(adjY)
  }

  def getTupleList(i: Int, j: Int): List[Tuple2[Int, Int]] = {
    List(Tuple2(i - 1, j - 1),
         Tuple2(i - 1, j    ),
         Tuple2(i - 1, j + 1),
         Tuple2(i    , j - 1),
         Tuple2(i    , j + 1),
         Tuple2(i + 1, j - 1),
         Tuple2(i + 1, j    ),
         Tuple2(i + 1, j + 1))
  }

  def findVisibleSeatIdxs(i: Int, j: Int, a: Array[Array[Char]]): List[Tuple2[Int, Int]] = {
    //
  }

  def getFinalOccupiedSeats(arr: Array[Array[Char]]): Int = {

    def adjacentsRulesPart1(i: Int, j: Int, a: Array[Array[Char]]): Char = {
      val adjSeatIdxs = getTupleList(i, j)
      val adjSeats = adjSeatIdxs.map(p => findAdjacentSeat(p._1, p._2, a))
      val currSeat = a(i)(j)
      val surroundingFilledSeats = adjSeats.filter(_ == '#')

      if (currSeat == 'L' && (surroundingFilledSeats).size == 0)      '#'
      else if (currSeat == '#' && (surroundingFilledSeats).size >= 4) 'L'
      else                                                            currSeat
    }

    def adjacentsRulesPart2(i: Int, j: Int, a: Array[Array[Char]]): Char = {
      // val adjSeatIdxs = getTupleList(i, j)
      val adjSeats = adjSeatIdxs.map(p => findAdjacentSeat(p._1, p._2, a))
      val currSeat = a(i)(j)
      val surroundingFilledSeats = adjSeats.filter(_ == '#')

      if (currSeat == 'L' && (surroundingFilledSeats).size == 0)      '#'
      else if (currSeat == '#' && (surroundingFilledSeats).size >= 5) 'L'
      else                                                            currSeat
    }

    def applyRules(grid: Array[Array[Char]]): Array[Array[Char]] = {
      val rowLength = grid(0).length
      (for (i <- 0 until grid.length; j <- 0 until rowLength)
        // yield adjacentsRulesPart1(i, j, grid)
        yield adjacentsRulesPart2(i, j, grid)
      ).toArray.grouped(rowLength).toArray
    }

    val modifiedGrid = applyRules(arr)

    val doAllRowsMatch = for (i <- 0 until modifiedGrid.length) yield modifiedGrid(i) sameElements arr(i)

    if (doAllRowsMatch.forall(_ == true)) {
      (for (i <- 0 until modifiedGrid.length) yield modifiedGrid(i).count(_ == '#')).sum
    } else {
      getFinalOccupiedSeats(modifiedGrid)
    }
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day11input.txt").getLines.toArray
    val rows = inputList.length
    val cols = inputList(0).length

    val grid = populateMultiDimensionalArray(rows, cols, inputList)
    println("Part 1 - " + getFinalOccupiedSeats(grid))
  }
}
