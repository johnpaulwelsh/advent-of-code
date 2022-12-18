package src.aoc2022.main;

import src.aoc2022.main.Pojos.Direction;
import src.aoc2022.main.Pojos.RopeGrid;

import java.util.List;

public class Day9 {

    private static RopeGrid createGridWithAppropriateDimensions(List<String> inputList) throws Exception {
        int left = 0, right = 0, up = 0, down = 0;
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

        return new RopeGrid(Math.abs(left + right), Math.abs(up + down));
    }

    private static void followInstructionsOnGrid(List<String> inputList, RopeGrid grid) throws Exception {
        for (String instruction : inputList) {
            String[] split = instruction.split(" ");
            Direction direction = Direction.valueOf(split[0]);
            int stepCount = Integer.parseInt(split[1]);
            grid.moveHeadNSpacesInDirectionAndMakeTailFollow(direction, stepCount);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day9input.txt");

        RopeGrid grid = createGridWithAppropriateDimensions(inputList);

        followInstructionsOnGrid(inputList, grid);

        System.out.println("answer 1 = " + grid.countSpacesThatTailVisited());
    }
}
