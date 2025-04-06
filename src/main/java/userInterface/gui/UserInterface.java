package userInterface.gui;

import configManager.App;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

public class UserInterface {
  private static JLabel fullpath =
      new JLabel(
          "Fullpath: "
              + App.getConfigPath()
              + "/"
              + App.getAccountID()
              + "/"
              + App.getGameID()
              + "/");

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
    final JTextField configPath = new JTextField(App.getConfigPath(), 40);
    final JLabel label = new JLabel("Path to config dir:");

    configPath.addActionListener(
        e -> {
          App.setConfigPath(configPath.getText());

          fullpath.setText(
              "Fullpath: "
                  + App.getConfigPath()
                  + "/"
                  + App.getAccountID()
                  + "/"
                  + App.getGameID()
                  + "/");
        });

    panel.add(label);
    panel.add(configPath);

    return panel;
  }

  private static JPanel accountId() {
    final JPanel panel = new JPanel(new FlowLayout());
    final JTextField textfield = new JTextField(App.getAccountID(), 10);
    final JLabel label = new JLabel("AccountID:");

    panel.add(label);
    panel.add(textfield);

    textfield.addActionListener(
        e -> {
          App.setAccountID(textfield.getText());
          fullpath.setText(
              "Fullpath: "
                  + App.getConfigPath()
                  + "/"
                  + App.getAccountID()
                  + "/"
                  + App.getGameID()
                  + "/");
        });

    return panel;
  }

  private static JPanel gameId() {
    final JPanel panel = new JPanel(new FlowLayout());
    final JTextField textfield = new JTextField(App.getGameID(),6);
    final JLabel label = new JLabel("GameID:");

    panel.add(label);
    panel.add(textfield);
    textfield.addActionListener(
        e -> {
          App.setGameID(textfield.getText());
          fullpath.setText(
              "Fullpath: "
                  + App.getConfigPath()
                  + "/"
                  + App.getAccountID()
                  + "/"
                  + App.getGameID()
                  + "/");
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
    JPanel panel = new JPanel(new BorderLayout());

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
