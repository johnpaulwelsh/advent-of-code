package src.aoc2023.main;

import src.aoc2022.main.Utilities;
import src.aoc2023.main.Pojos.CubeDrawGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2 {

    public static final Pattern regexGameId = Pattern.compile("Game (\\d+):");
    public static final Pattern regexRedCubeDraw = Pattern.compile("(\\d+) red");
    public static final Pattern regexBlueCubeDraw = Pattern.compile("(\\d+) blue");
    public static final Pattern regexGreenCubeDraw = Pattern.compile("(\\d+) green");

    public static List<CubeDrawGame> constructCubeDrawGameList(List<String> inputList) {
        List<CubeDrawGame> cdgList = new ArrayList<>();

        for (String row : inputList) {
            Matcher gameIdMatcher = regexGameId.matcher(row);
            int gameId = (gameIdMatcher.find()) ? Integer.parseInt(gameIdMatcher.group(1)) : 0;
            CubeDrawGame cdg = new CubeDrawGame(gameId);

            String[] splitIntoDraws = row.split(";");

            for (String draw : splitIntoDraws) {
                Matcher redDrawMatcher = regexRedCubeDraw.matcher(draw);
                Matcher blueDrawMatcher = regexBlueCubeDraw.matcher(draw);
                Matcher greenDrawMatcher = regexGreenCubeDraw.matcher(draw);

                int redBlockCount = redDrawMatcher.find() ? Integer.parseInt(redDrawMatcher.group(1)) : 0;
                int blueBlockCount = blueDrawMatcher.find() ? Integer.parseInt(blueDrawMatcher.group(1)) : 0;
                int greenBlockCount = greenDrawMatcher.find() ? Integer.parseInt(greenDrawMatcher.group(1)) : 0;

                cdg.addNewRound(redBlockCount, blueBlockCount, greenBlockCount);
            }
            cdgList.add(cdg);
        }
        return cdgList;
    }

    public static List<CubeDrawGame> findGamesWhichAllowTheseCubeCounts(List<CubeDrawGame> cdgList, int maxRed, int maxBlue, int maxGreen) {
        return cdgList.stream()
                .filter(cdg -> cdg.maxRedsShownAtOnce() <= maxRed)
                .filter(cdg -> cdg.maxBluesShownAtOnce() <= maxBlue)
                .filter(cdg -> cdg.maxGreensShownAtOnce() <= maxGreen)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> list = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2023/resources/day2input.txt");

        List<CubeDrawGame> cdgList = constructCubeDrawGameList(list);
        List<CubeDrawGame> cdgListWhichAllowCubeCounts = findGamesWhichAllowTheseCubeCounts(cdgList, 12, 14, 13);
        int sumGameIdsWhichAllowCubeCounts = cdgListWhichAllowCubeCounts.stream()
                .map(CubeDrawGame::getGameId)
                .reduce(0, Integer::sum);
        System.out.println("Solution to part 1 = " + sumGameIdsWhichAllowCubeCounts);


    }
}
