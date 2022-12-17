package src.aoc2022.main.Pojos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeGrid {
    private final int[][] grid;
    private final boolean[][] visibilityOfGridFromOutside;
    private final int[][] scenicScoreGrid;
    private final int sideLength;

    public TreeGrid(int sideLength) {
        this.sideLength = sideLength;
        grid = new int[sideLength][sideLength];
        visibilityOfGridFromOutside = new boolean[sideLength][sideLength];
        scenicScoreGrid = new int[sideLength][sideLength];
        populateEmptyVisibilityGrid();
    }

    private void populateEmptyVisibilityGrid() {
        for (boolean[] rows : visibilityOfGridFromOutside) {
            Arrays.fill(rows, false);
        }
    }

    public boolean determineVisibilityOfTree(int currentRow, int currentColumn, int treeHeight) {
        if (currentRow == 0 || currentRow == sideLength-1
                || currentColumn == 0 || currentColumn == sideLength-1) {
            return true;
        }

        List<Boolean> visibilities = new ArrayList<>();

        boolean isVisibleFromLeft = true;
        for (int c = 0; c < currentColumn; c++) {
            if (getTree(currentRow, c) >= treeHeight) {
                isVisibleFromLeft = false;
                break;
            }
        }

        visibilities.add(isVisibleFromLeft);

        boolean isVisibleFromAbove = true;
        for (int r = 0; r < currentRow; r++) {
            if (getTree(r, currentColumn) >= treeHeight) {
                isVisibleFromAbove = false;
                break;
            }
        }

        visibilities.add(isVisibleFromAbove);

        boolean isVisibleFromRight = true;
        for (int x = sideLength-1; x > currentColumn; x--) {
            if (getTree(currentRow, x) >= treeHeight) {
                isVisibleFromRight = false;
                break;
            }
        }

        visibilities.add(isVisibleFromRight);

        boolean isVisibleFromBelow = true;
        for (int y = sideLength-1; y > currentRow; y--) {
            if (getTree(y, currentColumn) >= treeHeight) {
                isVisibleFromBelow = false;
                break;
            }
        }

        visibilities.add(isVisibleFromBelow);

        return visibilities.stream().anyMatch(b -> b);
    }

    public int determineScenicScoreOfTree(int currentRow, int currentColumn, int treeHeight) {
        if (currentRow == 0 || currentRow == sideLength-1
                || currentColumn == 0 || currentColumn == sideLength-1) {
            return 0;
        }

        List<Integer> scenicScorePieces = new ArrayList<>();

        int scenicScoreToLeft = 0;
        for (int c = currentColumn-1; c >= 0; c--) {
            if (getTree(currentRow, c) >= treeHeight) {
                scenicScoreToLeft++;
                break;
            } else {
                scenicScoreToLeft++;
            }
        }

        scenicScorePieces.add(scenicScoreToLeft);

        int scenicScoreToAbove = 0;
        for (int r = currentRow-1; r >= 0; r--) {
            if (getTree(r, currentColumn) >= treeHeight) {
                scenicScoreToAbove++;
                break;
            } else {
                scenicScoreToAbove++;
            }
        }

        scenicScorePieces.add(scenicScoreToAbove);

        int scenicScoreToRight = 0;
        for (int c = currentColumn+1; c < sideLength; c++) {
            if (getTree(currentRow, c) >= treeHeight) {
                scenicScoreToRight++;
                break;
            } else {
                scenicScoreToRight++;
            }
        }

        scenicScorePieces.add(scenicScoreToRight);

        int scenicScoreToBelow = 0;
        for (int r = currentRow+1; r < sideLength; r++) {
            if (getTree(r, currentColumn) >= treeHeight) {
                scenicScoreToBelow++;
                break;
            } else {
                scenicScoreToBelow++;
            }
        }

        scenicScorePieces.add(scenicScoreToBelow);

        return scenicScorePieces.stream().reduce(1, (x,y) -> x*y);
    }

    public void populateVisibilityOfEachTree() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                putVisibilityBoolean(i, j, determineVisibilityOfTree(i, j, grid[i][j]));
            }
        }
    }

    public void populateScenicScoresOfEachTree() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                putScenicScore(i, j, determineScenicScoreOfTree(i, j, grid[i][j]));
            }
        }
    }

    public int getTree(int row, int col) {
        return grid[row][col];
    }

    public void putTree(int row, int col, int val) {
        grid[row][col] = val;
    }

    public void putVisibilityBoolean(int row, int col, boolean isVisible) {
        visibilityOfGridFromOutside[row][col] = isVisible;
    }

    public void putScenicScore(int row, int col, int score) {
        scenicScoreGrid[row][col] = score;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] ints : grid) {
            for (int anInt : ints) {
                stringBuilder.append(anInt);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String toStringVisibilityBooleans() {
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean[] boolRow : visibilityOfGridFromOutside) {
            for (boolean b : boolRow) {
                stringBuilder.append((b) ? "T" : "F");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String toStringScenicGrid() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : scenicScoreGrid) {
            for (int i : row) {
                stringBuilder.append("|");
                stringBuilder.append(i);
            }
            stringBuilder.append("|");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean[][] getVisibilityOfGridFromOutside() {
        return visibilityOfGridFromOutside;
    }

    public int[][] getScenicScoreGrid() {
        return scenicScoreGrid;
    }
}
