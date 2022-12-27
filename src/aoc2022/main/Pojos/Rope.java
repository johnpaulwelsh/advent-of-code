package src.aoc2022.main.Pojos;

import java.util.ArrayList;
import java.util.Comparator;

public class Rope {
    ArrayList<Coordinate> ropeKnots;
    int indexOfLastKnot;

    public Rope(int knotCount, Coordinate startingPosition) {
        ropeKnots = new ArrayList<>();
        for (int i = 1; i <= knotCount; i++) {
            ropeKnots.add(new Coordinate(startingPosition.column, startingPosition.row));
        }
        indexOfLastKnot = ropeKnots.size()-1;
    }

    public Coordinate getHead() {
        return ropeKnots.get(0);
    }

    public void setHead(Coordinate c) {
        ropeKnots.set(0, c);
    }

    public Coordinate getTail() {
        return ropeKnots.get(indexOfLastKnot);
    }

    public void moveHeadInDirectionOneSpace(Direction dir) {
        Coordinate head = getHead();
        int nextCol = head.column, nextRow = head.row;

        switch (dir) {
            case L:
                nextCol = head.column - 1;
                break;
            case R:
                nextCol = head.column + 1;
                break;
            case U:
                nextRow = head.row - 1;
                break;
            case D:
                nextRow = head.row + 1;
                break;
        }
        Coordinate updatedHead = new Coordinate(nextCol, nextRow);
//        System.out.println("    moved head to position " + nextCol + "," + nextRow);
        setHead(updatedHead);
    }

    public void moveRestOfRope() {
        for (int i = 1; i < ropeKnots.size(); i++) {
            Coordinate previousKnot = ropeKnots.get(i-1);
            Coordinate currentKnot = ropeKnots.get(i);

            int prevCol = previousKnot.column, prevRow = previousKnot.row;
            int currCol = currentKnot.column, currRow = currentKnot.row;

            // if the knots are still touching, don't move this one or
            // any of the ones in the rest of the list
            if (Math.abs(prevCol - currCol) <= 1
                    && Math.abs(currRow - prevRow) <= 1) {
//                System.out.println("the rest of the rope should be touching knots, breaking out");
                return;
            }

            int nextCol = currentKnot.column, nextRow = currentKnot.row;

            nextCol = adjustColDueToDiagonalIfNeeded(prevCol, currentKnot, nextCol);
            nextRow = adjustRowDueToDiagonalIfNeeded(prevRow, currentKnot, nextRow);

            Coordinate updatedKnot = new Coordinate(nextCol, nextRow);
//            System.out.println("        moved next knot from position " + currCol + "," + currRow + " to position " + nextCol + "," + nextRow);
            ropeKnots.set(i, updatedKnot);
        }
    }

    private int adjustRowDueToDiagonalIfNeeded(int prevRow, Coordinate currentKnot, int nextRow) {
        return nextRow + Integer.compare(prevRow, currentKnot.row);
    }

    private int adjustColDueToDiagonalIfNeeded(int prevCol, Coordinate currentKnot, int nextCol) {
        return nextCol + Integer.compare(prevCol, currentKnot.column);
    }
}
