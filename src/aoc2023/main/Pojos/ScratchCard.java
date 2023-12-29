package src.aoc2023.main.Pojos;

import java.util.List;

public class ScratchCard {
    private final int gameNumber;
    private final List<Integer> winningNumbers;
    private final List<Integer> yourNumbers;
    private int copiesOfThisCardHeld;

    public ScratchCard(int gameNumber, List<Integer> winningNumbers, List<Integer> yourNumbers) {
        this.gameNumber = gameNumber;
        this.winningNumbers = winningNumbers;
        this.yourNumbers = yourNumbers;
        this.copiesOfThisCardHeld = 1;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public List<Integer> getYourNumbers() {
        return yourNumbers;
    }

    public void incrementCopiesOfThisCardHeld() {
        copiesOfThisCardHeld++;
    }

    public int getCopiesOfThisCardHeld() {
        return copiesOfThisCardHeld;
    }

    @Override
    public String toString() {
        return "ScratchCard{" +
                "gameNumber=" + gameNumber +
                ", winningNumbers=" + winningNumbers +
                ", yourNumbers=" + yourNumbers +
                ", copiesOfThisCardHeld=" + copiesOfThisCardHeld +
                '}';
    }
}
