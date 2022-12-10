package src.aoc2022.main;

import java.util.*;
import java.util.stream.Collectors;

public class Day3 {

    public static Map<Character, Integer> priorityMap = new HashMap<>();
    public static int LOWERCASE_A_ASCII = 97;
    public static int LOWERCASE_Z_ASCII = 122;
    public static int UPPERCASE_A_ASCII = 65;
    public static int UPPERCASE_Z_ASCII = 90;

    static {
        for (int i = LOWERCASE_A_ASCII; i <= LOWERCASE_Z_ASCII; i++) {
            priorityMap.put((char) i, i - LOWERCASE_A_ASCII + 1);
        }

        for (int j = UPPERCASE_A_ASCII; j <= UPPERCASE_Z_ASCII; j++) {
            priorityMap.put((char) j, j - UPPERCASE_A_ASCII + 26 + 1);
        }
    }

    public static List<Integer> findPrioritiesOfCommonElementsFromKnapsackCompartments(List<String> inputList) {
        List<Integer> priorityListOfCommonElements = new ArrayList<>();

        for (String line : inputList) {
            int middle = line.length() / 2;
            String firstHalf = line.substring(0, middle);
            String secondHalf = line.substring(middle);

            Set<Character> firstHalfSet = firstHalf
                    .chars()
                    .mapToObj(e -> (char) e)
                    .collect(Collectors.toSet());

            Set<Character> secondHalfSet = secondHalf
                    .chars()
                    .mapToObj(e -> (char) e)
                    .collect(Collectors.toSet());

            Set<Character> intersection = new HashSet<>(firstHalfSet);

            intersection.retainAll(secondHalfSet);

            for (Character c : intersection) {
                int priority = priorityMap.getOrDefault(c, 0);
                System.out.println(c + " -> " + priority);
                priorityListOfCommonElements.add(priority);
            }
        }

        return priorityListOfCommonElements;
    }

    public static List<Integer> findPrioritiesOfCommonElementsFromEveryThreeKnapsacks(List<String> inputList) throws Exception {
        if (inputList.size() % 3 != 0) {
            throw new Exception("Not divisible by three!");
        }

        List<Integer> priorityListOfCommonElements = new ArrayList<>();

        Set<Character> elfGroupItemSet = inputList.get(0).chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toSet());

        for (int i = 0; i < inputList.size(); i++) {
            Set<Character> tempElfGroupItemSet = inputList.get(i)
                    .chars()
                    .mapToObj(e -> (char) e)
                    .collect(Collectors.toSet());

            if (elfGroupItemSet.isEmpty()) {
                elfGroupItemSet = new HashSet<>(tempElfGroupItemSet);
            }

            elfGroupItemSet.retainAll(tempElfGroupItemSet);

            System.out.println("elf group so far in set form = " + Arrays.toString(elfGroupItemSet.toArray()));

            if (i % 3 == 2) {
                System.out.println("elf group is finished......");
                for (Character c : elfGroupItemSet) {
                    int priority = priorityMap.getOrDefault(c, 0);
                    System.out.println(c + " -> " + priority);
                    priorityListOfCommonElements.add(priority);
                }
                elfGroupItemSet.clear();
            }
        }

        return priorityListOfCommonElements;
    }

    public static void main(String[] args) throws Exception {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day3input.txt");

        List<Integer> priorityListOfCommonElements = findPrioritiesOfCommonElementsFromKnapsackCompartments(inputList);

        int sum = priorityListOfCommonElements.stream()
                .mapToInt(i -> i)
                .sum();

        System.out.println("answer 1 = " + sum);

        List<Integer> priorityListOfCommonElementsByGroup = findPrioritiesOfCommonElementsFromEveryThreeKnapsacks(inputList);

        int sum2 = priorityListOfCommonElementsByGroup.stream()
                .mapToInt(i -> i)
                .sum();

        System.out.println("answer 2 = " + sum2);
    }
}
