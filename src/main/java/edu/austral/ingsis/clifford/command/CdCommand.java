package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class CdCommand implements Command {
    private Directory currentDirectory;
    private final String directoryName;

    public CdCommand(Directory currentDirectory, String directoryName) {
        this.currentDirectory = currentDirectory;
        this.directoryName = directoryName;
    }

    @Override
    public String execute() {
        for (FileSystem fileSystem : currentDirectory.getElements()) {
            if (fileSystem instanceof Directory && fileSystem.getName().equals(directoryName)) {
                currentDirectory = (Directory) fileSystem;
                break;
            }
        }
        if (currentDirectory.getName().equals(directoryName)) {
            return "moved to directory" + " " + "'" + currentDirectory.getName() + "'";
        } else {
            return "directory" + " " + "'" + directoryName + "'" + " " + "not found";
        }
    }

    public Directory getResult() {
        return currentDirectory;
    }

}
