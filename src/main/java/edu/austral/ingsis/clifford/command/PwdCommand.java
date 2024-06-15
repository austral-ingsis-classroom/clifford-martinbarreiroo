package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;

public class PwdCommand implements Command {
    private final Directory currentDirectory;

    public PwdCommand(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute() {
        return "/" + currentDirectory.getPath();
    }

}