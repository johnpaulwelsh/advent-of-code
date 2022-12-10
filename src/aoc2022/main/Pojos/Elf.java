package src.aoc2022.main.Pojos;

import java.util.ArrayList;
import java.util.List;

public class Elf {
    private final List<Integer> foodList;

    public Elf() {
        foodList = new ArrayList<>();
    }

    public void addFood(int calories) {
        foodList.add(calories);
    }


    public int getAllCalories() {
        return foodList.stream().reduce(0, Integer::sum);
    }

    @Override
    public String toString() {
        return "foodList = " + foodList + "\n";
    }
}