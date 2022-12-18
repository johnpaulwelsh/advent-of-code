package src.aoc2022.main.Pojos;

import java.util.Arrays;

public class RopeGrid {
    static class Coordinate {
        int column;
        int row;

        public Coordinate(int column, int row) {
            this.column = column;
            this.row = row;
        }
    }

    final String TAILWASHERE = "T";
    int rowCount;
    int columnCount;
    String[][] gridShowingTailOccurrences;
    Coordinate startPosition;
    Coordinate currentHeadPosition;
    Coordinate currentTailPosition;

    public RopeGrid(int columnCount, int rowCount) throws Exception {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        gridShowingTailOccurrences = new String[columnCount][rowCount];
        populateEmptyTailOccurrenceGrid();
        startPosition = new Coordinate(columnCount / 2, rowCount / 2);
        currentHeadPosition = new Coordinate(startPosition.column, startPosition.row);
        currentTailPosition = new Coordinate(startPosition.column, startPosition.row);
        setTailOccurrence();
    }

    public void setTailOccurrence() throws Exception {

        if (currentTailPosition.column < 0 || currentTailPosition.row < 0) {
            throw new Exception("The index is out of bounds, current state of board = \n" + this);
        }
        gridShowingTailOccurrences[currentTailPosition.column][currentTailPosition.row] = TAILWASHERE;
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

    public boolean headAndTailAreAdjacent(int colDiff, int rowDiff) {
        return Math.abs(colDiff) <= 1 && Math.abs(rowDiff) <= 1;
    }

    public void moveTailAccordingToDirectionAndDistanceOfHeadMovement(Direction dir) {
        int colDiff = currentHeadPosition.column - currentTailPosition.column;
        int rowDiff = currentHeadPosition.row - currentTailPosition.row;

        if (headAndTailAreAdjacent(colDiff, rowDiff)) {
            return;
        }

        int nextTailCol = currentTailPosition.column;
        int nextTailRow = currentTailPosition.row;

        switch (dir) {
            case L:
                nextTailCol = currentHeadPosition.column + 1;
                nextTailRow = currentHeadPosition.row;
                break;
            case R:
                nextTailCol = currentHeadPosition.column - 1;
                nextTailRow = currentHeadPosition.row;
                break;
            case U:
                nextTailRow = currentHeadPosition.row + 1;
                nextTailCol = currentHeadPosition.column;
                break;
            case D:
                nextTailRow = currentHeadPosition.row - 1;
                nextTailCol = currentHeadPosition.column;
                break;
        }

        currentTailPosition = new Coordinate(nextTailCol, nextTailRow);
    }

    public void moveHeadNSpacesInDirectionAndMakeTailFollow(Direction dir, int numSpaces) throws Exception {
        int nextCol = currentHeadPosition.column, nextRow = currentHeadPosition.row;

        for (int i = 1; i <= numSpaces; i++) {
            switch (dir) {
                case L:
                    nextCol = currentHeadPosition.column - 1;
                    nextRow = currentHeadPosition.row;
                    break;
                case R:
                    nextCol = currentHeadPosition.column + 1;
                    nextRow = currentHeadPosition.row;
                    break;
                case U:
                    nextCol = currentHeadPosition.column;
                    nextRow = currentHeadPosition.row - 1;
                    break;
                case D:
                    nextCol = currentHeadPosition.column;
                    nextRow = currentHeadPosition.row + 1;
                    break;
            }

            currentHeadPosition = new Coordinate(nextCol, nextRow);

            moveTailAccordingToDirectionAndDistanceOfHeadMovement(dir);
            setTailOccurrence();
        }
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
