package src.aoc2023.main;

import src.aoc2022.main.Utilities;
import src.aoc2023.main.Pojos.ScratchCard;

import java.util.*;
import java.util.stream.Collectors;

public class Day4 {

    public static double determinePointMultiplier(int matchingNumbers) {
        if (matchingNumbers == 0) return 0;
        return Math.pow(2, matchingNumbers-1);
    }

    public static List<ScratchCard> generateOriginalScratchCardList(List<String> inputList) {
        List<ScratchCard> scratchCards = new ArrayList<>();
        for (String row : inputList) {
            String[] gameNumAndTheRest = row.split(":");
            int gameNum = Integer.parseInt(gameNumAndTheRest[0].trim().split("\\s+")[1]);
            String[] winningAndYourNumbers = gameNumAndTheRest[1].trim().split("\\|");
            List<Integer> winningNumbers = Arrays.stream(winningAndYourNumbers[0].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
            List<Integer> yourNumbers    = Arrays.stream(winningAndYourNumbers[1].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());

            scratchCards.add(new ScratchCard(gameNum, winningNumbers, yourNumbers));
        }
        return scratchCards;
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2023/resources/day4input-sample.txt");

        List<ScratchCard> scratchCards = generateOriginalScratchCardList(list);

        // PART 1

        Map<Integer, Integer> winningNumbersPerCard = new HashMap<>();
        for (ScratchCard scratchCard : scratchCards) {
            Set<Integer> winningNumbersSet = new HashSet<>(scratchCard.getWinningNumbers());
            Set<Integer> yourNumbersSet = new HashSet<>(scratchCard.getYourNumbers());
            Set<Integer> intersection = new HashSet<>(winningNumbersSet);
            intersection.retainAll(yourNumbersSet);
            winningNumbersPerCard.put(scratchCard.getGameNumber(), intersection.size());
        }

        double sum = winningNumbersPerCard.values().stream()
                .map(Day4::determinePointMultiplier)
                .reduce(0.0, Double::sum);
        System.out.println("Answer for part 1 = " + sum);

        // PART 2

        for (Map.Entry<Integer, Integer> e : winningNumbersPerCard.entrySet()) {
            int gameNumber = e.getKey();
            int wins = e.getValue();
            for (int i = gameNumber+1; i <= gameNumber+wins; i++) {
                System.out.println("Game " + gameNumber + " had " + wins +
                        " winning numbers, so incrementing copies of card " + i);
                // you gain wins per card including copies you already won
                scratchCards.get(i).incrementCopiesOfThisCardHeld();
            }
        }

        double cardCount = scratchCards.stream()
                .map(ScratchCard::getCopiesOfThisCardHeld)
                .reduce(0, Integer::sum);
        System.out.println("Answer for part 2 = " + cardCount);
    }
}
