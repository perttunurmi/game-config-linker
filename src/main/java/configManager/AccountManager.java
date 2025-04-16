package configManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import utils.*;

public class AccountManager {

  /*
   * Find all subdirectories(accounts) in the userdata folder
   */
  public static File[] getAllAccounts(final String configPath)
      throws InvalidConfigPathException, InvalidAccountIdException {

    final File config = new File(configPath);

    if (!config.exists() || !config.isDirectory()) {
      throw new InvalidConfigPathException(
          "Directory " + config + " doesn't exist or is not a directory");
    }

    final List<File> accounts = new ArrayList<File>();
    for (final File account : config.listFiles()) {
      if (!account.toString().contains(".")) {
        accounts.add(account);
      }
    }

    final File[] accArr = new File[accounts.size()];
    accounts.toArray(accArr);

    App.setAccounts(accArr);
    return accArr;
  }

  /*
   * Predicts the main account by checking which account has the most config folders
   */
  public static void predictMainAccount() {
    if (App.getAccounts().length > 0) {
      final File[] accounts = App.getAccounts();
      File mostLikelyMainAccount = accounts[0];

      for (final File account : accounts) {
        if (account.list().length > mostLikelyMainAccount.list().length) {
          mostLikelyMainAccount = account;
        }
      }
      App.setAccountID(mostLikelyMainAccount.getName());
    } else {
      System.err.println("Accounts are not yet set or steam/userdata folder is empty");
    }
  }
}
