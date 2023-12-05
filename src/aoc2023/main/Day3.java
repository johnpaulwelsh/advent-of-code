package src.aoc2023.main;

import src.aoc2022.main.Utilities;
import src.aoc2023.main.Pojos.PartSchematic;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {

    public static final Pattern regexNumeral = Pattern.compile("\\d");

    public static int getPartNumberSubstring(List<String> lineSplit, int rangeBegin, int rangeEndExclusive) {
        return Integer.parseInt(
                lineSplit.subList(rangeBegin, rangeEndExclusive)
                        .stream()
                        .reduce("", String::concat)
        );
    }

    public static PartSchematic createPartSchematic(List<List<String>> listSplit) {
        PartSchematic ps = new PartSchematic(listSplit);

        // outer loop rows, then inner loop columns
        int row = 0;
        while(row < listSplit.size()) {
            int rangeBegin = -1;

            int col = 0;
            while(col < listSplit.get(0).size()) {
                String s = ps.get(row, col);
                // if it's a number, start a new range if needed
                if (regexNumeral.matcher(s).find()) {
                    if (rangeBegin == -1) {
                        rangeBegin = col;
                    }
                    // otherwise it's either a . or a symbol, respond the same in either case
                } else {
                    // end the range if we have one started
                    if (rangeBegin != -1) {
                        int partNum = getPartNumberSubstring(listSplit.get(row), rangeBegin, col);
                        ps.addNewPartNumber(partNum, row, rangeBegin, col);
                        rangeBegin = -1;
                    }
                }
                col++;
            }

            // as you end a row, terminate the range if we've begun one
            if (rangeBegin != -1) {
                int partNum = getPartNumberSubstring(listSplit.get(row), rangeBegin, col);
                ps.addNewPartNumber(partNum, row, rangeBegin, col);
            }

            row++;
        }
        return ps;
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2023/resources/day3input.txt");

        List<List<String>> listSplit = list.stream()
                .map(row -> Arrays.asList(row.split("")))
                .collect(Collectors.toList());

        PartSchematic ps = createPartSchematic(listSplit);

        List<PartSchematic.PartNumber> partNumbersAdjacentToSymbols = ps.getNumbersListAdjacentToSymbol();
        int partNumberSum = partNumbersAdjacentToSymbols.stream()
                        .map(PartSchematic.PartNumber::getNumber)
                        .reduce(0, Integer::sum);
        System.out.println("Answer for part 1 = " + partNumberSum);
    }
}
