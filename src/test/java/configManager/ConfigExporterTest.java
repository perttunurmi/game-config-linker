package configManager;

import java.io.File;
import org.junit.jupiter.api.Test;

/** ConfigExporterTest */
public class ConfigExporterTest {
  private final String projectDir = System.getProperty("user.dir");
  private final File testDir = new File(projectDir + "/src/test/resources");

  @Test
  public void ExportText() {
    final File path = new File(testDir, "testExport");
    App.setAccountID("73936547");
    App.setConfigPath(testDir.toString());

    ConfigExporter.setExportPath(path);
    ConfigExporter.export();
  }
}
