package CustomGUI;

import javax.swing.*;
import java.awt.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class CustomGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JButton button;

    CustomGUI(){
        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(new GridLayout(0, 1));

        label = new JLabel("Test Label");
        panel.add(label);

        button = new JButton("Lol");
        panel.add(button);

        frame.setVisible(true);
    }
    
}
