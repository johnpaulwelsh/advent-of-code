package src.aoc2022.main;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    static ArrayList<String> brackets = new ArrayList<>(Arrays.asList("[","]"));
    static ArrayList<String> digits = new ArrayList<>(Arrays.asList("0","1","2","3","4","5","6","7","8","9"));

    static class Instruction {
        private final int count;
        private final int sourceStack;
        private final int destinationStack;

        public Instruction(int count, int sourceStack, int destinationStack) {
            this.count = count;
            this.sourceStack = sourceStack;
            this.destinationStack = destinationStack;
        }

        public String toString() {
            return "count="+count+",sourceStack="+sourceStack+",destinationStack"+destinationStack;
        }
    }

    // make every row the same length, right-padding with spaces as needed
    public static void makeAllRowsEqualLength(List<List<String>> stackDiagram) {
        int maxColumnCount = 0;
        for (List<String> row : stackDiagram) {
            maxColumnCount = Math.max(maxColumnCount, row.size());
        }

        for (List<String> row : stackDiagram) {
            while (row.size() < maxColumnCount) {
                row.add(" ");
            }
        }
    }

    public static List<List<String>> transposeDiagram(List<List<String>> stackDiagram) {
        // reverse everything to put the column numbers on top instead of on bottom
        Collections.reverse(stackDiagram);

        makeAllRowsEqualLength(stackDiagram);

        //https://stackoverflow.com/questions/5190419/transposing-values-in-java-2d-arraylist
        // flip it on its side (transpose)
        int rowLength = stackDiagram.size();
        int columnLength = stackDiagram.get(0).size();
        String[][] transposedDiagram = new String[columnLength][rowLength];

        for(int i = 0; i < rowLength; i++) {
            for(int j = 0; j < columnLength; j++) {
                transposedDiagram[j][i] = stackDiagram.get(i).get(j);
            }
        }

        List<List<String>> transposedUnfiltered = new ArrayList<>();
        for (String[] strings : transposedDiagram) {
            ArrayList<String> innerList = new ArrayList<>(Arrays.asList(strings));
            transposedUnfiltered.add(innerList);
        }

        // get rid of the rows that are entirely spaces
        // and get rid of the rows that contain brackets
        // (not every element will contain a bracket because of the space regulation above)
        List<List<String>> transposedFiltered = transposedUnfiltered.stream()
                .filter(row -> row.stream().noneMatch(brackets::contains))
                .filter(row -> row.stream().anyMatch(digits::contains))
                .collect(Collectors.toList());

        System.out.println("filtered...");
        for (List<String> row : transposedFiltered) {
            System.out.println(String.join("", row));
        }

        return transposedFiltered;
    }

    public static void removeSpacesFromTopOfStacks(List<Stack<String>> stacks) {
        for (Stack<String> stack : stacks) {
            while (stack.peek().equals(" ")) {
                stack.pop();
            }
//            System.out.println("stack = " + String.join("", stack));
        }
    }

    public static void followInstructions9000(List<Stack<String>> stacks, List<Instruction> instructions) {
        for (Instruction inst : instructions) {
            Stack<String> sourceStack = stacks.get(inst.sourceStack-1);
            Stack<String> destStack = stacks.get(inst.destinationStack-1);
            for (int i = 0; i < inst.count; i++) {
                destStack.push(sourceStack.pop());
            }
        }
    }

    public static void followInstructions9001(List<Stack<String>> stacks, List<Instruction> instructions) {
        for (Instruction inst : instructions) {
            Stack<String> sourceStack = stacks.get(inst.sourceStack-1);
            Stack<String> destStack = stacks.get(inst.destinationStack-1);
            Stack<String> multiGrab = new Stack<>();
            for (int i = 0; i < inst.count; i++) {
                multiGrab.push(sourceStack.pop());
            }
            for (int i = 0; i < inst.count; i++) {
                destStack.push(multiGrab.pop());
            }
        }
    }

    public static void main(String[] args) {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day5input.txt");

        List<List<String>> stackDiagram = new ArrayList<>();

        int i = 0;
        while (i < inputList.size() && !inputList.get(i).isEmpty()) {
            String[] split = inputList.get(i).split("");
            ArrayList<String> ls = new ArrayList<>(Arrays.asList(split));
            stackDiagram.add(ls);
            i++;
        }

        List<List<String>> transposedDiagram = transposeDiagram(stackDiagram);

        // Move past the blank line that separates diagram from instructions
        inputList = inputList.subList(i+1, inputList.size());

        // collect instructions
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

        List<Stack<String>> stacks = new ArrayList<>();
        for (List<String> row : transposedDiagram) {
            Stack<String> currStack = new Stack<>();
            for (String elem : row) {
                currStack.push(elem);
            }
            stacks.add(currStack);
        }

        removeSpacesFromTopOfStacks(stacks);

//        followInstructions9000(stacks, instructionObjects);
        followInstructions9001(stacks, instructionObjects);

        String topsOfStacks = stacks.stream().map(Stack::peek).collect(Collectors.joining());

        System.out.println("answer = {" + topsOfStacks + "}");
        // pt 1 = JDTMRWCQJ
        // pt 2 = VHJDDCWRD
    }
}
