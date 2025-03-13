package configManager;

import java.io.File;
import userInterface.cli.*;
import utils.*;

// TODO: Add GUI
// TODO: Make the program to work with multiple games

/*
 * Steam game config manager
 * Author: Perttu Nurmi
 * Email: perttu.nurmi@gmail.com
 * Program that links multiple Steam accounts to use the same config files
 */
public class ConfigLinker {
  private static String ConfigPath = "C:\\Program Files (x86)\\Steam\\userdata";
  private static String AccountID =
      ""; // Steam AccountID, can be found at https://steamdb.info/calculator/
  private static String GameID = "730"; // GameID, default 730 = csgo/cs2
  private static File[] Accounts;

  public static void main(final String[] args) {

    InteractiveMode.interactiveMode();

    // At this point ConfigPath and AccountID should be set
    try {
      Accounts = AccountManager.getAllAccounts();
    } catch (final InvalidConfigPathException error) {
      System.out.println(error.getMessage());
      System.exit(1);
    } catch (final InvalidAccountIdException error) {
      System.out.println(error.getMessage());
      System.exit(2);
    }

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

    System.out.println("Press CTRL+C to exit");
    while (true) {}
  }

  @SuppressWarnings("unused")
  private static void makeLinks() {}
}
