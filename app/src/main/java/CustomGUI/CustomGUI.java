package CustomGUI;

import javax.swing.*;
import java.awt.*;

public class CustomGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JButton button;
    private NetworkTableClient client;

    CustomGUI(){
        client = new NetworkTableClient();
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

    CustomGUI(int windowSize, int aspectLength, int aspectHeight){
        client = new NetworkTableClient();
        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(new GridLayout(0, 1));

        label = new JLabel("Test Label");
        panel.add(label);

        button = new JButton("Lol");
        panel.add(button);

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
