import scala.io.Source

object Day2 {

  def pswdIsValidPartOne(pswd: String, ch: Char, min: Int, max: Int): Boolean = {
    pswd.count(_ == ch) match {
      case x if min to max contains x => true
      case _ => false
    }
  }

  def pswdIsValidPartTwo(pswd: String, ch: Char, idx1: Int, idx2: Int): Boolean = {
    pswd.charAt(idx1-1) != pswd.charAt(idx2-1) &&
    (pswd.charAt(idx1-1) == ch ||
    pswd.charAt(idx2-1) == ch)

  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day2input.txt").getLines

    var countPartOne, countPartTwo = 0
    for (item <- inputList) {
      val itemSplit = item.replace(":", "").split(" ")
      val pswd = itemSplit(2)
      val ch = itemSplit(1).toCharArray
      val range = itemSplit(0)
      val rangeSplit = range.split("-").map(_.toInt)
      if (pswdIsValidPartOne(pswd, ch(0), rangeSplit(0), rangeSplit(1))) countPartOne+=1
      if (pswdIsValidPartTwo(pswd, ch(0), rangeSplit(0), rangeSplit(1))) countPartTwo+=1
    }
    println("Part 1 = " + countPartOne) //393
    println("Part 2 = " + countPartTwo) //690
  }
}
