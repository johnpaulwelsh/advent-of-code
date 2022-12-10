package src.aoc2022.main;

import src.aoc2022.main.Pojos.File;
import src.aoc2022.main.Pojos.Folder;

import java.util.List;

public class Day7 {

    public static final int maxFolderSize = 100000;

    public static Folder constructFileSystemTree(List<String> inputList) {
        Folder root = new Folder("/");
        Folder currentFolder = root;

        for (String row : inputList) {
            if (row.contains("$ cd")) {
                String[] split = row.split(" ");
                String folderName = split[split.length - 1];

                if (folderName.equals(root.getName())) {
                    continue;
                }else if (folderName.equals("..")) {
                    currentFolder = currentFolder.getParentFolder();
                } else {
                    Folder next = new Folder(folderName);
                    currentFolder.addSubfolder(next);
                    next.setParentFolder(currentFolder);
                    currentFolder = next;
                }

            } else if (row.contains("$ ls")) {
                continue;

            } else if (row.contains("dir")) {
                String[] split = row.split(" ");
                String folderName = split[split.length-1];

                currentFolder.addSubfolder(new Folder(folderName));

            } else {
                String[] split = row.split(" ");
                String fileSize = split[0];
                String fileName = split[split.length-1];

                currentFolder.addFile(new File(fileName, Integer.parseInt(fileSize)));
            }

//            System.out.println("Current folder = " + currentFolder.getName() + ",\n"
//                + "parent folder = " + ((currentFolder.getParentFolder() != null) ? currentFolder.getParentFolder().getName() : null) + ",\n"
//                + "current folder's children files = " + currentFolder.getFiles().stream().map(File::getName).collect(Collectors.joining(",")) + ",\n"
//                + "current folder's children folders = " + currentFolder.getSubfolders().stream().map(Folder::getName).collect(Collectors.joining(","))
//            );
//            System.out.println("--------------------");
        }
        return root;
    }

    public static void recursivelyCalculateSizes(Folder f) {
        if (f.getSubfolders().isEmpty()) {
            int sum = f.getFiles().stream().map(File::getSize).mapToInt(i -> i).sum();
            f.addToSumOfAllChildElements(sum);

            if (f.getParentFolder() != null) {
                f.getParentFolder().addToSumOfAllChildElements(f.getSumOfAllChildElements());
            }

//            System.out.println("folder = {" + f.getName() + "}"
//                    + " files = [" + f.getFiles().stream()
//                    .map(File::getName)
//                    .collect(Collectors.joining(",")) + "]"
//                    + " have sum " + sum
//            );

//            if (f.getParentFolder() != null) {
//                System.out.println("    parent folder = {" + f.getParentFolder().getName() + "} "
//                        + "has sum " + f.getParentFolder().getSumOfAllChildElements());
//            }
        } else {
            int sum = f.getFiles().stream().map(File::getSize).mapToInt(i -> i).sum();
            f.addToSumOfAllChildElements(sum);
            for (Folder sub : f.getSubfolders()) {
                recursivelyCalculateSizes(sub);
            }
        }
    }

    public static int collectFolderSizesUnderCertainMax(Folder f) {
        int currentFolderSum = f.getSumOfAllChildElements();
        int cappedSum = (currentFolderSum > maxFolderSize) ? 0 : currentFolderSum;

        if (f.getSubfolders().isEmpty()) {
//            System.out.println("folder {" + f.getName() + "} has sum " + cappedSum);
            return cappedSum;
        } else {
            int runningSum = cappedSum;
            for (Folder sub : f.getSubfolders()) {
                runningSum += collectFolderSizesUnderCertainMax(sub);
            }
            return runningSum;
        }
    }

    public static void main(String[] args) {
        List<String> inputList = Utilities.readFileInList(
                "/Users/johnpaulwelsh/Documents/advent-of-code/src/aoc2022/resources/day7input.txt");

        // 1. construct an object-oriented representation of the file system
        Folder root = constructFileSystemTree(inputList);

        System.out.println("-------------------");

        // 2. recursively iterate down each path, collecting cumulative sizes along the way and storing it in the
        // folder's object (so object for folder A will contain the sum of all files in all subfolders of A)
        recursivelyCalculateSizes(root);

        System.out.println("-------------------");

        // 3. sum the folder sizes that are <= 100000
        System.out.println("answer 1 = " + collectFolderSizesUnderCertainMax(root)); //1285231, too high
    }


}
