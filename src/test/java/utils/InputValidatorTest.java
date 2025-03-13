package utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.FileSystems;

import org.junit.jupiter.api.Test;

public class InputValidatorTest {

    @Test
    public void validateAccountIdException() {
        // Invalid account id
        String accountId = "test";
        try {
            InputValidator.validateAccountId(accountId);
            assertTrue(false);
        } catch (InvalidAccountIdException error) {
            assertTrue(true);
        }
    }

    @Test
    public void validateAccountIdNoException() {
        // Valid account id
        String accountId = "110802326";
        try {
            InputValidator.validateAccountId(accountId);
            assertTrue(true);
        } catch (InvalidAccountIdException error) {
            assertTrue(false);
        }
    }

    @Test
    public void validateAccountFolderNoException() {
        String projectDir = System.getProperty("user.dir");
        File testDir = new File(projectDir + "/src/test/resources/testfolder");
        System.out.println(testDir);

        // Directory is for testing and should always exist
        assertTrue(testDir.isDirectory());

        String testAccountID = "73936547";

        try {
            InputValidator.validateAccountFolder(testAccountID, testDir);
            assertTrue(true);
        } catch (InvalidAccountIdException | InvalidConfigPathException error) {
            assertTrue(false);
        }
    }
}
