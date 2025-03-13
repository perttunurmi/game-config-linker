package userInterface.cli;

import java.util.Scanner;

public class InteractiveMode {
    private static String AccountID;
    private static String ConfigPath;

    /*
     * For running interactively in the terminal
     */
    public static void interactiveMode() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        final Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Enter the path to the Steam userdata folder: (defaults to C:\\Program Files (x86)\\Steam\\userdata\\)");
        final String userdatafolder = scanner.nextLine().trim();
        if (!userdatafolder.isEmpty()) {
            ConfigPath = userdatafolder;
        }

        while (AccountID.isEmpty()) {
            System.out.println(
                    "Enter the main accounts AccountID: (can be found at https://steamdb.info/calculator/)");
            AccountID = scanner.nextLine().trim();

            if (AccountID.isEmpty()) {
                System.out.println("AccountID cannot be empty.");
            }

            if (!AccountID.matches("^[0-9]+") && !AccountID.isEmpty()) {
                System.out.println("AccountID can only contain numbers");
                AccountID = "";
            }
        }

        scanner.close();
    }
}
