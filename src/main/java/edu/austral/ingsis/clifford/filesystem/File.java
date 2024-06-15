package edu.austral.ingsis.clifford.filesystem;

public class File implements FileSystem {
  private final String name;

  public File(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void print() {
    System.out.println("File: " + name);
  }
}
