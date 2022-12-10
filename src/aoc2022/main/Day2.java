package src.aoc2022.main;

import java.util.List;

public class Day2 {

    enum Outcome {
        WIN (6),
        LOSE (0),
        DRAW (3);

        Outcome(int points) {
            this.points = points;
        }

        private final int points;
    }

    enum OpponentChoice {
        ROCK ('A'),
        PAPER ('B'),
        SCISSORS ('C');

        OpponentChoice(char c) {
            this.c = c;
        }

        private final char c;
    }

    enum YourChoice {
        ROCK ('X', 1),
        PAPER ('Y',  2),
        SCISSORS ('Z', 3);

        YourChoice(char c, int points) {
            this.c = c;
            this.points = points;
        }

        private final int points;
        private final char c;
    }

    public static Outcome determineOutcomeGivenBothMoves(char opponent, char you) {
        if (('A' == opponent && 'X' == you)
                || ('B' == opponent && 'Y' == you)
                || ('C' == opponent && 'Z' == you)) {
            return Outcome.DRAW;
        }

        if (('C' == opponent && 'X' == you)
                || ('A' == opponent && 'Y' == you)
                || ('B' == opponent && 'Z' == you)) {
            return Outcome.WIN;
        }

        return Outcome.LOSE;
    }

    public static int calculatePointsOfStrategy(List<String> strat) {
        int total = 0;

        for (String game : strat) {
            char opponent = game.charAt(0);
            char you = game.charAt(2);
            YourChoice y;

            if (YourChoice.ROCK.c == you) {
                y = YourChoice.ROCK;
            } else if (YourChoice.PAPER.c == you) {
                y = YourChoice.PAPER;
            } else {
                y = YourChoice.SCISSORS;
            }
            total += (determineOutcomeGivenBothMoves(opponent, you).points + y.points);
        }
        return total;
    }

    public static YourChoice determineYourMoveGivenOpponentAndOutcome(OpponentChoice opponent, Outcome outcome) {
        switch (opponent) {
            case ROCK:
                if (Outcome.WIN == outcome) {
                    return YourChoice.PAPER;
                } else if (Outcome.DRAW == outcome) {
                    return YourChoice.ROCK;
                } else {
                    return YourChoice.SCISSORS;
                }
            case PAPER:
                if (Outcome.WIN == outcome) {
                    return YourChoice.SCISSORS;
                } else if (Outcome.DRAW == outcome) {
                    return YourChoice.PAPER;
                } else {
                    return YourChoice.ROCK;
                }
            default:
                if (Outcome.WIN == outcome) {
                    return YourChoice.ROCK;
                } else if (Outcome.DRAW == outcome) {
                    return YourChoice.SCISSORS;
                } else {
                    return YourChoice.PAPER;
                }
        }
    }

    public static int calculatePointsOfBetterStrategy(List<String> strat) {
        int total = 0;

        for (String game : strat) {
            char opponent = game.charAt(0);
            OpponentChoice opp;
            if (OpponentChoice.ROCK.c == opponent) {
                opp = OpponentChoice.ROCK;
            } else if (OpponentChoice.PAPER.c == opponent) {
                opp = OpponentChoice.PAPER;
            } else {
                opp = OpponentChoice.SCISSORS;
            }

            char outcome = game.charAt(2);
            Outcome out;
            if ('X' == outcome) {
                out = Outcome.LOSE;
            } else if ('Y' == outcome) {
                out = Outcome.DRAW;
            } else {
                out = Outcome.WIN;
            }

            YourChoice you = determineYourMoveGivenOpponentAndOutcome(opp, out);
            total += (determineOutcomeGivenBothMoves(opponent, you.c).points + you.points);
        }
        return total;
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day2input.txt");

        System.out.println("answer 1 = " + calculatePointsOfStrategy(list));

        System.out.println("answer 2 = " + calculatePointsOfBetterStrategy(list));
    }
}
