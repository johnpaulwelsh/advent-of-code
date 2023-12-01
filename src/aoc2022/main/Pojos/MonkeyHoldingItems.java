package src.aoc2022.main.Pojos;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Function;

public class MonkeyHoldingItems {
    int number;
    Queue<BigInteger> items;
    Function<BigInteger, BigInteger> operation;
    Function<BigInteger, Boolean> test;
    int monkeyIfTrue;
    int monkeyIfFalse;
    BigInteger itemInspections;

    public MonkeyHoldingItems() {
        items = new ArrayDeque<>();
        itemInspections = BigInteger.ZERO;
    }

    public void addItem(BigInteger item) {
        items.add(item);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Function<BigInteger, BigInteger> getOperation() {
        return operation;
    }

    public void setOperation(Function<BigInteger, BigInteger> operation) {
        this.operation = operation;
    }

    public Function<BigInteger, Boolean> getTest() {
        return test;
    }

    public void setTest(Function<BigInteger, Boolean> test) {
        this.test = test;
    }

    public int getMonkeyIfTrue() {
        return monkeyIfTrue;
    }

    public void setMonkeyIfTrue(int monkeyIfTrue) {
        this.monkeyIfTrue = monkeyIfTrue;
    }

    public int getMonkeyIfFalse() {
        return monkeyIfFalse;
    }

    public void setMonkeyIfFalse(int monkeyIfFalse) {
        this.monkeyIfFalse = monkeyIfFalse;
    }

    public String toString() {
        return "monkey = " + number + ", items = " + items + ", inspection count = "  + itemInspections + "\n";
    }

    public BigInteger getItemInspections() {
        return itemInspections;
    }

    public void incrementItemInspections() {
        this.itemInspections = this.itemInspections.add(BigInteger.ONE);
    }

    public Queue<BigInteger> getItems() {
        return items;
    }
}
