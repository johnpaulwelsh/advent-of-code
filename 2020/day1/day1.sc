import scala.io.Source

object Day1 {

  def productOfPairThatAddsToSum(list: List[Int], sum: Int): Option[Int] = {

    def subLoop(i: Int, j: Int, ls: List[Int]): Option[Int] = {
      if (i+j == sum)     Some(i*j)
      else if (ls == Nil) None
      else                subLoop(i, ls.head, ls.tail) // moves j down the list
    }

    if (list.length < 2) None
    else subLoop(list.head, list.tail.head, list.tail.tail) match {
      case Some(p) => Some (p)
      case None    => productOfPairThatAddsToSum(list.tail, sum) // moves i down the list
    }
  }

  def productOfTripletThatAddsToSum(list: List[Int], sum: Int): Option[Int] = {

    def outerSubLoop(x: Int, y: Int, list2: List[Int]): Option[Int] = {

      def subLoop(i: Int, j: Int, k: Int, ls: List[Int]): Option[Int] = {
        if (i+j+k == sum)     Some(i * j * k)
        else if (ls == Nil)   None
        else                  subLoop(i, j, ls.head, ls.tail) // moves k down the list
      }

      if (list2.length < 2) None
      else subLoop(x, y, list2.head, list2.tail) match {
        case Some(p) => Some (p)
        case None    => outerSubLoop(x, list2.head, list2.tail) // moves j down the list
      }
    }

    if (list.length < 3) None
    else outerSubLoop(list.head, list.tail.head, list.tail.tail) match {
      case Some(p) => Some (p)
      case None    => productOfTripletThatAddsToSum(list.tail, sum) // moves i down the list
    }
  }

  def main(args: Array[String]) {
    val sum = 2020
    val inputIterator = Source.fromFile("day1input.txt").getLines
    val inputListInt = inputIterator.map(_.toInt).toList

    productOfPairThatAddsToSum(inputListInt, sum) match {
      case Some(p) => println("Found a pair of values that add to " + sum + ". Their product is " + p)
      case None    => println("Oopsie poopsie! Pair")
    }
    //326211

    productOfTripletThatAddsToSum(inputListInt, sum) match {
      case Some(p) => println("Found a trio of values that add to " + sum + ". Their product is " + p)
      case None    => println("Oopsie poopsie! Trio")
    }
    //131347190

  }
}
