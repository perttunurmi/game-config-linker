package config.cloner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import config.utils.*;
import config.cli.*;

// TODO: Add GUI
// TODO: Add unit tests
// TODO: Make the program to work with multiple games

/*
 * Steam game config manager
 * Author: Perttu Nurmi
 * Github: perttunurmi
 * Email: perttu.nurmi@gmail.com
 * Version: 0.1
 * License: MIT
 * Program that links multiple Steam accounts to use the same config files
 */
public class ConfigLinker {
    private static String ConfigPath = "C:\\Program Files (x86)\\Steam\\userdata";
    private static String AccountID = ""; // Steam AccountID, can be found at https://steamdb.info/calculator/
    private static String GameID = "730"; // GameID, default 730 = csgo/cs2
    private static File[] Accounts;

    private static void getAllAccounts() throws InvalidConfigPathException, InvalidAccountIdException {
        // Check for in invalid attributes
        if (!AccountID.matches("^[0-9]+")) {
            System.out.println("AccountID can only contain numbers");
            AccountID = "";
            System.exit(3);

        } else if (ConfigPath.trim().isEmpty()) {
            throw new InvalidConfigPathException("ConfigPath is not set");
        }

        else {
            File config = new File(ConfigPath);
            File accountPath = new File(ConfigPath, AccountID);

            if (!config.exists() || !config.isDirectory()) {
                throw new InvalidConfigPathException(
                        "Directory " + config + " doesn't exist or is not a directory");
            }

            if (!accountPath.exists() || !accountPath.isDirectory()) {
                throw new InvalidAccountIdException("Directory " + accountPath + " doesn't exist");
            }

            Accounts = config.listFiles();
        }
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

    /*
     * Removes all backups
     * Helps you to get rid of backups of backups of backups...
     */
    @SuppressWarnings("unused")
    private static void RemoveOldBackups() {
    }

    @SuppressWarnings("unused")
    private static void makeLinks() {
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            InteractiveMode.interactiveMode();
        } else {
            ExpertMode.expertMode(args, ConfigPath);
        }
        // At this point ConfigPath and AccountID should be set

        // Backup all configs
        for (File account : Accounts) {
            File destination = new File(account.getAbsolutePath() + ".bak");
            try {
                makeBackup(account, destination);
                System.out.println("Created backup for account " + account.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Error when creating a backup " + account.getAbsolutePath());
                continue;
            }
        }

        try {
            getAllAccounts();
        } catch (InvalidConfigPathException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (InvalidAccountIdException e) {
            System.out.println(e.getMessage());
            System.exit(2);
        }

        System.out.println("Press CTRL+C to exit");
        while (true) {
        }
    }

}
