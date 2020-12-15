import scala.io.Source

object Day4 {

  class Passport(val byr: String, val iyr: String, val eyr: String, val hgt: String, val hcl: String, val ecl: String, val pid: String)

  def isValidPassportPartOne(s: String): Boolean = {
    s.contains("byr:") &&
    s.contains("iyr:") &&
    s.contains("eyr:") &&
    s.contains("hgt:") &&
    s.contains("hcl:") &&
    s.contains("ecl:") &&
    s.contains("pid:")
  }

  def combinePassportRows(ls: List[String]): List[String] = {
    var outputList = List[String]()
    var passport = ""
    for (row <- ls) {
      row match {
        case "" => {
          outputList = passport :: outputList
          passport = ""
        }
        case _  => passport = passport + " " + row
      }
    }

    outputList = passport :: outputList

    outputList
  }

  def parsePassport(pspt: String): Passport = {
    val split = pspt.split(" ")
    val byr = split.find(_.contains("byr")).getOrElse("BLANK").dropWhile(_ != ':').drop(1)
    val iyr = split.find(_.contains("iyr")).getOrElse("BLANK").dropWhile(_ != ':').drop(1)
    val eyr = split.find(_.contains("eyr")).getOrElse("BLANK").dropWhile(_ != ':').drop(1)
    val hgt = split.find(_.contains("hgt")).getOrElse("BLANK").dropWhile(_ != ':').drop(1)
    val hcl = split.find(_.contains("hcl")).getOrElse("BLANK").dropWhile(_ != ':').drop(1)
    val ecl = split.find(_.contains("ecl")).getOrElse("BLANK").dropWhile(_ != ':').drop(1)
    val pid = split.find(_.contains("pid")).getOrElse("BLANK").dropWhile(_ != ':').drop(1)
    // println(byr + "__" + iyr + "__" + eyr + "__" + hgt + "__" + hcl + "__" + ecl + "__" + pid)
    new Passport(byr, iyr, eyr, hgt, hcl, ecl, pid)
  }


  /**
  byr (Birth Year) - four digits; at least 1920 and at most 2002.
  iyr (Issue Year) - four digits; at least 2010 and at most 2020.
  eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
  hgt (Height) - a number followed by either cm or in:
    If cm, the number must be at least 150 and at most 193.
    If in, the number must be at least 59 and at most 76.
  hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
  ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
  pid (Passport ID) - a nine-digit number, including leading zeroes.
  cid (Country ID) - ignored, missing or not.
  */
  def parseNum(s: String): Int = {
    try{
      s.toInt
    } catch {
      case e: Exception => -1
    }
  }

  def validHeight(s: String): Boolean = {
    val heightPattern = "([0-9]+)(cm|in)".r

    s match {
      case heightPattern(num, unit) =>
        (unit == "cm" && (105 to 193).contains(parseNum(num))) ||
        (unit == "in" && (59 to 76).contains(parseNum(num)))
      case _ => false
    }
  }

  def validHairColor(s: String): Boolean = s.matches("#[0-9a-f]{6}")

  def validEyeColor(s: String): Boolean = List("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(s)

  def validPassportId(s: String): Boolean = s.matches("[0-9]{9}")

  def isValidPassportPartTwo(p: Passport): Boolean = {
    (1920 to 2002).contains(parseNum(p.byr)) &&
    (2010 to 2020).contains(parseNum(p.iyr)) &&
    (2020 to 2030).contains(parseNum(p.eyr)) &&
    validHeight(p.hgt) &&
    validHairColor(p.hcl) &&
    validEyeColor(p.ecl) &&
    validPassportId(p.pid)
  }

  def main(args: Array[String]) {
    val inputList = Source.fromFile("day4input.txt").getLines.toList

    val passportList = combinePassportRows(inputList) //260

    val isValidListPartOne = passportList.map(isValidPassportPartOne(_))
    println("Part 1 = " + isValidListPartOne.count(_ == true)) //222


    val validPassportList = passportList.filter(isValidPassportPartOne(_) == true)
    val passportObjList = validPassportList.map(parsePassport(_))
    val isValidListPartTwo = passportObjList.map(isValidPassportPartTwo(_))
    println("Part 2 = " + isValidListPartTwo.count(_ == true)) //???
  }
}
