package userInterface.gui;

import configManager.App;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.*;

public class UserInterface {
  private static JLabel fullpath =
      new JLabel(
          "Fullpath: "
              + App.getConfigPath()
              + "\\"
              + App.getAccountID()
              + "\\"
              + App.getGameID()
              + "\\");

  public static void gui() {
    final JFrame frame = new JFrame("Config manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 200);
    frame.setResizable(false);

    final JPanel menubar = menubar();
    final JPanel buttonArea = buttonArea();
    final JPanel panel = centerPanel();

    frame.getContentPane().add(BorderLayout.SOUTH, buttonArea);
    frame.getContentPane().add(BorderLayout.NORTH, menubar);
    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setVisible(true);
  }

  private static JPanel config() {
    final JPanel panel = new JPanel(new FlowLayout());
    final JLabel label = new JLabel("Path to config dir:");
    final JButton chooseButton = new JButton("Choose Directory");
    final JLabel pathLabel = new JLabel(App.getConfigPath());

    chooseButton.addActionListener(
        e -> {
          final JFileChooser chooser = new JFileChooser();
          chooser.setCurrentDirectory(new File(App.getConfigPath()));
          chooser.setDialogTitle("Select Steam userdata directory");
          chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          chooser.setAcceptAllFileFilterUsed(false);

          if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            final String newPath = chooser.getSelectedFile().getAbsolutePath();
            App.setConfigPath(newPath);
            pathLabel.setText(newPath);
            fullpath.setText(
                "Fullpath: "
                    + App.getConfigPath()
                    + "\\"
                    + App.getAccountID()
                    + "\\"
                    + App.getGameID()
                    + "\\");
          }
        });

    panel.add(label);
    panel.add(pathLabel);
    panel.add(chooseButton);

    return panel;
  }

  private static JPanel accountId() {
    final JPanel panel = new JPanel(new FlowLayout());
    final JComboBox<String> comboBox = new JComboBox<>();
    final JLabel label = new JLabel("AccountID:");

    // Populate the combo box with account IDs
    final File[] accounts = App.getAccounts();
    for (final File account : accounts) {
      comboBox.addItem(account.getName());
    }

    // Set the selected item to current account ID if it exists
    final String currentAccountId = App.getAccountID();
    if (currentAccountId != null && !currentAccountId.isEmpty()) {
      comboBox.setSelectedItem(currentAccountId);
    }

    panel.add(label);
    panel.add(comboBox);

    comboBox.addActionListener(
        e -> {
          App.setAccountID((String) comboBox.getSelectedItem());
          fullpath.setText(
              "Fullpath: "
                  + App.getConfigPath()
                  + "\\"
                  + App.getAccountID()
                  + "\\"
                  + App.getGameID()
                  + "\\");
        });

    return panel;
  }

  private static JPanel gameId() {
    final JPanel panel = new JPanel(new FlowLayout());
    final JComboBox<String> comboBox = new JComboBox<>();
    final JLabel label = new JLabel("GameID:");

    // Populate the combo box with game IDs
    final String[] gameIDs = App.getGameIDs();
    for (final String gameID : gameIDs) {
      comboBox.addItem(gameID);
    }

    // Set the selected item to current game ID if it exists
    final String currentGameId = App.getGameID();
    if (currentGameId != null && !currentGameId.isEmpty()) {
      comboBox.setSelectedItem(currentGameId);
    }

    panel.add(label);
    panel.add(comboBox);

    comboBox.addActionListener(
        e -> {
          App.setGameID((String) comboBox.getSelectedItem());
          fullpath.setText(
              "Fullpath: "
                  + App.getConfigPath()
                  + "\\"
                  + App.getAccountID()
                  + "\\"
                  + App.getGameID()
                  + "\\");
        });

    return panel;
  }

  private static JPanel centerPanel() {
    final JPanel panel = new JPanel(new FlowLayout());

    panel.add(config());
    panel.add(accountId());
    panel.add(gameId());

    return panel;
  }

  /*
   * Creates the console area at the bottom of the window and
   * manages appending information to it
   */
  private static JPanel buttonArea() {
    final JPanel panel = new JPanel();

    final JPanel parent = new JPanel(new BorderLayout());
    parent.add(fullpath, BorderLayout.NORTH);
    parent.add(panel, BorderLayout.SOUTH);

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

    return parent;
  }

  /*
   * Creates the menubar and handless the clicks
   */
  private static JPanel menubar() {
    final JPanel panel = new JPanel(new BorderLayout());

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

    panel.add(menubar, BorderLayout.SOUTH);

    return panel;
  }
}

