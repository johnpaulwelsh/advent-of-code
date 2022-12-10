package src.aoc2022.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4 {

    public static boolean firstRangeContainsSecondRange(int[] range1, int[] range2) {
        return (range1[0] <= range2[0] && range1[1] >= range2[1]);
    }

    public static boolean firstRangeOverlapsSecondRange(int[] range1, int[] range2) {
        Set<Integer> range1Set = new HashSet<>();
        for (int i = range1[0]; i <= range1[1]; i++) {
            range1Set.add(i);
        }

        Set<Integer> range2Set = new HashSet<>();
        for (int i = range2[0]; i <= range2[1]; i++) {
            range2Set.add(i);
        }

        range1Set.retainAll(range2Set);

        return !range1Set.isEmpty();
    }

    public static void main(String[] args) {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day4input.txt");

        int count = 0;
        int count2 = 0;
        for (String line : inputList) {
            String[] lineSplit = line.split(",");
            int[] firstRange = Arrays.stream(lineSplit[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] secondRange = Arrays.stream(lineSplit[1].split("-")).mapToInt(Integer::parseInt).toArray();

            if (firstRangeContainsSecondRange(firstRange, secondRange)
                || firstRangeContainsSecondRange(secondRange, firstRange)) {
                System.out.println(Arrays.toString(firstRange) + "-" + Arrays.toString(secondRange));
                count++;
            }

            if (firstRangeOverlapsSecondRange(firstRange, secondRange)
                    || firstRangeOverlapsSecondRange(secondRange, firstRange)) {
                System.out.println(Arrays.toString(firstRange) + "-" + Arrays.toString(secondRange));
                count2++;
            }
        }

        System.out.println("answer 1 = " + count);

        System.out.println("answer 2 = " + count2);
    }
}
