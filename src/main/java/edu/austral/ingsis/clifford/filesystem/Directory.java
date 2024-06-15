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

    public void print() {
        System.out.println("Directory: " + name);
        for (FileSystem fileSystem : elements) {
            fileSystem.print();
        }
    }
}
