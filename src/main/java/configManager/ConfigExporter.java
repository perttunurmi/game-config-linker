package configManager;

import java.io.File;
import java.io.IOException;

/** ConfigExporter */
public class ConfigExporter {
  private static File exportPath = new File("");

  private static void export() {
    File userdataAccountDir = new File(App.getConfigPath(), App.getAccountID());
    File configFile = new File(userdataAccountDir, App.getGameID());

    try {
      BackupManager.copyFolderRecursively(configFile, exportPath);
    } catch (IOException error) {
      System.err.println("Export failed");
      error.printStackTrace();
    }
  }

  public static void export(File pathToExport) {
    exportPath = pathToExport;
    export();
  }

  public static void setExportPath(File pathToExport) {
    exportPath = pathToExport;
  }

  public static File getExportPath() {
    return exportPath;
  }
}
