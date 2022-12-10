package src.aoc2022.main;

import src.aoc2022.main.Pojos.Elf;

import java.util.*;
import java.util.stream.Collectors;

public class Day1 {
    public static void populateElves(List<String> list, List<Elf> elves) {
        Elf elf = new Elf();
        for (String food : list) {
            if (food.equals("")) {
                elves.add(elf);
                elf = new Elf();
            } else {
                elf.addFood(Integer.parseInt(food));
            }
        }
    }

    public static int findSumOfTopNElvesByCalorieCount(int n, List<Elf> elves) {
        List<Integer> sortedList = elves.stream()
                .map(Elf::getAllCalories)
                .sorted((e1, e2) -> Integer.compare(e2, e1))
                .collect(Collectors.toList());
        List<Integer> topList = sortedList.subList(0, n);
        System.out.println(topList);
        return topList.stream()
                .mapToInt(i -> i)
                .sum();
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day1input.txt");

        List<Elf> elves = new ArrayList<>();

        populateElves(list, elves);

        int maxCalories = findSumOfTopNElvesByCalorieCount(1, elves);
        System.out.println("answer 1 = " + maxCalories);

        int caloriesForTopThree = findSumOfTopNElvesByCalorieCount(3, elves);
        System.out.println("answer 2 = " + caloriesForTopThree);
    }
}