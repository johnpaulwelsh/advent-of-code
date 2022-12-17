package src.aoc2022.main.Pojos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Folder extends Node {
    private final List<File> files;
    private final List<Folder> subfolders;
    private int sumOfAllChildElements;
    private Folder parentFolder;

    public Folder(String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.subfolders = new ArrayList<>();
        this.sumOfAllChildElements = 0;
        this.parentFolder = null;
    }

    public List<File> getFiles() {
        return files;
    }

    public void addFile(File f) {
        files.add(f);
    }

    public List<Folder> getSubfolders() {
        return subfolders;
    }

    public void addSubfolder(Folder f) {
        subfolders.add(f);
    }

    public int getSumOfAllChildElements() {
        return sumOfAllChildElements;
    }

    public void addToSumOfAllChildElements(int toAdd) {
        sumOfAllChildElements = sumOfAllChildElements + toAdd;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder f) {
        this.parentFolder = f;
    }

    public boolean containsSubfolderByName(String queryName) {
        return (subfolders.stream().anyMatch(f -> f.name.equals(queryName)));
    }

    public Folder getSubfolderByName(String queryName) {
        List<Folder> filteredSubfolders = subfolders.stream().filter(f -> f.name.equals(queryName)).collect(Collectors.toList());
        if (filteredSubfolders.size() > 1) {
            System.err.println("too many folder found, there are already duplicates!");
            return null;
        } else {
            return filteredSubfolders.get(0);
        }
    }

    public String toString() {
        return "{" + name + "} == " + sumOfAllChildElements;
    }
}
