package config.gui;

// Java program using label (swing)
import java.io.*;
import javax.swing.*;

// Main class
public class UserInterface {

    // Main driver method
    public static void main(String[] args) {
        // Creating instance of JFrame
        JFrame frame = new JFrame();

        // Creating instance of JButton
        JButton button = new JButton("test");

        // x axis, y axis, width, height
        button.setBounds(150, 200, 220, 50);

        // adding button in JFrame
        frame.add(button);

        JTextField textField = new JTextField("");
        textField.setBounds(150, 100, 200, 30);

        frame.add(textField);

        // 400 width and 500 height
        frame.setSize(500, 600);

        // using no layout managers
        frame.setLayout(null);

        // making the frame visible
        frame.setVisible(true);
    }
}
