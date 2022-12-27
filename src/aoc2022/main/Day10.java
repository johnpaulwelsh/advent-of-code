package src.aoc2022.main;

import src.aoc2022.main.Pojos.CpuInstructionDuringOneCycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {

    static List<Integer> cyclesToCheck = Arrays.asList(20, 60, 100, 140, 180, 220);

    static List<CpuInstructionDuringOneCycle> instructionsDuringOneCycle = new ArrayList<>();

    public static void populateInstructions(List<String> list) {
        for (String s : list) {
            List<String> split = Arrays.asList(s.split(" "));
            String instName = split.get(0);

            instructionsDuringOneCycle.add(new CpuInstructionDuringOneCycle(instName, 0));

            if (!instName.equals("noop")) {
                int registerAdditionValue = Integer.parseInt(split.get(1));
                instructionsDuringOneCycle.add(new CpuInstructionDuringOneCycle(instName, registerAdditionValue));
            }
        }
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day10input.txt");

        populateInstructions(list);

        List<Integer> signalStrengths = new ArrayList<>();
        int register = 1;
        for (int cycleCounter = 1; cycleCounter <= instructionsDuringOneCycle.size(); cycleCounter++) {
            CpuInstructionDuringOneCycle inst = instructionsDuringOneCycle.get(cycleCounter-1);
            int instAddVal = inst.getRegisterAdditionValue();
            if (cyclesToCheck.contains(cycleCounter)) {
                signalStrengths.add(cycleCounter * register);
            }
            register += instAddVal;
        }

        System.out.println("answer 1 = " + signalStrengths.stream().mapToInt(i -> i).sum());
    }
}
