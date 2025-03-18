package userInterface.gui;

import configManager.App;
import java.awt.BorderLayout;
import javax.swing.*;

public class UserInterface {
  private static JTextArea consoleArea = new JTextArea(0, 40);

  public static void gui() {
    final JFrame frame = new JFrame("Config manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);

    final JMenuBar menubar = menubar();

    final JTextArea consoleArea = consoleArea(5, 1);

    final JPanel panel = centerPanel();

    frame.getContentPane().add(BorderLayout.SOUTH, consoleArea);
    frame.getContentPane().add(BorderLayout.NORTH, menubar);
    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setVisible(true);
  }

  /*
   *
   */
  private static JPanel centerPanel() {
    final JPanel panel = new JPanel();

    final JLabel labelConfigPath = new JLabel("Config path: ");
    final JTextField tfConfigPath = new JTextField(40);
    tfConfigPath.setText(App.getConfigPath());

    final JLabel labelAccountID = new JLabel("AccountID: ");
    final JTextField tfAccountID = new JTextField(40);

    panel.add(labelConfigPath);
    panel.add(tfConfigPath);
    panel.add(labelAccountID);
    panel.add(tfAccountID);

    final JButton makeBackupButton = new JButton("Make backup");
    panel.add(makeBackupButton);

    final JButton removeBackupButton = new JButton("Remove backup");
    panel.add(removeBackupButton);

    final JButton button = new JButton("Clone config");
    panel.add(button);

    return panel;
  }

  /*
   * Creates the console area at the bottom of the window and
   * manages appending information to it
   */
  private static JTextArea consoleArea(final int rows, final int columns) {
    consoleArea = new JTextArea(rows, columns);
    consoleArea.setEditable(false);
    consoleArea.append("Default config path: " + App.getConfigPath() + "\n");
    consoleArea.append("Make backup is turned ON \n");

    return consoleArea;
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

    menu22.addActionListener(
        e -> {
          consoleArea.append("Exporting config... (not implemented yet)\n");
        });

    menu22.addActionListener(
        e -> {
          consoleArea.append("Importing config... (not implemented yet)\n");
        });

    return menubar;
  }
}
