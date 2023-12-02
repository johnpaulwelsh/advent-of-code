package src.aoc2023.main.Pojos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CubeDrawGame {
    private final int gameId;
    private final List<CubeDrawRound> roundsOfGame;

    private static class CubeDrawRound {
        private int numRed;
        private int numBlue;
        private int numGreen;

        public CubeDrawRound(int numRed, int numBlue, int numGreen) {
            this.numRed = numRed;
            this.numBlue = numBlue;
            this.numGreen = numGreen;
        }

        public int getNumRed() {
            return numRed;
        }

        public void setNumRed(int numRed) {
            this.numRed = numRed;
        }

        public int getNumBlue() {
            return numBlue;
        }

        public void setNumBlue(int numBlue) {
            this.numBlue = numBlue;
        }

        public int getNumGreen() {
            return numGreen;
        }

        public void setNumGreen(int numGreen) {
            this.numGreen = numGreen;
        }

        @Override
        public String toString() {
            return "CubeDrawRound{" +
                    "numRed=" + numRed +
                    ", numBlue=" + numBlue +
                    ", numGreen=" + numGreen +
                    '}';
        }
    }

    public CubeDrawGame(int gameId) {
        this.gameId = gameId;
        roundsOfGame = new ArrayList<>();
    }

    public int getGameId() {
        return gameId;
    }

    public void addNewRound(int numRed, int numBlue, int numGreen) {
        roundsOfGame.add(new CubeDrawRound(numRed, numBlue, numGreen));
    }

    public int maxRedsShownAtOnce() {
        return roundsOfGame.stream().max(Comparator.comparingInt(o -> o.numRed)).get().numRed;
    }

    public int maxBluesShownAtOnce() {
        return roundsOfGame.stream().max(Comparator.comparingInt(o -> o.numBlue)).get().numBlue;
    }

    public int maxGreensShownAtOnce() {
        return roundsOfGame.stream().max(Comparator.comparingInt(o -> o.numGreen)).get().numGreen;
    }

    @Override
    public String toString() {
        return "CubeDrawGame{" +
                "gameId=" + gameId +
                ", roundsOfGame=" + roundsOfGame +
                '}';
    }
}
