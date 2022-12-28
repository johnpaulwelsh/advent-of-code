package src.aoc2022.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {

    static class CpuInstructionDuringOneCycle {
        String instructionName;
        int registerAdditionValue;

        public CpuInstructionDuringOneCycle(String instructionName, int registerAdditionValue) {
            this.instructionName = instructionName;
            this.registerAdditionValue = registerAdditionValue;
        }

        public int getRegisterAdditionValue() {
            return registerAdditionValue;
        }
    }

    static final int CRT_WIDTH = 40;
    static final int CRT_HEIGHT = 6;
    static final String DARK = ".";
    static final String LIT = "#";

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

    public static List<Integer> findSignalStrengths() {
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
        return signalStrengths;
    }

    public static String[][] createAndDrawCrtScreen() {
        String[][] crtDisplay = new String[CRT_HEIGHT][CRT_WIDTH];
        for (String[] strings : crtDisplay) {
            Arrays.fill(strings, DARK);
        }

        int register = 1;
        for (int cycleCounter = 1; cycleCounter <= instructionsDuringOneCycle.size(); cycleCounter++) {
            CpuInstructionDuringOneCycle inst = instructionsDuringOneCycle.get(cycleCounter-1);
            int instAddVal = inst.getRegisterAdditionValue();

            int registerMod = register % CRT_WIDTH;
            List<Integer> pixelPositions = Arrays.asList(registerMod, registerMod-1, registerMod+1);
            int cycleCounterMod = (cycleCounter-1) % CRT_WIDTH;

            if (pixelPositions.contains(cycleCounterMod)) {
                int row = (cycleCounter-1) / CRT_WIDTH; // 0 - 5
                int column = (cycleCounter-1) % CRT_WIDTH; // 0 - 39
                crtDisplay[row][column] = LIT;
            }

            register += instAddVal;
        }

        return crtDisplay;
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day10input.txt");

        populateInstructions(list);

        List<Integer> signalStrengths = findSignalStrengths();

        System.out.println("answer 1 = " + signalStrengths.stream().mapToInt(i -> i).sum());

        System.out.println("answer 2 = ");
        String[][] crtDisplay = createAndDrawCrtScreen();
        for (String[] strings : crtDisplay) {
            System.out.println(String.join("", strings));
        }
    }
}
