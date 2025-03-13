package userInterface.cli;

import configManager.App;
import java.util.Scanner;
import utils.InputValidator;
import utils.InvalidAccountIdException;

public class InteractiveMode {
  private static String AccountID = App.getAccountID();
  private static String ConfigPath = App.getConfigPath();

  /*
   * For running interactively in the terminal
   */
  public static void interactiveMode() {
    System.out.print("\033[H\033[2J");
    System.out.flush();

    final Scanner scanner = new Scanner(System.in);

    System.out.println(
        "Enter the path to the Steam userdata folder: (defaults to C:\\Program Files"
            + " (x86)\\Steam\\userdata\\)");
    final String userdatafolder = scanner.nextLine().trim();

    if (!userdatafolder.isEmpty()) {
      ConfigPath = userdatafolder;
      App.setConfigPath(ConfigPath);
    }

    while (AccountID.isEmpty()) {
      System.out.println(
          "Enter the main accounts AccountID: (can be found at https://steamdb.info/calculator/)");
      AccountID = scanner.nextLine().trim();

      if (AccountID.isEmpty()) {
        System.out.println("AccountID cannot be empty.");
      }

      try {
        InputValidator.validateAccountId(AccountID);
      } catch (InvalidAccountIdException error) {
        System.out.println("AccountID can only contain numbers");
        AccountID = "";
      }
    }

    App.setAccountID(AccountID);

    scanner.close();
  }
}
