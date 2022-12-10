import scala.io.Source
import util.control.Breaks._

object Day8 {

  def gameLoopFinalAccumulatorPartOne(instructionList: Array[String], accum: Int, instExecutedList: Array[Boolean]): Int = {
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

  def gameLoopFinalAccumulatorPartTwo(instructionList: Array[String], accum: Int, instExecutedList: Array[Boolean]): Int = {
    def loop(instList: Array[String], inst: String, pos: Int, a: Int, execList: Array[Boolean]): (Boolean, Int) = {
      if (execList(pos) == true) (false, a)
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

        if (newPos >= instList.length) (true, newA)
        else loop(instList, instList(newPos), newPos, newA, execList.updated(pos, true))
      }
    }

    var finishedAccum = 0
    breakable {
      for (x <- 0 until instructionList.length if !instructionList(x).contains("acc")) {
        val tweakedInst = instructionList(x).substring(0,3) match {
          case "jmp" => instructionList(x).replaceAll("jmp", "nop")
          case "nop" => instructionList(x).replaceAll("nop", "jmp")
          case _     => instructionList(x)
        }
        val tweakedInstList = instructionList.updated(x, tweakedInst)

        val (isFixed, terminatedAcc) = loop(tweakedInstList, tweakedInstList(0), 0, accum, instExecutedList)
        if (isFixed) {
          finishedAccum = terminatedAcc
          break
        }
      }
    }

    finishedAccum
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day8input.txt").getLines.toArray
    val instExecutedListAllFalse = Array.fill(inputList.length)(false)
    val acc = gameLoopFinalAccumulatorPartOne(inputList, 0, instExecutedListAllFalse)
    println("Part 1 - " + acc) //1134

    val acc2 = gameLoopFinalAccumulatorPartTwo(inputList, 0, instExecutedListAllFalse)
    println("Part 2 - " + acc2) //1205
  }
}
