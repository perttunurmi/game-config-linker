package configManager;

import java.io.File;
import java.io.IOException;
import userInterface.cli.*;
import userInterface.gui.*;
import utils.*;

/** Entry point for the program */
public class App {
  private static String ConfigPath = "C:\\Program Files (x86)\\Steam\\userdata";
  private static String AccountID =
      ""; // Steam AccountID, can be found at https://steamdb.info/calculator/
  private static String GameID = "730"; // GameID, default 730 = csgo/cs2
  private static File[] Accounts;

  /*
   * Steam game config manager
   * Author: Perttu Nurmi
   * License: MIT
   * GitHub: https://github.com/perttunurmi
   * Email: perttu.nurmi@gmail.com
   * Program that links multiple Steam accounts to use the same config files
   */
  public static void main(final String[] args) {
    if (args.length < 1) {
      UserInterface.gui();
    } else {

      InteractiveMode.interactiveMode();
      // At this point ConfigPath and AccountID should be set

      try {
        InputValidator.validateAccountId(AccountID);
      } catch (final InvalidAccountIdException error) {
        error.printStackTrace();
      }

      try {
        InputValidator.validateAccountFolder(AccountID, ConfigPath);
      } catch (InvalidConfigPathException | InvalidAccountIdException error) {
        error.printStackTrace();
      }

      System.out.println("Found " + Accounts.length + " accounts.");
      System.out.println("Starting backup");
      // Backup all configs
      for (final File account : Accounts) {
        try {
          BackupManager.makeNewBackup(account);
          System.out.println("Created backup for account " + account.getAbsolutePath());
        } catch (final Exception error) {
          System.out.println("Error when creating a backup " + account.getAbsolutePath());
          continue;
        }
      }

      File accountDir = new File(ConfigPath, AccountID);
      File gameDir = new File(accountDir, GameID);
      // TODO: check that all is good with gameDir

      for (final File account : Accounts) {
        if (!account.getPath().contains(AccountID)) {
          File gameConfigCopy = new File(account, GameID);
          if (gameConfigCopy.exists()) {
            if (LinkManager.isSymbolicLink(gameConfigCopy)) {
              LinkManager.removeLink(gameConfigCopy);
            } else {
              try {
                BackupManager.deleteFolderRecursively(gameConfigCopy);
              } catch (IOException error) {
                error.printStackTrace();
              }
            }

            LinkManager.createLink(gameDir, gameConfigCopy);
          }
        }
      }

      System.out.println("Press CTRL+C to exit");
      while (true) {}
    }
  }

  public static void linkConfigs() {
    //
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
      Accounts = AccountManager.getAllAccounts(AccountID, ConfigPath);
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
}
