package src.aoc2022.main;

import java.util.Arrays;
import java.util.List;

public class Day6 {

    public static boolean listIsEntirelyUniqueValues(List<String> list) {
        return list.size() == list.stream().distinct().count();
    }

    public static int numCharsToProcessBeforeFindingMarker(String s, int streak) {
        List<String> split = Arrays.asList(s.split(""));
        int endOfSubStr = streak;
        while (endOfSubStr <= split.size()) {
            List<String> substring = split.subList(endOfSubStr-streak, endOfSubStr);
            if (listIsEntirelyUniqueValues(substring)) {
                break;
            }
            endOfSubStr++;
        }
        return endOfSubStr;
    }

    public static void main(String[] args) {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day6input.txt");

        if (inputList.size() > 1) {
            System.out.println("bad!");
        }

        int answer1 = numCharsToProcessBeforeFindingMarker(inputList.get(0), 4);
        System.out.println("answer 1 = " + answer1);

        int answer2 = numCharsToProcessBeforeFindingMarker(inputList.get(0), 14);
        System.out.println("answer 2 = " + answer2);

    }
}
