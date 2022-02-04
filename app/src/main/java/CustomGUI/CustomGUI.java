package CustomGUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomGUI {
    private JFrame frame;
    private JPanel switchPanel;
    private JPanel startPanel;
    private JPanel protoPanel;
    private JLabel startLabel;
    private JButton startButton;

    private CardLayout cl;
    
    private GridBagConstraints labelConstraint;
    private GridBagConstraints buttonConstraint;

    private NetworkTableClient client;

    CustomGUI(int windowSize, int aspectLength, int aspectHeight){
        client = new NetworkTableClient();
        frame = new JFrame();
        switchPanel = new JPanel();
        startPanel = new JPanel();
        protoPanel = new JPanel();
        cl = new CardLayout();

        frame.setSize(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(switchPanel);
        startPanelInitialize(windowSize, aspectLength, aspectHeight);
        protoPanelInitialize(windowSize, aspectLength, aspectHeight);

        switchPanel.setLayout(cl);
        switchPanel.add(startPanel, "START");
        switchPanel.add(protoPanel, "PROTO");
        cl.show(switchPanel, "START");
        


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                cl.show(switchPanel, "PROTO");
            }
        });
       
        frame.setVisible(true);
    }

    private void startPanelInitialize(int windowSize, int aspectLength, int aspectHeight){
        startPanel.setLayout(new GridBagLayout());
        startPanel.setSize(new Dimension(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize)));
        startPanel.setBackground(Color.BLACK);
        

        startLabel = new JLabel("Welcome to the Prototype Dashboard", SwingConstants.CENTER);
        startLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        startLabel.setForeground(Color.WHITE);
        labelConstraint = new GridBagConstraints();
        labelConstraint.anchor = GridBagConstraints.PAGE_START;
        labelConstraint.fill = GridBagConstraints.HORIZONTAL;
        labelConstraint.ipadx = 100;
        labelConstraint.ipady = 100;
        labelConstraint.gridx = 0;
        labelConstraint.gridy = 0;
        startPanel.add(startLabel, labelConstraint);

        startButton = new JButton("Press to Continue");
        startButton.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        buttonConstraint = new GridBagConstraints();
        buttonConstraint.anchor = GridBagConstraints.CENTER;
        buttonConstraint.fill = GridBagConstraints.HORIZONTAL;
        buttonConstraint.ipadx = 100;
        buttonConstraint.ipady = 100;
        buttonConstraint.gridx = 0;
        buttonConstraint.gridy = 1;
        startPanel.add(startButton, buttonConstraint);
    }

    private void protoPanelInitialize(int windowSize, int aspectLength, int aspectHeight){
        protoPanel.setLayout(new GridBagLayout());
        protoPanel.setSize(new Dimension(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize)));
        protoPanel.setBackground(Color.BLACK);
        
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
