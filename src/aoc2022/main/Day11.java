package src.aoc2022.main;

import src.aoc2022.main.Pojos.MonkeyHoldingItems;
import src.aoc2022.main.Pojos.HighToLowMonkeyComparator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 {

    public static List<MonkeyHoldingItems> parseInputIntoStartingState(List<String> list) {
        List<MonkeyHoldingItems> monkeys = new ArrayList<>();
        MonkeyHoldingItems monkey = null;
        for (String row : list) {

            if (row.contains("Monkey")) {
                monkey = new MonkeyHoldingItems();
                String[] rowSplit = row.split(" ");
                String number = rowSplit[1].substring(0, rowSplit[1].length()-1);
                monkey.setNumber(Integer.parseInt(number));

            } else {
                String trim = row.substring(row.lastIndexOf(':') + 1).trim();
                if (row.contains("Starting items")) {
                    String[] split = trim.split(",");
                    for (String s : split) {
                        assert monkey != null;
                        monkey.addItem(new BigInteger(s.trim()));
                    }

                } else if (row.contains("Operation")) {
                    String[] operationSplit = trim.split(" ");
                    String operator = operationSplit[3];
                    String secondTerm = operationSplit[4];
                    Function<BigInteger, BigInteger> operation;

                    if (secondTerm.equals("old")) {
                        operation = a -> a.pow(2);
                    } else if (operator.equals("*")) {
                        operation = a -> a.multiply(new BigInteger(secondTerm));
                    } else {
                        operation = a -> a.add(new BigInteger(secondTerm));
                    }

                    assert monkey != null;
                    monkey.setOperation(operation);

                } else if (row.contains("Test")) {
                    String[] testSplit = trim.split(" ");
                    BigInteger divisor = new BigInteger(testSplit[testSplit.length-1]);
                    assert monkey != null;
                    monkey.setTest(a -> (a.mod(divisor).equals(BigInteger.ZERO)));

                } else if (row.contains("If true")) {
                    String[] rowSplit = row.split(" ");
                    assert monkey != null;
                    monkey.setMonkeyIfTrue(Integer.parseInt(rowSplit[rowSplit.length-1]));

                } else if (row.contains("If false")) {
                    String[] rowSplit = row.split(" ");
                    assert monkey != null;
                    monkey.setMonkeyIfFalse(Integer.parseInt(rowSplit[rowSplit.length-1]));

                } else {
                    // blank row, so finish with this monkey
                    monkeys.add(monkey);
                }
            }
        }
        // do this one last time because the input doesn't end in a blank row
        monkeys.add(monkey);
        return monkeys;
    }

    public static void goThroughMonkeysForNRounds(List<MonkeyHoldingItems> monkeys, int n, boolean worryDividesByThree) {
        int count = 0;
        while (count < n) {

            do {
                MonkeyHoldingItems m = monkeys.get(0);
//                System.out.println("Monkey " + m.getNumber());
                Queue<BigInteger> itemsThisMonkeyHolds = m.getItems();
                while (!itemsThisMonkeyHolds.isEmpty()) {
                    BigInteger firstItem = itemsThisMonkeyHolds.remove();
//                    System.out.println("  Inspecting " + firstItem);
                    firstItem = m.getOperation().apply(firstItem);
//                    System.out.println("  Worry level becomes " + firstItem);
                    m.incrementItemInspections();
                    if (worryDividesByThree)
                        firstItem = firstItem.divide(new BigInteger("3"));
//                    System.out.println("  Monkey gets bored with item, worry level becomes " + firstItem);
                    boolean testResult = m.getTest().apply(firstItem);
                    int nextMonkeyNum = (testResult) ? m.getMonkeyIfTrue() : m.getMonkeyIfFalse();
//                    System.out.println("  Worry level divisible? " + testResult);
                    MonkeyHoldingItems monkeyBeingThrownTo = monkeys.stream()
                            .filter(monk -> monk.getNumber() == nextMonkeyNum)
                            .collect(Collectors.toList())
                            .get(0);
                    monkeyBeingThrownTo.addItem(firstItem);
//                    System.out.println("  Item with worry level " + firstItem + " thrown to " + monkeyBeingThrownTo);
                }
                monkeys.add(monkeys.remove(0));
            } while (monkeys.get(0).getNumber() != 0);
            count++;
        }
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day11input-sample.txt");

        HighToLowMonkeyComparator htlmc = new HighToLowMonkeyComparator();

        List<MonkeyHoldingItems> monkeys = parseInputIntoStartingState(list);

        goThroughMonkeysForNRounds(monkeys, 20, true);

        List<MonkeyHoldingItems> sortedMonkeysByHighestInteractions = monkeys.stream()
                .sorted(htlmc)
                .collect(Collectors.toList());

        System.out.println("Sorted...");
        System.out.println(sortedMonkeysByHighestInteractions);

        BigInteger monkeyBusinessProduct =
                sortedMonkeysByHighestInteractions.get(0).getItemInspections().multiply(sortedMonkeysByHighestInteractions.get(1).getItemInspections());

        System.out.println("answer 1 = " + monkeyBusinessProduct);

        //-------------------------------------------------

        List<MonkeyHoldingItems> monkeys2 = parseInputIntoStartingState(list);

        goThroughMonkeysForNRounds(monkeys2, 10000, false);

        List<MonkeyHoldingItems> sortedMonkeysByHighestInteractions2 = monkeys2.stream()
                .sorted(htlmc)
                .collect(Collectors.toList());

        System.out.println("Sorted...");
        System.out.println(sortedMonkeysByHighestInteractions2);

        BigInteger monkeyBusinessProduct2 =
                sortedMonkeysByHighestInteractions2.get(0).getItemInspections().multiply(sortedMonkeysByHighestInteractions2.get(1).getItemInspections());

        System.out.println("answer 2 = " + monkeyBusinessProduct2);
        // 1520320008 too low
        // 2002342176 too low
    }
}
