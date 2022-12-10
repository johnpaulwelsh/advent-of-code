import scala.io.Source

object Day6 {

  def combineQuestionnaireGroups(ls: List[String], separatePersons: Boolean): List[String] = {
    var outputList = List[String]()
    var groupAnswers = ""
    for (row <- ls) {
      row match {
        case "" => {
          outputList = groupAnswers :: outputList
          groupAnswers = ""
        }
        case _  => groupAnswers = groupAnswers + (if (separatePersons) " " else "") + row
      }
    }

    outputList = groupAnswers :: outputList

    outputList.map(_.stripPrefix(" "))
  }

  def countUnanimousAnswers(groupAnswers: String): Int = {
    val personAnswers = groupAnswers.split(" ")
    val groupSize = personAnswers.length

    val groupAnswersMerged = groupAnswers filterNot (_ == ' ')
    val answerFreqMap = groupAnswersMerged.groupBy(_.toChar).map{x => (x._1, x._2.length)}
    answerFreqMap.count(x => x._2 == groupSize)
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day6input.txt").getLines.toList
    val groupAnswers = combineQuestionnaireGroups(inputList, false)
    val duplicateAnswersRemoved = groupAnswers.map(_.distinct)
    val lengths = duplicateAnswersRemoved.map(_.length)
    println("Part 1 - " + lengths.sum) //7027

    val groupAnswersSeparated = combineQuestionnaireGroups(inputList, true)
    val unanimousAnswerCountList = groupAnswersSeparated.map(countUnanimousAnswers(_))
    println("Part 2 - " + unanimousAnswerCountList.sum) //3579
  }
}
