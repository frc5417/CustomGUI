package CustomGUI;

import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;

public class CustomGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JButton button;
    
    private GridBagConstraints labelConstraint;
    private GridBagConstraints buttonConstraint;

    private NetworkTableClient client;

    CustomGUI(int windowSize, int aspectLength, int aspectHeight){
        client = new NetworkTableClient();
        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(new GridBagLayout());
        panel.setSize(new Dimension(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize)));
        panel.setBackground(Color.BLACK);
        

        label = new JLabel("Welcome to the Prototype Dashboard", SwingConstants.CENTER);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        labelConstraint = new GridBagConstraints();
        labelConstraint.anchor = GridBagConstraints.PAGE_START;
        labelConstraint.fill = GridBagConstraints.HORIZONTAL;
        labelConstraint.ipadx = 100;
        labelConstraint.ipady = 100;
        labelConstraint.gridx = 0;
        labelConstraint.gridy = 0;
        panel.add(label, labelConstraint);

        button = new JButton("Press to Continue");
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        buttonConstraint = new GridBagConstraints();
        buttonConstraint.anchor = GridBagConstraints.CENTER;
        buttonConstraint.fill = GridBagConstraints.HORIZONTAL;
        buttonConstraint.ipadx = 100;
        buttonConstraint.ipady = 100;
        buttonConstraint.gridx = 0;
        buttonConstraint.gridy = 1;
        panel.add(button, buttonConstraint);

        frame.setVisible(true);
    }

    private int setWindowSize(int size){
        return size;
    }
    private int setAspectRatioLength(int length){
        return length;
    }
    private int setAspectRatioHeight(int height){
        return height;
    }

}
