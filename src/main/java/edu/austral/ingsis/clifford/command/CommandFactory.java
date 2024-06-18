package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;

public class CommandFactory {
  private Directory currentDirectory;

  public CommandFactory(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  public void setCurrentDirectory(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  public Command createCommand(String commandString) {
    String[] parts = commandString.split(" ");
    String commandName = parts[0];
    switch (commandName) {
      case "ls":
        if (parts.length > 1 && parts[1].startsWith("--ord=")) {
          String order = parts[1].substring(6);
          return new LsCommand(currentDirectory, order);
        } else {
          return new LsCommand(currentDirectory);
        }
      case "mkdir":
        String dirName = parts[1];
        return new MkDirCommand(currentDirectory, dirName);
      case "cd":
        String path = parts[1];
        return new CdCommand(currentDirectory, path);
      case "touch":
        String fileName = parts[1];
        return new TouchCommand(currentDirectory, fileName);
      case "rm":
        if (parts.length > 1) {
          boolean recursive = parts[1].equals("--recursive");
          String targetName = recursive ? parts[2] : parts[1];
          return new RmCommand(currentDirectory, targetName, recursive);
        } else {
          return new RmCommand(currentDirectory, null, false);
        }
      case "pwd":
        return new PwdCommand(currentDirectory);
      default:
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }
  }
}
