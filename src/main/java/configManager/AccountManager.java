package configManager;

import java.io.File;
import utils.*;

public class AccountManager {
    private static String AccountID;
    private static String ConfigPath;

    public static File[] getAllAccounts() throws InvalidConfigPathException, InvalidAccountIdException {
        InputValidator.validateAccountId(AccountID, ConfigPath);

        File config = new File(ConfigPath);
        File accountPath = new File(ConfigPath, AccountID);

        if (!config.exists() || !config.isDirectory()) {
            throw new InvalidConfigPathException(
                    "Directory " + config + " doesn't exist or is not a directory");
        }

        if (!accountPath.exists() || !accountPath.isDirectory()) {
            throw new InvalidAccountIdException("Directory " + accountPath + " doesn't exist");
        }

        return config.listFiles();
    }
}
