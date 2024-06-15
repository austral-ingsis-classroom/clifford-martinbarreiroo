package edu.austral.ingsis;

import edu.austral.ingsis.clifford.command.*;
import edu.austral.ingsis.clifford.filesystem.Directory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileSystemTests {

  private final FileSystemRunner runner = commands -> List.of();

  private void executeTest(List<Map.Entry<String, String>> commandsAndResults) {
    final List<String> commands = commandsAndResults.stream().map(Map.Entry::getKey).toList();
    final List<String> expectedResult = commandsAndResults.stream().map(Map.Entry::getValue).toList();

    final List<String> actualResult = runner.executeCommands(commands);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void test1() {
    // Create a FileSystem instance
    Directory root = new Directory("/");

    // Execute the 'ls' command
    LsCommand lsCommand = new LsCommand(root);
    assertEquals("", lsCommand.execute());

    // Execute the 'mkdir horace' command
    MkDirCommand mkdirHoraceCommand = new MkDirCommand(root, "horace");
    assertEquals("'horace' directory created", mkdirHoraceCommand.execute());

    // Execute the 'ls' command

    assertEquals("horace", lsCommand.execute());

    // Execute the 'mkdir emily' command
    MkDirCommand mkdirEmilyCommand = new MkDirCommand(root, "emily");
    assertEquals("'emily' directory created", mkdirEmilyCommand.execute());

    // Execute the 'ls' command
    assertEquals("horace emily", lsCommand.execute());

    // Execute the 'ls --ord=asc' command
    LsCommand lsOrdAscCommand = new LsCommand(root, "asc");
    assertEquals("emily horace", lsOrdAscCommand.execute());
  }

  @Test
  public void test2() {
    // Create a FileSystem instance
    Directory root = new Directory("/");

    // Execute the 'mkdir horace' command
    MkDirCommand mkdirHoraceCommand = new MkDirCommand(root, "horace");

    assertEquals("'horace' directory created", mkdirHoraceCommand.execute());

    // Execute the 'mkdir emily' command
    MkDirCommand mkdirEmilyCommand = new MkDirCommand(root, "emily");

    assertEquals("'emily' directory created", mkdirEmilyCommand.execute());

    // Execute the 'mkdir jetta' command
    MkDirCommand mkdirJettaCommand = new MkDirCommand(root, "jetta");

    assertEquals("'jetta' directory created", mkdirJettaCommand.execute());

    // Execute the 'ls' command
    LsCommand lsCommand = new LsCommand(root);
    assertEquals("horace emily jetta", lsCommand.execute());

    // Execute the 'cd emily' command
    CdCommand cdEmilyCommand = new CdCommand(root, "emily");

    assertEquals("moved to directory 'emily'", cdEmilyCommand.execute());

    // Execute the 'pwd' command
    PwdCommand pwdCommand = new PwdCommand(cdEmilyCommand.getResult());

    assertEquals("/emily", pwdCommand.execute());

    // Execute the 'touch elizabeth.txt' command
    TouchCommand touchElizabethCommand = new TouchCommand(cdEmilyCommand.getResult(), "elizabeth.txt");

    assertEquals("'elizabeth.txt' file created", touchElizabethCommand.execute());

    // Execute the 'mkdir t-bone' command
    MkDirCommand mkdirTBoneCommand = new MkDirCommand(cdEmilyCommand.getResult(), "t-bone");

    assertEquals("'t-bone' directory created", mkdirTBoneCommand.execute());

    // Execute the 'ls' command
    lsCommand = new LsCommand(cdEmilyCommand.getResult());
    assertEquals("elizabeth.txt t-bone", lsCommand.execute());
  }

  @Test
  public void test3() {
    // Create a FileSystem instance
    Directory root = new Directory("/");

    // Execute the 'mkdir horace' command
    MkDirCommand mkdirHoraceCommand = new MkDirCommand(root, "horace");

    assertEquals("'horace' directory created", mkdirHoraceCommand.execute());

    // Execute the 'mkdir emily' command
    MkDirCommand mkdirEmilyCommand = new MkDirCommand(root, "emily");

    assertEquals("'emily' directory created", mkdirEmilyCommand.execute());

    // Execute the 'mkdir jetta' command
    MkDirCommand mkdirJettaCommand = new MkDirCommand(root, "jetta");

    assertEquals("'jetta' directory created", mkdirJettaCommand.execute());

    // Execute the 'cd emily' command
    CdCommand cdEmilyCommand = new CdCommand(root, "emily");
    assertEquals("moved to directory 'emily'", cdEmilyCommand.execute());

    // Execute the 'touch elizabeth.txt' command
    TouchCommand touchElizabethCommand = new TouchCommand(cdEmilyCommand.getResult(), "elizabeth.txt");

    assertEquals("'elizabeth.txt' file created", touchElizabethCommand.execute());

    // Execute the 'mkdir t-bone' command
    MkDirCommand mkdirTBoneCommand = new MkDirCommand(cdEmilyCommand.getResult(), "t-bone");

    assertEquals("'t-bone' directory created", mkdirTBoneCommand.execute());

    // Execute the 'touch elizabeth.txt' command

    assertEquals("'elizabeth.txt' file already exists", touchElizabethCommand.execute());

    // Execute the 'ls' command
    LsCommand lsCommand = new LsCommand(cdEmilyCommand.getResult());
    assertEquals("elizabeth.txt t-bone", lsCommand.execute());

    // Execute the 'rm' command
    RmCommand rmCommand = new RmCommand(cdEmilyCommand.getResult(), "t-bone", false);

    assertEquals("cannot remove 't-bone', is a directory", rmCommand.execute());

    // Execute the 'rm --recursive t-bone' command
    RmCommand rmRecursiveCommand = new RmCommand(cdEmilyCommand.getResult(), "t-bone", true);

    assertEquals("'t-bone' removed", rmRecursiveCommand.execute());

    // Execute the 'ls' command
    assertEquals("elizabeth.txt", lsCommand.execute());

    // Execute the 'rm elizabeth.txt' command
    rmCommand = new RmCommand(cdEmilyCommand.getResult(), "elizabeth.txt", false);

    assertEquals("'elizabeth.txt' removed", rmCommand.execute());

    // Execute the 'ls' command
    assertEquals("", lsCommand.execute());
  }

  @Test
  void test4() {
    executeTest(List.of(
            entry("mkdir horace", "'horace' directory created"),
            entry("mkdir emily", "'emily' directory created"),
            entry("cd horace", "moved to directory 'horace'"),
            entry("mkdir jetta", "'jetta' directory created"),
            entry("cd ..", "moved to directory '/'"),
            entry("cd horace/jetta", "moved to directory 'jetta'"),
            entry("pwd", "/horace/jetta"),
            entry("cd /", "moved to directory '/'")
    ));
  }

  @Test
  void test5() {
    executeTest(List.of(
            entry("mkdir emily", "'emily' directory created"),
            entry("cd horace", "'horace' directory does not exist")
    ));
  }

  @Test
  void test6() {
    executeTest(List.of(
            entry("cd ..", "moved to directory '/'")
    ));
  }

  @Test
  void test7() {
    executeTest(List.of(
            entry("mkdir horace", "'horace' directory created"),
            entry("cd horace", "moved to directory 'horace'"),
            entry("touch emily.txt", "'emily.txt' file created"),
            entry("touch jetta.txt", "'jetta.txt' file created"),
            entry("ls", "emily.txt jetta.txt"),
            entry("rm emily.txt", "'emily.txt' removed"),
            entry("ls", "jetta.txt")
    ));
  }

  @Test
  void test8() {
    executeTest(List.of(
            entry("mkdir emily", "'emily' directory created"),
            entry("cd emily", "moved to directory 'emily'"),
            entry("mkdir emily", "'emily' directory created"),
            entry("touch horace.txt", "'horace.txt' file created"),
            entry("touch jetta.txt", "'jetta.txt' file created"),
            entry("ls", "emily emily.txt jetta.txt"),
            entry("rm --recursive emily", "'emily' removed"),
            entry("ls", "emily.txt jetta.txt"),
            entry("ls --ord=desc", "jetta.txt emily.txt")
    ));
  }
}
