package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LsCommand implements Command {
  private final Directory currentDirectory;
  private final String order;

  public LsCommand(Directory currentDirectory, String order) {
    this.currentDirectory = currentDirectory;
    this.order = order;
  }

  public LsCommand(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
    this.order = null;
  }

  @Override
  public String execute() {
    List<FileSystem> elements = currentDirectory.getElements();
    if (order != null) {
      Comparator<FileSystem> comparator = Comparator.comparing(FileSystem::getName);
      if (order.equals("desc")) {
        comparator = comparator.reversed();
      }
      elements = elements.stream().sorted(comparator).collect(Collectors.toList());
    }
    if (elements.isEmpty()) {
      return "";
    } else {
      return (elements.stream().map(FileSystem::getName).collect(Collectors.joining(" ")));
    }
  }
}
