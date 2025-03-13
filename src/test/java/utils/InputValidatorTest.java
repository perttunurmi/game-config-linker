package utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

public class InputValidatorTest {

  @Test
  public void validateAccountIdException() {
    // Invalid account id
    final String accountId = "test";
    try {
      InputValidator.validateAccountId(accountId);
      assertTrue(false);
    } catch (final InvalidAccountIdException error) {
      assertTrue(true);
    }
  }

  @Test
  public void validateAccountIdNoException() {
    // Valid account id
    final String accountId = "110802326";
    try {
      InputValidator.validateAccountId(accountId);
      assertTrue(true);
    } catch (final InvalidAccountIdException error) {
      assertTrue(false);
    }
  }

  @Test
  public void validateAccountFolderNoException() {
    final String projectDir = System.getProperty("user.dir");
    final File testDir = new File(projectDir + "/src/test/resources/testfolder");

    // Directory is for testing and should always exist
    assertTrue(testDir.isDirectory());

    final String testAccountID = "73936547";

    try {
      InputValidator.validateAccountFolder(testAccountID, testDir);
      assertTrue(true);
    } catch (InvalidAccountIdException | InvalidConfigPathException error) {
      assertTrue(false);
    }
  }
}
