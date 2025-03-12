package configManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BackupManager {
    public static void makeBackup(File src, File dest) throws IOException {
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
    public static void RemoveOldBackups() {
    }

}
