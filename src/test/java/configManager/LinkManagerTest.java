package configManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

/** LinkManagerTest */
public class LinkManagerTest {
  private final String projectDir = System.getProperty("user.dir");
  private final File testDir = new File(projectDir + "/src/test/resources");

  public void isSymbolicLinkTestFileMissing() {
    final File file = new File(testDir, "testlink");
    assertTrue(!LinkManager.isSymbolicLink(file));
  }

  @Test
  public void createLinkTest() {
    final File src = new File(testDir, "testfile");
    final File target = new File(testDir, "testlink");
    LinkManager.createLink(src, target);
    isSymbolicLinkTestFileExists();
    removeLinkTest();
  }

  public void isSymbolicLinkTestFileExists() {
    final File file = new File(testDir, "testlink");
    assertTrue(LinkManager.isSymbolicLink(file));
  }

  @Test
  public void removeLinkTest() {
    final File file = new File(testDir, "testlink");
    LinkManager.removeLink(file);
    isSymbolicLinkTestFileMissing();
  }
}
