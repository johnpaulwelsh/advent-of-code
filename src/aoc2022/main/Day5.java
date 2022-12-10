package src.aoc2022.main;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {

    static class Instruction {
        private int count;
        private int sourceStack;
        private int destinationStack;

        public Instruction(int count, int sourceStack, int destinationStack) {
            this.count = count;
            this.sourceStack = sourceStack;
            this.destinationStack = destinationStack;
        }

        public int getCount() {
            return count;
        }

        public int getSourceStack() {
            return sourceStack;
        }

        public int getDestinationStack() {
            return destinationStack;
        }

        public String toString() {
            return "count=" + count + ",sourceStack="+sourceStack+",destinationStack"+destinationStack;
        }
    }

    public static void transposeDiagram(List<List<String>> stackDiagram) {
        // reverse everything to put the column numbers on top instead of on bottom
        Collections.reverse(stackDiagram);

        System.out.println("reversed..........");
        for (List<String> row : stackDiagram) {
            System.out.println(row);
        }

        int maxColumnCount = 0;
        for (List<String> row : stackDiagram) {
            maxColumnCount = Math.max(maxColumnCount, row.size());
        }

        // make every row the same length, right-padding with spaces as needed
        for (List<String> row : stackDiagram) {
            while (row.size() < maxColumnCount) {
                row.add(" ");
            }
        }

        for (List<String> row : stackDiagram) {
            System.out.println(row);
        }

        // first row (after reversal) gives you the label of each stack
        // I happen to know it only goes up to 9 as a max, so we can just count the non-space chars


        // get rid of the columns that are entirely spaces

        // get rid of the columns with brackets


//        List<List<String>> transposedDiagram = new ArrayList<>(maxColumnCount);

    }

    public static void main(String[] args) {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day5input-sample.txt");

        List<List<String>> stackDiagram = new ArrayList<>();

        int i = 0;
        while (i < inputList.size() && !inputList.get(i).isEmpty()) {
            List<String> splitRow = Arrays.asList(inputList.get(i).split(""));
            stackDiagram.add(splitRow);
            i++;
        }

        for (List<String> row : stackDiagram) {
            System.out.println(row);
        }

        transposeDiagram(stackDiagram);

        inputList = inputList.subList(i+1, inputList.size());

        i = 0;
        List<String> instructions = new ArrayList<>();
        while (i < inputList.size()) {
            instructions.add(inputList.get(i));
            i++;
        }

        List<Instruction> instructionObjects = new ArrayList<>();
        for (String instRow : instructions) {
            String[] split = instRow.split(" ");
            int count = Integer.parseInt(split[1]);
            int sourceStack = Integer.parseInt(split[3]);
            int destinationStack = Integer.parseInt(split[5]);
            instructionObjects.add(new Instruction(count, sourceStack, destinationStack));
        }

        for (Instruction inst : instructionObjects) {
            System.out.println(inst);
        }

//        followInstructions(stacks, instructionObjects);

//        String topsOfStacks = stacks.stream().map(Stack::peek).collect(Collectors.joining());

//        System.out.println("answer 1  = " + topsOfStacks);
    }
}
