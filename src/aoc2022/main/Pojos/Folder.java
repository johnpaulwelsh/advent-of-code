package src.aoc2022.main.Pojos;

import java.util.ArrayList;
import java.util.List;

public class Folder extends TreeNode {
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

}
