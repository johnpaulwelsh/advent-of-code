import scala.io.Source

object Day7 {

  type ContainsMap = scala.collection.mutable.Map[String, Int]
  type RuleMap     = scala.collection.mutable.Map[String, ContainsMap]

  def createBagRuleMap(ls: List[String]): RuleMap = {
    var ruleMap = scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]()
    for (rule <- ls) {
      val ruleStartPattern = """([a-z ]+) bags contain""".r
      val ruleEndPattern   = """(\d) ([a-z ]+) bags?[,.]""".r

      val containingBag = ruleStartPattern.findFirstMatchIn(rule).get.group(1)
      val bagTypesContained = ruleEndPattern.findAllMatchIn(rule)

      val containedMap = scala.collection.mutable.Map[String, Int]()
      for (b <- bagTypesContained) containedMap.put(b.group(2), b.group(1).toInt)

      ruleMap.put(containingBag, containedMap)
    }

    ruleMap
  }

  def findListOfContainersForBag(bag: String, ruleMap: RuleMap): List[String] = {
    def accum(subList: List[String], outputList: List[String]): List[String] = {
          if (subList.length == 0) outputList
          else {
            val filteredMap = ruleMap.filter(_._2.keySet.intersect(subList.toSet).nonEmpty)
            val nextBagsUp = filteredMap.keys.toList
            accum(nextBagsUp, outputList ++ nextBagsUp)
          }
    }
    accum(List(bag), List[String]())
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day7input.txt").getLines.toList
    val bagRuleMap = createBagRuleMap(inputList)
    val listOfContainersForBagType = findListOfContainersForBag("shiny gold", bagRuleMap)
    println("Part 1 - " + listOfContainersForBagType.distinct.length) //274
  }
}
