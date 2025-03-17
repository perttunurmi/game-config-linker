package userInterface.gui;

import java.awt.BorderLayout;
// Java program using label (swing)
import javax.swing.*;

// Main class
public class UserInterface {
  public static void gui() {
    // Creating the Frame
    JFrame frame = new JFrame("Config manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);

    // Creating the MenuBar and adding components
    JMenuBar menubar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenu menu2 = new JMenu("Help");
    menubar.add(menu1);
    menubar.add(menu2);
    JMenuItem menu11 = new JMenuItem("Import config");
    JMenuItem menu22 = new JMenuItem("Export config");
    menu1.add(menu11);
    menu1.add(menu22);

    JPanel panel = new JPanel();
    JLabel labelConfigPath = new JLabel("Config path: ");
    JLabel labelAccountID = new JLabel("AccountID: ");
    JTextField tfConfigPath = new JTextField(40);
    JTextField tfAccountID = new JTextField(40);
    panel.add(labelConfigPath);
    panel.add(tfConfigPath);
    panel.add(labelAccountID);
    panel.add(tfAccountID);

    // Text Area at the Center
    JTextArea consoleArea = new JTextArea(5, 1);
    consoleArea.append("staring...\n");

    // Adding Components to the frame.
    frame.getContentPane().add(BorderLayout.SOUTH, consoleArea);
    frame.getContentPane().add(BorderLayout.NORTH, menubar);
    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setVisible(true);
  }
}
