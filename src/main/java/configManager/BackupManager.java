package configManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * BackupManager creates and removes backups of configs
 */
public class BackupManager {
    private final String ConfigPath;

    BackupManager(final String ConfigPath) {
        this.ConfigPath = ConfigPath;
    }

    /**
     * Copies the folder and all of it's contents recursively
     * 
     * @param src  source folder
     * @param dest destination for the copy
     * @throws IOException
     */
    public void copyFolderRecursively(final File src, final File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }

            final String files[] = src.list();
            for (final String file : files) {
                final File srcFile = new File(src, file);
                final File destFile = new File(dest, file);
                copyFolderRecursively(srcFile, destFile);
            }

        } else if (!src.isDirectory()) {
            final InputStream in = new FileInputStream(src);
            final OutputStream out = new FileOutputStream(dest);
            try {
                final byte[] buffer = new byte[1024];
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

    /**
     * Removes all backups
     * Helps you to get rid of backups of backups of backups...
     */
    public void RemoveOldBackups() {
        final File userdataFolder = new File(this.ConfigPath);
        for (final File folder : userdataFolder.listFiles()) {
            if (folder.getAbsolutePath().endsWith(".bak")) {
                folder.delete();
                System.out.println("Deleted backup: " + folder.getAbsolutePath());
            }
        }
    }

    /**
     * Does a backup for every file/folder in a directory.
     * Note: will also create backups for backups
     */
    public void MakeNewBackup(final File src) {
        File dest = new File(src.getAbsolutePath() + ".bak");

        try {
            copyFolderRecursively(src, dest);
        } catch (final IOException error) {
            error.printStackTrace();
        }
    }
}
