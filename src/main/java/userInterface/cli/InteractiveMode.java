package userInterface.cli;

import configManager.App;
import java.io.File;
import java.util.Scanner;
import utils.*;

public class InteractiveMode {

  /** For running interactively in the terminal ignores other args */
  public static void interactiveMode() {
    System.out.print("\033[H\033[2J");
    System.out.flush();

    final Scanner scanner = new Scanner(System.in);

    // Ask if the default path is corret
    boolean configPathSet = false;
    while (true) {
      System.out.println("Is your steam userdatafolder in " + App.getConfigPath() + "? (y/n)");
      final String answer = scanner.nextLine().trim();
      if (answer.trim().toLowerCase().matches("y")) {
        configPathSet = true;
        break;
      } else if (answer.trim().toLowerCase().matches("n")) {
        configPathSet = false;
        break;
      }
    }

    // Aks if userdata is not in the default location
    if (!configPathSet) {
      String userdatafolder = "";

      while (userdatafolder.isEmpty()) {
        System.out.println("Enter the path to the Steam userdata folder: ");
        userdatafolder = scanner.nextLine().trim();
        try {
          InputValidator.validateConfigPath(userdatafolder);
        } catch (final InvalidConfigPathException error) {
          System.out.println("userdata folder doesn't exist or is not a directory");
          userdatafolder = "";
        }
      }

      App.setConfigPath(userdatafolder);
    }

    // Get all accounts from userdata folder and predict the main account
    App.setAccounts();
    App.predictMainAccount();

    // Ask if main accounts is not the one with most games configs
    boolean accountIdSet = false;
    while (true) {
      System.out.println(
          "Is your main steam accounts accountid: " + App.getAccountID() + "? (y/n)");
      final String answer = scanner.nextLine().trim();
      if (answer.trim().toLowerCase().matches("y")) {
        accountIdSet = true;
        break;
      } else if (answer.trim().toLowerCase().matches("n")) {
        accountIdSet = false;
        break;
      }
    }

    if (!accountIdSet) {
      String accountID = "";
      while (accountID.isEmpty()) {
        System.out.println("Enter the main account's accountID: ");
        accountID = scanner.nextLine().trim();

        if (accountID.isEmpty()) {
          System.out.println("accountID cannot be empty.");
        }

        try {
          InputValidator.validateAccountId(accountID);
        } catch (final InvalidAccountIdException error) {
          System.out.println("accountID can only contain numbers");
          accountID = "";
        }
      }
      App.setAccountID(accountID);
    }

    // Ask if gameid is not counterstrike
    boolean gameIdSet = false;
    while (true) {
      System.out.println("Copy config for the gameID 730 Counter-Strike 2 / CSGO? (y/n)");
      final String answer = scanner.nextLine().trim();
      if (answer.trim().toLowerCase().matches("y")) {
        gameIdSet = true;
        break;
      } else if (answer.trim().toLowerCase().matches("n")) {
        gameIdSet = false;
        break;
      }
    }

    if (!gameIdSet) {
      String gameID = "";
      while (gameID.isEmpty()) {
        System.out.println("Enter GameID:");
        gameID = scanner.nextLine().trim();

        final File accountPath = new File(App.getConfigPath(), App.getAccountID());
        try {
          InputValidator.validateGameId(accountPath, gameID);
        } catch (final InvalidGameIdException error) {
          System.out.println("Config for this gameID does not exist");
          gameID = "";
        }
      }
    }

    System.out.println("Do you want to make new backups? this will remove old backups(y/n)");

    final String makeBackupAnswer = scanner.nextLine().trim();
    if (makeBackupAnswer.trim().toLowerCase().matches("y")
        || makeBackupAnswer.trim().toLowerCase().matches("yes")) {
      System.out.println("Removing old backups");
      App.removeOldBackups();

      System.out.println("Starting backup");
      App.backupAllConfigs();
    } else {
      System.out.println("Skipped backup");
    }

    System.out.println("Next action will delete all configs except the one from your main account");
    System.out.println("Do you want to link the configs? (y/n)");

    final String createLinksAnswer = scanner.nextLine().trim();
    if (createLinksAnswer.trim().toLowerCase().matches("y")
        || createLinksAnswer.trim().toLowerCase().matches("yes")) {
      System.out.println("Linking configs...");
      App.linkConfigs();
      System.out.println("Done. (Press ENTER to exit)");
      scanner.nextLine().trim();
      scanner.close();
      return;
    } else {
      System.out.println("Cancelled linking (press ENTER to exit)");
      scanner.nextLine().trim();
      scanner.close();
      return;
    }
  }
}
