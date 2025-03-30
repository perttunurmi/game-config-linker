package userInterface.gui;

import configManager.App;
import java.awt.BorderLayout;
import javax.swing.*;

public class UserInterface {

  public static void gui() {
    final JFrame frame = new JFrame("Config manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);

    final JMenuBar menubar = menubar();
    final JPanel buttonArea = buttonArea();
    final JPanel panel = centerPanel();

    frame.getContentPane().add(BorderLayout.SOUTH, buttonArea);
    frame.getContentPane().add(BorderLayout.NORTH, menubar);
    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setVisible(true);
  }

  /*
   *
   */
  private static JPanel centerPanel() {
    final JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    final JLabel labelConfigPath =
        new JLabel(
            "Path to main accounts config "
                + App.getConfigPath()
                + "/"
                + App.getAccountID()
                + "/"
                + App.getGameID());

    panel.add(labelConfigPath, BorderLayout.NORTH);

    return panel;
  }

  /*
   * Creates the console area at the bottom of the window and
   * manages appending information to it
   */
  private static JPanel buttonArea() {
    JPanel panel = new JPanel();

    final JButton makeBackupButton = new JButton("Make backup");
    panel.add(makeBackupButton);
    makeBackupButton.addActionListener(
        e -> {
          App.backupAllConfigs();
          makeBackupButton.setEnabled(false);
        });

    final JButton removeBackupButton = new JButton("Remove backup");
    panel.add(removeBackupButton);
    removeBackupButton.addActionListener(
        e -> {
          App.removeOldBackups();
          makeBackupButton.setEnabled(true);
        });

    final JButton button = new JButton("Clone config");
    panel.add(button);
    button.addActionListener(
        e -> {
          App.linkConfigs();
        });

    return panel;
  }

  /*
   * Creates the menubar and handless the clicks
   */
  private static JMenuBar menubar() {
    final JMenuBar menubar = new JMenuBar();
    final JMenu menu1 = new JMenu("File");
    final JMenu menu2 = new JMenu("Help");
    menubar.add(menu1);
    menubar.add(menu2);
    final JMenuItem menu11 = new JMenuItem("Import config");
    final JMenuItem menu22 = new JMenuItem("Export config");
    menu1.add(menu11);
    menu1.add(menu22);

    menu11.setEnabled(false);
    menu22.setEnabled(false);

    menu11.addActionListener(e -> {});

    menu22.addActionListener(e -> {});

    return menubar;
  }
}
