package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;
import java.util.Objects;

public class PwdCommand implements Command {
  private final Directory currentDirectory;

  public PwdCommand(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute() {
    StringBuilder path = new StringBuilder();
    Directory dir = currentDirectory;

    while (dir != null) {
      if (dir.getParent() != null && !Objects.equals(dir.getParent().getName(), "/")) {
        path.insert(0, "/" + dir.getName());
      } else {
        path.insert(0, dir.getName());
      }
      dir = dir.getParent();
    }

    return path.toString();
  }
}
