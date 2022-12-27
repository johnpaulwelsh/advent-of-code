package src.aoc2022.main;

import src.aoc2022.main.Pojos.Direction;
import src.aoc2022.main.Pojos.RopeGrid;

import java.util.List;

public class Day9 {

    private static RopeGrid createGridWithAppropriateDimensions(List<String> inputList, int knotCount) throws Exception {
        int left = knotCount, right = knotCount, up = knotCount, down = knotCount;
        for (String instruction : inputList) {
            String[] split = instruction.split(" ");
            Direction direction = Direction.valueOf(split[0]);
            int stepCount = Integer.parseInt(split[1]);

            switch (direction) {
                case L:
                    left += stepCount;
                    break;
                case R:
                    right += stepCount;
                    break;
                case U:
                    up += stepCount;
                    break;
                case D:
                    down += stepCount;
                    break;
            }
        }

        int columns = Math.abs(left + right);
        int rows = Math.abs(up + down);

//        System.out.println("making a grid with " + columns + " columns and " + rows + " rows");

        return new RopeGrid(columns, rows, knotCount);
    }

    private static void followInstructionsOnGrid(List<String> inputList, RopeGrid grid) throws Exception {
        for (String instruction : inputList) {
            String[] split = instruction.split(" ");
            Direction direction = Direction.valueOf(split[0]);
            int stepCount = Integer.parseInt(split[1]);
            grid.moveHeadNSpacesInDirectionAndMakeRestOfRopeFollow(direction, stepCount);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day9input.txt");

        RopeGrid grid = createGridWithAppropriateDimensions(inputList, 2);
        followInstructionsOnGrid(inputList, grid);
        System.out.println("answer 1 = " + grid.countSpacesThatTailVisited());

        RopeGrid grid2 = createGridWithAppropriateDimensions(inputList, 10);
        followInstructionsOnGrid(inputList, grid2);
        System.out.println("answer 2 = " + grid2.countSpacesThatTailVisited());
    }
}
