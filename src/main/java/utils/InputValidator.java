package utils;

import java.io.File;

/**
 * Class to store
 */
public final class InputValidator {
    public static void validateAccountId(String accountId) throws InvalidAccountIdException {
        if (!accountId.matches("^[0-9]+")) {
            throw new InvalidAccountIdException("AccountID can only contain numbers");
        }

    }

    public static void validateAccountFolder(String accountId, String configpath)
            throws InvalidAccountIdException, InvalidConfigPathException {

        validateAccountId(accountId);
        validateConfigPath(configpath);

        File accountConfigFolder = new File(configpath, accountId);

        if (!accountConfigFolder.isDirectory()) {
            throw new InvalidAccountIdException(
                    "Directory " + accountConfigFolder.getAbsolutePath() + " doesn't exist");
        }

    }

    public static void validateAccountFolder(String accountId, File configpath)
            throws InvalidAccountIdException, InvalidConfigPathException {

        validateAccountId(accountId);
        validateConfigPath(configpath);

        File accountConfigFolder = new File(configpath, accountId);

        if (!accountConfigFolder.isDirectory()) {
            throw new InvalidAccountIdException(
                    "Directory " + accountConfigFolder.getAbsolutePath() + " doesn't exist");
        }

    }

    public static void validateConfigPath(String configPath) throws InvalidConfigPathException {
        File userdata = new File(configPath);
        if (!userdata.isDirectory()) {
            throw new InvalidConfigPathException(
                    "Directory " + userdata.getAbsolutePath() + " doesn't exist");
        }
    }

    public static void validateConfigPath(File configPath) throws InvalidConfigPathException {
        File userdata = configPath;
        if (!userdata.isDirectory()) {
            throw new InvalidConfigPathException(
                    "Directory " + userdata.getAbsolutePath() + " doesn't exist");
        }
    }
}
