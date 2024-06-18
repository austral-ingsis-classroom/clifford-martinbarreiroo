package edu.austral.ingsis;

import edu.austral.ingsis.clifford.command.CdCommand;
import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.filesystem.Directory;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerImpl implements FileSystemRunner {
  private final CommandFactory commandFactory;
  private Directory currentDirectory;

  public FileSystemRunnerImpl() {
    this.currentDirectory = new Directory("/");
    this.commandFactory = new CommandFactory(currentDirectory);
  }

  @Override
  public List<String> executeCommands(List<String> commands) {
    List<String> results = new ArrayList<>();
    for (String commandString : commands) {
      Command command = commandFactory.createCommand(commandString);
      String result = command.execute();
      results.add(result);
      if (command instanceof CdCommand) {
        currentDirectory = ((CdCommand) command).getResult();
        commandFactory.setCurrentDirectory(currentDirectory);
      }
    }
    return results;
  }
}
