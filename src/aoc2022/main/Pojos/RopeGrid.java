package src.aoc2022.main.Pojos;

import java.util.Arrays;

public class RopeGrid {
    final String TAILWASHERE = "T";
    int rowCount;
    int columnCount;
    String[][] gridShowingTailOccurrences;
    Coordinate startPosition;
    Rope rope;

    public RopeGrid(int columnCount, int rowCount, int knotCount) throws Exception {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        gridShowingTailOccurrences = new String[columnCount][rowCount];
        populateEmptyTailOccurrenceGrid();
        startPosition = new Coordinate(columnCount / 2, rowCount / 2);
        rope = new Rope(knotCount, startPosition);
        setTailOccurrence();
//        System.out.println("rope is of length " + rope.ropeKnots.size()
//                + " and starting on position " + startPosition.column + "," + startPosition.row);
    }

    public void setTailOccurrence() throws Exception {
        Coordinate tail = rope.getTail();
        if (tail.column < 0 || tail.row < 0
                || tail.column >= columnCount || tail.row >= rowCount) {
            throw new Exception("The tail index is out of bounds at position "
                    + "{" + tail.column + "," + tail.row + "}"
                    + ", current state of board = \n" + this);
        }

        gridShowingTailOccurrences[tail.column][tail.row] = TAILWASHERE;
    }

    private void populateEmptyTailOccurrenceGrid() {
        for (String[] rows : gridShowingTailOccurrences) {
            Arrays.fill(rows, ".");
        }
    }

    public int countSpacesThatTailVisited() {
        int count = 0;
        for (String[] row : gridShowingTailOccurrences) {
            for (String s : row) {
                if (s.equals(TAILWASHERE)) count++;
            }
        }
        return count;
    }

    public void moveHeadNSpacesInDirectionAndMakeRestOfRopeFollow(Direction dir, int numSpaces) throws Exception {
//        System.out.println("Moving head " + numSpaces + " spaces in " + dir);

        for (int i = 1; i <= numSpaces; i++) {
            rope.moveHeadInDirectionOneSpace(dir);
            rope.moveRestOfRope();
            setTailOccurrence();
        }
//        System.out.println(this);
//        System.out.println("======================");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String[] row : gridShowingTailOccurrences) {
            sb.append(String.join("", row));
            sb.append("\n");
        }
        return sb.toString();
    }
}
