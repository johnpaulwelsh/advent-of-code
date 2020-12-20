import scala.io.Source

object Day8 {

  def gameLoopFinalAccumulator(instructionList: Array[String],
                               accum: Int,
                               instExecutedList: Array[Boolean]): Int = {
    def loop(inst: String, pos: Int, a: Int, execList: Array[Boolean]): Int = {
      if (execList(pos) == true) a
      else {
        val instSplit = inst.split(" ")
        val instStr = instSplit(0)
        val instNum = instSplit(1).toInt

        val newPos = instStr match {
          case "jmp" => pos + instNum
          case _     => pos + 1
        }

        val newA = instStr match {
          case "acc" => a + instNum
          case _     => a
        }

        loop(instructionList(newPos), newPos, newA, execList.updated(pos, true))
      }
    }

    loop(instructionList(0), 0, accum, instExecutedList)
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day8input.txt").getLines.toArray
    val instExecutedListAllFalse = Array.fill(inputList.length)(false)
    val acc = gameLoopFinalAccumulator(inputList, 0, instExecutedListAllFalse)
    println("Part 1 - " + acc) //1134
  }
}
