package configManager;

import java.io.File;
import java.io.IOException;
import userInterface.cli.InteractiveMode;
import userInterface.gui.UserInterface;
import utils.InputValidator;
import utils.InvalidAccountIdException;
import utils.InvalidConfigPathException;
import utils.InvalidGameIdException;

/** - TODO: configpath, accountid and gameid */

/** Entry point for the program */
public class App {
  private static String ConfigPath = "C:\\Program Files (x86)\\Steam\\userdata";
  private static String AccountID = "";
  private static String GameID = "730"; // GameID, default 730 = csgo/cs2
  private static File[] Accounts;

  /*
   * Game config linker, use the same config on multiple accounts
   * Author: Perttu Nurmi
   * License: MIT
   * GitHub: https://github.com/perttunurmi
   * Email: perttu.nurmi@gmail.com
   * Program that links multiple Steam accounts to use the same config files
   */

  /**
   * Entry point for the program
   *
   * @param args commandline arguments
   */
  public static void main(final String[] args) {
    // boolean interactive = false;

    final String os = System.getProperty("os.name");

    if (!os.toLowerCase().contains("win")) {
      System.out.println("Sorry this program is only supported on Windows");
      while (true) {}
    }

    // if no arguments are given start automatically with gui
    if (args.length == 0) {

      final File config = new File(ConfigPath);
      if (config.isDirectory()) {

        setAccounts();
        predictMainAccount();
        System.out.println(Accounts);
      }
      UserInterface.gui();

    } else {
      // check if user gave -i or --interactive as parameters
      for (final String arg : args) {
        System.out.println(arg);
        if (arg.matches("-i") || arg.matches("--interactive")) {
          runInteractively();
        }
      }
    }
  }

  public static void backupAllConfigs() {
    for (final File account : new File(ConfigPath).listFiles()) {
      try {
        BackupManager.makeNewBackup(account);
        System.out.println("Created backup for account " + account.getAbsolutePath());
      } catch (final Exception error) {
        System.out.println("Error when creating a backup " + account.getAbsolutePath());
        continue;
      }
    }
  }

  public static void removeOldBackups() {
    BackupManager.removeOldBackups(new File(ConfigPath));
  }

  public static void linkConfigs() {
    final File mainAccountDirectory = new File(ConfigPath, AccountID);

    for (final File account : Accounts) {
      if (!account.getPath().contains(AccountID)) {
        final File gameConfigCopy = new File(account, GameID);

        // Delete existing liks and folders
        if (gameConfigCopy.exists()) {
          if (LinkManager.isSymbolicLink(gameConfigCopy)) {
            LinkManager.removeLink(gameConfigCopy);
          } else {
            try {
              BackupManager.deleteFolderRecursively(gameConfigCopy);
            } catch (final IOException error) {
              error.printStackTrace();
            }
          }
        }
        // Create the link
        LinkManager.createLink(new File(mainAccountDirectory, GameID), gameConfigCopy);
        System.out.println("Created link for account " + account.getAbsolutePath());
      }
    }
  }

  public static String getConfigPath() {
    return ConfigPath;
  }

  public static void setConfigPath(final String configPath) {
    ConfigPath = configPath;
  }

  public static String getAccountID() {
    return AccountID;
  }

  public static void setAccountID(final String accountID) {
    AccountID = accountID;
  }

  public static String getGameID() {
    return GameID;
  }

  public static void setGameID(final String gameID) {
    GameID = gameID;
  }

  public static File[] getAccounts() {
    return Accounts;
  }

  public static void setAccounts() {
    try {
      Accounts = AccountManager.getAllAccounts(ConfigPath);
    } catch (final InvalidConfigPathException error) {
      System.out.println(error.getMessage());
      System.exit(1);
    } catch (final InvalidAccountIdException error) {
      System.out.println(error.getMessage());
      System.exit(2);
    }
  }

  public static void setAccounts(final File[] accounts) {
    Accounts = accounts;
  }

  public static String[] getGameIDs() {
    final File accountDir = new File(ConfigPath, AccountID);
    if (!accountDir.exists() || !accountDir.isDirectory()) {
      return new String[0];
    }

    final File[] gameDirs = accountDir.listFiles(File::isDirectory);
    // Count how many valid game IDs we have
    int validCount = 0;
    for (final File gameDir : gameDirs) {
      if (gameDir.getName().matches("^\\d+$")) {
        validCount++;
      }
    }

    final String[] gameIDs = new String[validCount];
    int index = 0;
    for (final File gameDir : gameDirs) {
      final String name = gameDir.getName();
      if (name.matches("^\\d+$")) {
        gameIDs[index++] = name;
      }
    }
    return gameIDs;
  }

  public static void predictMainAccount() {
    AccountManager.predictMainAccount();
  }

  /**
   * Interactive mode is supposed to prompt user for input in the ideal case user will only need to
   * answer yes/no questions
   */
  private static void runInteractively() {
    InteractiveMode.interactiveMode();
  }

  private static void runValidators()
      throws InvalidAccountIdException, InvalidConfigPathException, InvalidGameIdException {
    InputValidator.validateAccountId(AccountID);

    final File configpath = new File(ConfigPath);
    InputValidator.validateAccountFolder(AccountID, ConfigPath);

    final File accountpath = new File(configpath, AccountID);
    InputValidator.validateGameId(accountpath, GameID);
  }
}
