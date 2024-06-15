package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.File;

public class MkDirCommand implements Command {
    private final Directory currentDirectory;
    private final String dirName;

    public MkDirCommand(Directory currentDirectory, String dirName) {
        this.currentDirectory = currentDirectory;
        this.dirName = dirName;
    }

    @Override
    public String execute() {
        if (currentDirectory.contains(dirName)) {
            return "'" + dirName + "' directory already exists";
        }
        Directory newDir = new Directory(dirName);
        currentDirectory.add(newDir);
        return "'" + dirName + "' directory created";
    }
}