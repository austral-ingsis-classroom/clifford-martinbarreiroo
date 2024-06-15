package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.File;

public class TouchCommand implements Command {
    private final Directory currentDirectory;
    private final String fileName;

    public TouchCommand(Directory currentDirectory, String fileName) {
        this.currentDirectory = currentDirectory;
        this.fileName = fileName;
    }

    @Override
    public String execute() {
        if (currentDirectory.contains(fileName)) {
            return "'" + fileName + "' file already exists";
        }
        File newFile = new File(fileName);
        currentDirectory.add(newFile);
        return "'" + fileName + "' file created";
    }
}