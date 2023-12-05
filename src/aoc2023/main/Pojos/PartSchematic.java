package src.aoc2023.main.Pojos;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PartSchematic {
    private final List<List<String>> schematicGrid;
    private final List<PartNumber> partNumbersList;
    public static final Pattern regexSymbol = Pattern.compile("[^A-Za-z0-9.]");

    public static class PartNumber {
        private final int number;
        private final int row;
        private final int beginCol;
        private final int endColExclusive;

        public PartNumber(int number, int row, int beginCol, int endColExclusive) {
            this.number = number;
            this.row = row;
            this.beginCol = beginCol;
            this.endColExclusive = endColExclusive;
        }

        public int getNumber() {
            return number;
        }

        public int getRow() {
            return row;
        }

        public int getBeginCol() {
            return beginCol;
        }

        public int getEndColExclusive() {
            return endColExclusive;
        }
    }

    public PartSchematic(List<List<String>> schematicGrid) {
        this.schematicGrid = schematicGrid;
        partNumbersList = new ArrayList<>();
    }

    public String get(int row, int col) {
        return schematicGrid.get(row).get(col);
    }

    public void addNewPartNumber(int number, int row, int beginCol, int endColExclusive) {
        partNumbersList.add(new PartNumber(number, row, beginCol, endColExclusive));
    }

    public List<PartNumber> getNumbersListAdjacentToSymbol() {
        return partNumbersList.stream()
                .filter(this::isPartNumberAdjacentToSymbol)
                .collect(Collectors.toList());
    }

    public boolean isPartNumberAdjacentToSymbol(PartNumber pn) {
        // create a box of column and row ranges, and loop through every member of the box looking for a symbol
        int rowTop    = (pn.row-1 < 0)                                       ? pn.row : pn.row-1;
        int rowBottom = (pn.row+1 > schematicGrid.size())                    ? pn.row : pn.row+1;
        int colLeft   = (pn.beginCol-1 < 0)                                  ? pn.beginCol : pn.beginCol-1;
        int colRight  = (pn.endColExclusive+1 > schematicGrid.get(0).size()) ? pn.endColExclusive : pn.endColExclusive+1;

        for (int row = rowTop; row <= rowBottom; row++) {
            for (int col = colLeft; col < colRight; col++) { // remember we're using exclusive column ends!
                try {
                    if (regexSymbol.matcher(this.get(row, col)).find()) {
                        System.out.println("Found a symbol at position (row=" + row + ",col" + col + "), therefore " + pn.getNumber() + " is a real part number");
                        return true;
                    }
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    System.err.println("(row=" + row + ",col" + col + ")");
                }
            }
        }

        return false;
    }
}
