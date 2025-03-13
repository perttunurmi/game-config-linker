package configManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

public class BackupManagerTest {
  private final String projectDir = System.getProperty("user.dir");
  private final File testDir = new File(projectDir + "/src/test/resources/testfolder");

  @Test
  public void removeBackupsTest() {

    BackupManager.removeOldBackups(testDir);

    for (final File dir : testDir.listFiles()) {
      if (dir.getAbsolutePath().endsWith(".bak")) {
        assertTrue(false);
      }
    }

    assertTrue(true);
  }

  @Test
  public void makeNewBackupTest() {
    final String accountID = "73936547";
    final File testAccountDir = new File(testDir, accountID);

    BackupManager.makeNewBackup(testAccountDir);
    assertTrue(new File(testDir, accountID + ".bak").isDirectory());
  }
}
