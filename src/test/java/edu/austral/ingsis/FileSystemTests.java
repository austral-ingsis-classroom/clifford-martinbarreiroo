package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.*;
import edu.austral.ingsis.clifford.filesystem.Directory;
import org.junit.jupiter.api.Test;

public class FileSystemTests {

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
    TouchCommand touchElizabethCommand =
        new TouchCommand(cdEmilyCommand.getResult(), "elizabeth.txt");

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
    TouchCommand touchElizabethCommand =
        new TouchCommand(cdEmilyCommand.getResult(), "elizabeth.txt");

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
    // Create a FileSystem instance
    Directory root = new Directory("/");

    // Execute the 'mkdir horace' command
    MkDirCommand mkdirHoraceCommand = new MkDirCommand(root, "horace");
    assertEquals("'horace' directory created", mkdirHoraceCommand.execute());

    // Execute the 'mkdir emily' command
    MkDirCommand mkdirEmilyCommand = new MkDirCommand(root, "emily");
    assertEquals("'emily' directory created", mkdirEmilyCommand.execute());

    // Execute the 'cd horace' command
    CdCommand cdHoraceCommand = new CdCommand(root, "horace");
    assertEquals("moved to directory 'horace'", cdHoraceCommand.execute());

    // Execute the 'mkdir jetta' command
    MkDirCommand mkdirJettaCommand = new MkDirCommand(cdHoraceCommand.getResult(), "jetta");
    assertEquals("'jetta' directory created", mkdirJettaCommand.execute());

    // Execute the 'cd ..' command
    CdCommand cdRootCommand = new CdCommand(cdHoraceCommand.getResult(), "..");
    assertEquals("moved to directory '/'", cdRootCommand.execute());

    // Execute the 'cd horace/jetta' command
    CdCommand cdJettaCommand = new CdCommand(root, "horace/jetta");
    assertEquals("moved to directory 'jetta'", cdJettaCommand.execute());

    // Execute the 'pwd' command
    PwdCommand pwdCommand = new PwdCommand(cdJettaCommand.getResult());
    assertEquals("/horace/jetta", pwdCommand.execute());

    // Execute the 'cd /' command
    CdCommand cdRootCommand2 = new CdCommand(root, "/");
    assertEquals("moved to directory '/'", cdRootCommand2.execute());
  }

  @Test
  void test5() {
    // Create a FileSystem instance
    Directory root = new Directory("/");

    // Execute the 'mkdir emily' command
    MkDirCommand mkdirEmilyCommand = new MkDirCommand(root, "emily");
    String mkdirEmilyResult = mkdirEmilyCommand.execute();
    assertEquals("'emily' directory created", mkdirEmilyResult);

    // Execute the 'cd horace' command
    CdCommand cdHoraceCommand = new CdCommand(root, "horace");
    String cdHoraceResult = cdHoraceCommand.execute();
    assertEquals("'horace' directory does not exist", cdHoraceResult);
  }

  @Test
  void test6() {
    Directory root = new Directory("/");

    CdCommand cdRootCommand = new CdCommand(root, "/");

    assertEquals("moved to directory '/'", cdRootCommand.execute());
  }

  @Test
  void test7() {
    // Create a FileSystem instance
    Directory root = new Directory("/");

    // Execute the 'mkdir horace' command
    MkDirCommand mkdirHoraceCommand = new MkDirCommand(root, "horace");
    assertEquals("'horace' directory created", mkdirHoraceCommand.execute());

    // Execute the 'cd horace' command
    CdCommand cdHoraceCommand = new CdCommand(root, "horace");
    assertEquals("moved to directory 'horace'", cdHoraceCommand.execute());

    // Execute the 'touch emily.txt' command
    TouchCommand touchEmilyCommand = new TouchCommand(cdHoraceCommand.getResult(), "emily.txt");
    assertEquals("'emily.txt' file created", touchEmilyCommand.execute());

    // Execute the 'touch jetta.txt' command
    TouchCommand touchJettaCommand = new TouchCommand(cdHoraceCommand.getResult(), "jetta.txt");
    assertEquals("'jetta.txt' file created", touchJettaCommand.execute());

    // Execute the 'ls' command
    LsCommand lsCommand = new LsCommand(cdHoraceCommand.getResult());
    assertEquals("emily.txt jetta.txt", lsCommand.execute());

    // Execute the 'rm emily.txt' command
    RmCommand rmCommand = new RmCommand(cdHoraceCommand.getResult(), "emily.txt", false);
    assertEquals("'emily.txt' removed", rmCommand.execute());

    // Execute the 'ls' command
    assertEquals("jetta.txt", lsCommand.execute());
  }

  @Test
  void test8() {
    // Create a FileSystem instance
    Directory root = new Directory("/");

    // Execute the 'mkdir emily' command
    MkDirCommand mkdirEmilyCommand = new MkDirCommand(root, "emily");
    assertEquals("'emily' directory created", mkdirEmilyCommand.execute());

    // Execute the 'cd emily' command
    CdCommand cdEmilyCommand = new CdCommand(root, "emily");
    assertEquals("moved to directory 'emily'", cdEmilyCommand.execute());

    // Execute the 'mkdir emily' command
    MkDirCommand mkdirEmilyCommand2 = new MkDirCommand(cdEmilyCommand.getResult(), "emily");
    assertEquals("'emily' directory created", mkdirEmilyCommand2.execute());

    // Execute the 'touch horace.txt' command
    TouchCommand touchHoraceCommand = new TouchCommand(cdEmilyCommand.getResult(), "horace.txt");
    assertEquals("'horace.txt' file created", touchHoraceCommand.execute());

    // Execute the 'touch jetta.txt' command
    TouchCommand touchJettaCommand = new TouchCommand(cdEmilyCommand.getResult(), "jetta.txt");
    assertEquals("'jetta.txt' file created", touchJettaCommand.execute());

    // Execute the 'ls' command
    LsCommand lsCommand = new LsCommand(cdEmilyCommand.getResult());
    assertEquals("emily horace.txt jetta.txt", lsCommand.execute());

    // Execute the 'rm --recursive emily' command
    RmCommand rmCommand = new RmCommand(cdEmilyCommand.getResult(), "emily", true);
    assertEquals("'emily' removed", rmCommand.execute());

    // Execute the 'ls' command
    assertEquals("horace.txt jetta.txt", lsCommand.execute());

    // Execute the 'ls --ord=desc' command
    LsCommand lsDescCommand = new LsCommand(cdEmilyCommand.getResult(), "desc");
    assertEquals("jetta.txt horace.txt", lsDescCommand.execute());
  }
}
