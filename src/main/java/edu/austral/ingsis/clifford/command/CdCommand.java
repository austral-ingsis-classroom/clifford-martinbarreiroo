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
    if ("/".equals(directoryName)) {
      while (currentDirectory.getParent() != null) {
        currentDirectory = currentDirectory.getParent();
      }
    } else {
      String[] directories = directoryName.split("/");
      for (String dir : directories) {
        if ("..".equals(dir)) {
          if (currentDirectory.getParent() != null) {
            currentDirectory = currentDirectory.getParent();
          }
        } else {
          FileSystem fileSystem = currentDirectory.find(dir);
          if (fileSystem instanceof Directory) {
            currentDirectory = (Directory) fileSystem;
          } else {
            return "'" + dir + "'" + " " + "directory does not exist";
          }
        }
      }
    }
    return "moved to directory" + " " + "'" + currentDirectory.getName() + "'";
  }

  public Directory getResult() {
    return currentDirectory;
  }
}
