package userInterface.cli;

import configManager.App;
import java.awt.List;
import utils.*;

public class ExpertMode {
  private static String AccountID;
  private static String ConfigPath;

  /**
   * Allows user to enter config path and accountid as arguments
   *
   * @param args Command line arguments
   * @param configPath path to steam userdata folder
   */
  public static void expertMode(final String args[], final String configPath) {
    ConfigPath = configPath;
    AccountID = args[0];

    if (!AccountID.matches("^[0-9]+") && !AccountID.isEmpty()) {
      System.out.println("AccountID can only contain numbers");
      AccountID = "";
      System.out.println(
          "Use the syntax: [ProgramName AccountID pathToSteamUserdataFolder(optional)]");
      System.exit(2);
    }

    if (args.length > 1) {
      ConfigPath = args[1];
    }

    System.out.println("AccountID: " + AccountID);
    System.out.println("Steam userdata folder: " + ConfigPath);
  }

  private static void parseArgs(final String args[]) throws InvalidAccountIdException {

    App.setAccountID(args[0]);
    InputValidator.validateAccountId(App.getAccountID());
  }

  // TODO: Lista 2-tuplia???
  private static void seperateArgumentsAndParameters(final String args[]) {
    List parameters = new List();
    Tuple[] arguments = new Tuple[args.length];

    for (int i = 0; i < args.length; i++) {
      if ((args[i].startsWith("-")) || args[i].startsWith("--")) {
        if ((i < args.length + 1)) {
          if (!args[i].startsWith("-") && !args[i].startsWith("--")) {
            Tuple tuple = new Tuple<String, String>(args[i], args[i + 1]);
          } else {
            Tuple tuple = new Tuple<String, String>(args[i], "");
          }
        }
      }
    }
  }
}
