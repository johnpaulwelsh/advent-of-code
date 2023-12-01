package src.aoc2023.main;

import src.aoc2022.main.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {

    public static final Pattern regex = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|[0-9])");

    public static int getCalibrationFromRow(String rowText) {
        Stream<Character> characterStream = rowText.codePoints().mapToObj(c -> (char) c);
        List<Character> digits = characterStream
                .filter(Character::isDigit)
                .collect(Collectors.toList());
        char digit1 = digits.get(0);
        char digit2 = digits.get(digits.size() - 1);
        return Integer.parseInt((digit1 + String.valueOf(digit2)));
    }

    public static int getCalibrationFromRowWithTextDigits(String rowText) {
        Matcher m = regex.matcher(rowText);
        List<String> numStrList = new ArrayList<>();
        //https://stackoverflow.com/questions/49197125/find-all-matches-of-a-regex-pattern-in-java-even-overlapping-ones
        if(m.find()) {
            do {
                numStrList.add(m.group());
            } while(m.find(m.start()+1));
        }
        //end stolen code
        List<Integer> numList = numStrList.stream()
                .map(Day1::translateNumber)
                .collect(Collectors.toList());
        int digit1 = numList.get(0);
        int digit2 = numList.get(numList.size() - 1);
        return Integer.parseInt((digit1 + String.valueOf(digit2)));
    }

    public static int translateNumber(String numStr) {
        switch (numStr) {
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
            default:
                return Integer.parseInt(numStr);
        }
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2023/resources/day1input.txt");

        List<Integer> calibrationList = list.stream()
                .map(Day1::getCalibrationFromRow)
                .collect(Collectors.toList());
        System.out.println("Part 1 answer = " + calibrationList.stream().reduce(0, Integer::sum));


        List<Integer> calibrationList2 = list.stream()
                .map(Day1::getCalibrationFromRowWithTextDigits)
                .collect(Collectors.toList());
        System.out.println("Part 2 answer = " + calibrationList2.stream().reduce(0, Integer::sum));
    }
}
