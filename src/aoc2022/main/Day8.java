package src.aoc2022.main;

import src.aoc2022.main.Pojos.TreeGrid;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

public class Day8 {

    public static TreeGrid buildTreeGrid(List<String> inputList) {
        TreeGrid grid = new TreeGrid(inputList.size());
        for (int i = 0; i < inputList.size(); i++) {
            String[] split = inputList.get(i).split("");
            for (int j = 0; j < split.length; j++) {
                grid.putTree(i, j, Integer.parseInt(split[j]));
            }
        }
        return grid;
    }

    public static int countVisibleTreesFromOutside(TreeGrid grid) {
        boolean[][] visibilityGrid = grid.getVisibilityOfGridFromOutside();
        int count = 0;
        for (boolean[] row : visibilityGrid) {
            for (boolean b : row) {
                if (b) count++;
            }
        }
        return count;
    }

    //https://stackoverflow.com/questions/38286212/finding-minimum-and-maximum-in-java-2d-array
    public static int findHighestScenicScore(TreeGrid grid) {
        IntSummaryStatistics stats = Arrays.stream(grid.getScenicScoreGrid())
                .flatMapToInt(Arrays::stream)
                .summaryStatistics();
        return stats.getMax();
    }

    public static void main(String[] args) {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day8input.txt");

        TreeGrid grid = buildTreeGrid(inputList);
        System.out.println(grid);

        grid.populateVisibilityOfEachTree();
        System.out.println(grid.toStringVisibilityBooleans());

        int visibleCount = countVisibleTreesFromOutside(grid);
        System.out.println("answer 1 = " + visibleCount);

        grid.populateScenicScoresOfEachTree();
        System.out.println(grid.toStringScenicGrid());

        int highestScenicScore = findHighestScenicScore(grid);
        System.out.println("answer 2 = " + highestScenicScore);
    }
}
