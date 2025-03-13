package configManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** BackupManager creates and removes backups of configs */
public final class BackupManager {

  /**
   * Copies the folder and all of it's contents recursively
   *
   * @param src source folder
   * @param dest destination for the copy
   * @throws IOException
   */
  public static void copyFolderRecursively(final File src, final File dest) throws IOException {
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

  public static void deleteFolderRecursively(final File file) throws IOException {
    for (final File child : file.listFiles()) {
      if (child.isDirectory()) {
        System.out.println("deleting..." + child);
        deleteFolderRecursively(child);
      } else {
        if (!child.delete()) {
          throw new IOException("Failed to delete file: " + child.getAbsolutePath());
        }
      }
    }

    if (!file.delete()) {
      throw new IOException("Failed to delete file: " + file.getAbsolutePath());
    }
  }

  /**
   * Removes all backups Helps you to get rid of backups of backups of backups and also required so
   * you can create symbolic links
   */
  public static void removeOldBackups(final File userdataFolder) {
    for (final File folder : userdataFolder.listFiles()) {
      if (folder.getAbsolutePath().endsWith(".bak")) {
        try {
          deleteFolderRecursively(folder);
          System.out.println("Deleted backup: " + folder.getAbsolutePath());
        } catch (final IOException error) {
          error.printStackTrace();
        }
      }
    }
  }

  /**
   * Does a backup for every file/folder in a directory. Note: will also create backups for backups
   */
  public static void makeNewBackup(final File src) {
    final File dest = new File(src.getAbsolutePath() + ".bak");

    try {
      copyFolderRecursively(src, dest);
    } catch (final IOException error) {
      error.printStackTrace();
    }
  }
}
