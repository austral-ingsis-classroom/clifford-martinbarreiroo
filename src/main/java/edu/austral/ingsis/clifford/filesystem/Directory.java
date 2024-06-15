package edu.austral.ingsis.clifford.filesystem;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystem {
    private final String name;
    private final List<FileSystem> elements;

    public Directory(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void add(FileSystem fileSystem) {
        elements.add(fileSystem);
    }

    public List<FileSystem> getElements() {
        return elements;
    }

    public void print() {
        System.out.println("Directory: " + name);
        for (FileSystem fileSystem : elements) {
            fileSystem.print();
        }
    }

    public Boolean contains(String name) {
        for (FileSystem fileSystem : elements) {
            if (fileSystem.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public FileSystem find (String name) {
        for (FileSystem fileSystem : elements) {
            if (fileSystem.getName().equals(name)) {
                return fileSystem;
            }
        }
        return null;
    }

    public void remove(FileSystem fileSystem) {
        elements.remove(fileSystem);
    }

    public String getPath() {
        return name;
    }
}
