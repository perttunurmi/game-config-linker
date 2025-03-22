package configManager;

import java.io.File;
import utils.*;

public class AccountManager {
  private static String ConfigPath = "";

  public static File[] getAllAccounts(String configPath)
      throws InvalidConfigPathException, InvalidAccountIdException {
    ConfigPath = configPath;

    final File config = new File(ConfigPath);

    if (!config.exists() || !config.isDirectory()) {
      throw new InvalidConfigPathException(
          "Directory " + config + " doesn't exist or is not a directory");
    }

    App.setAccounts(config.listFiles());
    return config.listFiles();
  }
}
