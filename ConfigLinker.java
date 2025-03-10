import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class ConfigLinker {
    private static String DefaultConfigPath = "C:\\Program Files (x86)\\Steam\\userdata";
    private static String AccountID = ""; // Steam AccountID, can be found at https://steamdb.info/calculator/
    private static String GameID = "730"; // GameID, default 730 = csgo/cs2

    private static void interactiveMode() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Enter the path to the Steam userdata folder: (defaults to C:\\Program Files (x86)\\Steam\\userdata\\)");
        String userdatafolder = scanner.nextLine().trim();
        if (!userdatafolder.isEmpty()) {
            DefaultConfigPath = userdatafolder;
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

    private static void expertMode(String args[]) {

        AccountID = args[0];

        if (!AccountID.matches("^[0-9]+") && !AccountID.isEmpty()) {
            System.out.println("AccountID can only contain numbers");
            AccountID = "";
            System.out.println(
                    "Use the syntax: [ProgramName AccountID pathToSteamUserdataFolder(optional)]");
            System.exit(2);
        }

        if (args.length > 1) {
            DefaultConfigPath = args[1];
        }

        System.out.println("AccountID: " + AccountID);
        System.out.println("Steam userdata folder: " + DefaultConfigPath);
    }

    private static void makeBackup(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }

            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                makeBackup(srcFile, destFile);
            }

        } else if (!src.isDirectory()) {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            try {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

            } finally {
                in.close();
                out.close();
            }
        }
    }

    @SuppressWarnings("unused")
    private static void RemoveOldBackups() {
    }

    private static void MakeLinks() {
        System.out.println("Creating links for account: " + AccountID + "...");
        File userdata = new File(DefaultConfigPath);

        if (!userdata.exists()) {
            System.out.println("Userdata folder does not exist: " + userdata.getAbsolutePath());
            System.exit(1);
        }

        System.out.println("Userdata folder exists: " + userdata.getAbsolutePath());

        File[] accounts = userdata.listFiles();
        for (File account : accounts) {

            File destination = new File(account.getAbsolutePath() + ".bak");
            try {
                makeBackup(account, destination);
                System.out.println("Created backup for account " + account.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Error when creating a backup " + account.getAbsolutePath());
                continue;
            }



        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            interactiveMode();
        } else {
            expertMode(args);
        }

        MakeLinks();

        System.out.println("press CTRL+C to exit");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
