package src.aoc2022.main;

import src.aoc2022.main.Pojos.File;
import src.aoc2022.main.Pojos.Folder;

import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    public static final int maxFolderSize = 100000;
    public static int totalUsedSpace;
    public static final int requiredUnusedSpace = 30000000;
    public static final String CD = "$ cd";
    public static final String LS = "$ ls";
    public static final String DIR = "dir";
    public static final String DOTDOT = "..";
    public static final String SPACE = " ";

    public static Folder constructFileSystemTree(List<String> inputList) {
        Folder root = new Folder("/");
        Folder currentFolder = root;

        for (String row : inputList) {
            if (row.contains(CD)) {
                String[] split = row.split(SPACE);
                String folderName = split[split.length - 1];

                if (folderName.equals(root.getName())) {
                    continue;

                }else if (folderName.equals(DOTDOT)) {
                    currentFolder = currentFolder.getParentFolder();

                } else if (currentFolder.containsSubfolderByName(folderName)) {
                    Folder prevCurrent = currentFolder;
                    currentFolder = currentFolder.getSubfolderByName(folderName);
                    currentFolder.setParentFolder(prevCurrent);

                } else {
                    Folder next = new Folder(folderName);
                    currentFolder.addSubfolder(next);
                    next.setParentFolder(currentFolder);
                    currentFolder = next;
                }

            } else if (row.contains(LS)) {
                continue;

            } else if (row.contains(DIR)) {
                String[] split = row.split(SPACE);
                String folderName = split[split.length-1];

                currentFolder.addSubfolder(new Folder(folderName));

            } else {
                String[] split = row.split(SPACE);
                String fileSize = split[0];
                String fileName = split[split.length-1];

                currentFolder.addFile(new File(fileName, Integer.parseInt(fileSize)));
            }

//            System.out.println(
//                row + ";\n"
//                + "current folder = " + currentFolder.getName()
//                + " => " + currentFolder.getFiles().stream().map(File::getName).collect(Collectors.joining(","))
//                + " {" + currentFolder.getSubfolders().stream().map(Folder::getName).collect(Collectors.joining("},{")) + "}"
//            );
//            System.out.println("--------------");
        }
        return root;
    }

    public static void recursivelyCalculateSizes(Folder f) {
        int sumOfChildFiles = f.getFiles().stream().map(File::getSize).mapToInt(i -> i).sum();
        f.addToSumOfAllChildElements(sumOfChildFiles);
//        System.out.println("folder {" + f.getName() + "} has sum " + sumOfChildFiles);

        if (f.getSubfolders().isEmpty()) {
//            System.out.println("at the bottom of DFS, folder {" + f.getName() + "} has sum " + sumOfChildFiles);
        } else {
            int sumOfChildFolders = 0;
            for (Folder sub : f.getSubfolders()) {
                recursivelyCalculateSizes(sub);
                sumOfChildFolders += sub.getSumOfAllChildElements();
            }
            f.addToSumOfAllChildElements(sumOfChildFolders);
//            System.out.println("after recursion unrolled, folder {" + f.getName() + "} has sum " + sumOfChildFolders);
        }
    }

    public static int collectFolderSizesUnderCertainMax(Folder f) {
        int currentFolderSum = f.getSumOfAllChildElements();
        int cappedSum = (currentFolderSum > maxFolderSize) ? 0 : currentFolderSum;

        if (f.getSubfolders().isEmpty()) {
            return cappedSum;
        } else {
            int runningSum = cappedSum;
            for (Folder sub : f.getSubfolders()) {
                runningSum += collectFolderSizesUnderCertainMax(sub);
            }
            return runningSum;
        }
    }

    public static boolean isFolderWorthDeleting(Folder f) {
        return (totalUsedSpace - f.getSumOfAllChildElements() <= requiredUnusedSpace);
    }

    public static Folder smallestSubfolderWorthDeleting(Folder currentRoot, Folder smallestThatWorks) {
        if (currentRoot.getSubfolders().isEmpty()) {
            smallestThatWorks = (isFolderWorthDeleting(currentRoot)) ? currentRoot : smallestThatWorks;
            return smallestThatWorks;
        } else {
            for (Folder sub : currentRoot.getSubfolders()) {
                smallestThatWorks = smallestSubfolderWorthDeleting(sub, smallestThatWorks);
            }
            return smallestThatWorks;
        }
    }

    public static void main(String[] args) {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day7input.txt");

        // 1. construct an object-oriented representation of the file system
        Folder root = constructFileSystemTree(inputList);

        // 2. recursively iterate down each path, collecting cumulative sizes along the way
        // and storing it in the folder's object (so object for folder A will contain the
        // sum of all files in all subfolders of A)
        recursivelyCalculateSizes(root);

        // 3. sum the folder sizes that are <= 100000
        System.out.println("answer 1 = " + collectFolderSizesUnderCertainMax(root));

        totalUsedSpace = root.getSumOfAllChildElements();

        Folder toDelete = smallestSubfolderWorthDeleting(root, root);

        System.out.println("answer 2 = "
                + toDelete.getName()
                + " with size "
                + toDelete.getSumOfAllChildElements()); // folder / too high: 47052440
    }
}
