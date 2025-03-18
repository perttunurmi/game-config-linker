package configManager;

import java.io.File;
import utils.*;

public class AccountManager {
  private static String AccountID = "";
  private static String ConfigPath = "";

  public static File[] getAllAccounts(String mainAccountID, String configPath)
      throws InvalidConfigPathException, InvalidAccountIdException {
    AccountID = mainAccountID;
    ConfigPath = configPath;
    InputValidator.validateAccountFolder(AccountID, ConfigPath);

    final File config = new File(ConfigPath);
    final File accountPath = new File(ConfigPath, AccountID);

    if (!config.exists() || !config.isDirectory()) {
      throw new InvalidConfigPathException(
          "Directory " + config + " doesn't exist or is not a directory");
    }

    if (!accountPath.exists() || !accountPath.isDirectory()) {
      throw new InvalidAccountIdException("Directory " + accountPath + " doesn't exist");
    }

    App.setAccounts(config.listFiles());
    return config.listFiles();
  }
}
