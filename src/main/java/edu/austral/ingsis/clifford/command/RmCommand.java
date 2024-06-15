package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class RmCommand implements Command {
  private final Directory currentDirectory;
  private final String name;
  private final boolean recursive;

  public RmCommand(Directory currentDirectory, String name, boolean recursive) {
    this.currentDirectory = currentDirectory;
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public String execute() {
    FileSystem target = currentDirectory.find(name);
    if (target == null) {
      return "No such file or directory: " + name;
    }

    if (target instanceof Directory && !recursive) {
      return "cannot remove '" + name + "', is a directory";
    }

    currentDirectory.remove(target);
    return "'" + name + "' removed";
  }
}
