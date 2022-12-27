package src.aoc2022.main.Pojos;

public class CpuInstructionDuringOneCycle {
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
