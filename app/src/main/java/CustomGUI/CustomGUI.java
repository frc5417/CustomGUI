package CustomGUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CustomGUI {
    private JFrame frame;
    private JPanel switchPanel;
    
    private JPanel startPanel;
    private JLabel startLabel;
    private JButton startButton;

    private JPanel protoPanel;
    private JLabel protoLabel;
    private JTextField motorTextField;
    private JButton enterButton;

    private CardLayout cl;
    
    private GridBagConstraints labelConstraint;
    private GridBagConstraints buttonConstraint;
    private GridBagConstraints textFieldConstraint;

    private String motorInfoString;
    private int motorInfoInt;

    private NetworkTableClient client;
    private InputStream is;
    private Font font;

    CustomGUI(int windowSize, int aspectLength, int aspectHeight){
        client = new NetworkTableClient();
        frame = new JFrame();
        switchPanel = new JPanel();
        startPanel = new JPanel();
        protoPanel = new JPanel();
        labelConstraint = new GridBagConstraints();
        buttonConstraint = new GridBagConstraints();
        textFieldConstraint = new GridBagConstraints();
        cl = new CardLayout();

        is = CustomGUI.class.getResourceAsStream("TwCenMTStd.otf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                motorInfoString = motorTextField.getText();
                motorInfoInt = Integer.parseInt(motorInfoString);
            }
        });

        client.getEntry("Number of Motors:").setDouble(motorInfoInt);
       
        frame.setVisible(true);
    }

    private void startPanelInitialize(int windowSize, int aspectLength, int aspectHeight){
        startPanel.setLayout(new GridBagLayout());
        startPanel.setSize(new Dimension(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize)));
        startPanel.setBackground(Color.BLACK);
        

        startLabel = new JLabel("Welcome to the Prototype Dashboard", SwingConstants.CENTER);
        startLabel.setFont(font.deriveFont(36f));
        startLabel.setForeground(Color.WHITE);
        labelConstraint.anchor = GridBagConstraints.PAGE_START;
        labelConstraint.fill = GridBagConstraints.HORIZONTAL;
        labelConstraint.ipadx = 100;
        labelConstraint.ipady = 100;
        labelConstraint.gridx = 0;
        labelConstraint.gridy = 0;
        startPanel.add(startLabel, labelConstraint);

        startButton = new JButton("Press to Continue");
        startButton.setFont(font.deriveFont(36f));
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
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

        protoLabel = new JLabel("Enter Number of Motors", SwingConstants.CENTER);
        protoLabel.setFont(font.deriveFont(36f));
        protoLabel.setForeground(Color.WHITE);
        labelConstraint.anchor = GridBagConstraints.PAGE_START;
        labelConstraint.fill = GridBagConstraints.HORIZONTAL;
        labelConstraint.ipadx = 20;
        labelConstraint.ipady = 20;
        labelConstraint.gridx = 0;
        labelConstraint.gridy = 0;
        protoPanel.add(protoLabel, labelConstraint);

        motorTextField = new JTextField();
        motorTextField.setFont(font.deriveFont(36f));
        motorTextField.setBackground(Color.WHITE);
        motorTextField.setForeground(Color.BLACK);
        textFieldConstraint.anchor = GridBagConstraints.CENTER;
        textFieldConstraint.fill = GridBagConstraints.HORIZONTAL;
        textFieldConstraint.ipadx = 0;
        textFieldConstraint.ipady = 0;
        textFieldConstraint.gridx = 0;
        textFieldConstraint.gridy = 1;
        protoPanel.add(motorTextField, textFieldConstraint);

        enterButton = new JButton("Enter");
        enterButton.setFont(font.deriveFont(36f));
        enterButton.setBackground(Color.RED);
        enterButton.setForeground(Color.WHITE);
        enterButton.setFocusPainted(false);
        buttonConstraint.anchor = GridBagConstraints.LINE_END;
        buttonConstraint.ipadx = 20;
        buttonConstraint.ipady = 0;
        buttonConstraint.gridx = 1;
        buttonConstraint.gridy = 1;
        protoPanel.add(enterButton, buttonConstraint);
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
