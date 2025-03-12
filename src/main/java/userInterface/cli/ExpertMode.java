package config.cli;

public class ExpertMode {
    private static String AccountID;
    private static String ConfigPath;

    /**
     * Allows user to enter config path and accountid as arguments
     * 
     * @param args       Command line arguments
     * @param configPath path to steam userdata folder
     */
    public static void expertMode(String args[], String configPath) {
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
}
