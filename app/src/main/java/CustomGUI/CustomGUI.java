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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import edu.wpi.first.networktables.NetworkTable;

public class CustomGUI {
    private JFrame frame;
    private JPanel switchPanel;

    private JPanel motorPanel;
    private JScrollPane motorPane;
    private DevicePanel[] devicePanels;
    private JButton restartButton;

    private JPanel startPanel;
    private JLabel startLabel;
    private JLabel teamLabel;
    private JButton startButton;
    private JTextField startTextField;

    private JPanel protoPanel;
    private JLabel protoLabel;
    private JTextField motorTextField;
    private JButton enterButton;

    private CardLayout cl;
    
    private GridBagConstraints labelConstraint;
    private GridBagConstraints buttonConstraint;
    private GridBagConstraints textFieldConstraint;

    private String teamInfoString;
    private int teamInfoInt;
    private String motorInfoString;
    private int motorInfoInt;

    private NetworkTableClient client;
    private InputStream is;
    public static Font font;

    CustomGUI(int windowSize, int aspectLength, int aspectHeight){
        client = new NetworkTableClient();
        frame = new JFrame();
        switchPanel = new JPanel();
        startPanel = new JPanel();
        protoPanel = new JPanel();
        labelConstraint = new GridBagConstraints();
        buttonConstraint = new GridBagConstraints();
        textFieldConstraint = new GridBagConstraints();
        motorPanel = new JPanel();
        motorPanel.setBackground(Color.BLACK);
        motorPanel.setLayout(new GridBagLayout());
        
        motorPane = new JScrollPane(motorPanel);
        motorPane.getVerticalScrollBar().setUnitIncrement(50);
        motorPane.getHorizontalScrollBar().setUnitIncrement(50);
        motorPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        switchPanel.add(motorPane, "MOTOR");
        cl.show(switchPanel, "START");
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                teamInfoString = startTextField.getText();
                teamInfoInt = Integer.parseInt(teamInfoString);
                client.startClient(teamInfoInt);
                cl.show(switchPanel, "PROTO");
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                motorInfoString = motorTextField.getText();
                motorInfoInt = Integer.parseInt(motorInfoString);
                client.getEntry("Number of Motors:").setDouble(motorInfoInt);
                cl.show(switchPanel, "MOTOR");
                devicePanels = new DevicePanel[motorInfoInt];
                for (int i = 0; i < motorInfoInt; i++) {
                    devicePanels[i] = (new DevicePanel(motorPanel, i, client));
                }
                restartButton = new JButton("Restart");
                restartButton = CustomGUI.formatButton(restartButton, 32f);
                restartButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        NetworkTable table = client.getTable();
                        for (String key : table.getKeys()) {
                            table.delete(key);
                        }
                        client.getEntry("reset").setBoolean(true);
                        motorInfoInt = 0;
                        motorPanel.removeAll();
                        devicePanels = new DevicePanel[0];
                        cl.show(switchPanel, "PROTO");
                    }
                });
                GridBagConstraints restartConstraints = CustomGUI.formatConstraint(new GridBagConstraints(), GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 1, ((int)motorInfoInt / 3)+1);
                if(motorInfoInt == 1) {
                    restartConstraints.gridx = 0;
                } else if(motorInfoInt == 2) {
                    restartConstraints.gridx = 0;
                    restartConstraints.gridwidth = 2;
                }
                motorPanel.add(restartButton, restartConstraints);
            }
        });

        
        frame.setVisible(true);
    }

    private void startPanelInitialize(int windowSize, int aspectLength, int aspectHeight){
        startPanel.setLayout(new GridBagLayout());
        startPanel.setSize(new Dimension(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize)));
        startPanel.setBackground(Color.BLACK);
        

        startLabel = new JLabel("Welcome to the Prototype Dashboard", SwingConstants.CENTER);
        startLabel.setFont(font.deriveFont(36f));
        startLabel.setForeground(Color.WHITE);
        labelConstraint = formatConstraint(labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 100, 0, 0);
        startPanel.add(startLabel, labelConstraint);

        teamLabel = new JLabel("Enter Team Number", SwingConstants.CENTER);
        teamLabel.setFont(font.deriveFont(24f));
        teamLabel.setForeground(Color.WHITE);
        labelConstraint = formatConstraint(labelConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 50, 50, 0, 1);
        startPanel.add(teamLabel, labelConstraint);

        startTextField = new JTextField();
        startTextField.setFont(font.deriveFont(24f));
        startTextField.setBackground(Color.WHITE);
        startTextField.setForeground(Color.BLACK);
        textFieldConstraint = formatConstraint(textFieldConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 2);
        startPanel.add(startTextField, textFieldConstraint);

        startButton = new JButton("Press to Continue");
        startButton.setFont(font.deriveFont(24f));
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        buttonConstraint = formatConstraint(buttonConstraint, GridBagConstraints.PAGE_END, GridBagConstraints.HORIZONTAL, 20, 20, 0, 3);
        startPanel.add(startButton, buttonConstraint);
    }

    private void protoPanelInitialize(int windowSize, int aspectLength, int aspectHeight){
        protoPanel.setLayout(new GridBagLayout());
        protoPanel.setSize(new Dimension(setAspectRatioLength(aspectLength)*setWindowSize(windowSize), setAspectRatioHeight(aspectHeight)*setWindowSize(windowSize)));
        protoPanel.setBackground(Color.BLACK);

        protoLabel = new JLabel("Enter Number of Motors", SwingConstants.CENTER);
        protoLabel.setFont(font.deriveFont(36f));
        protoLabel.setForeground(Color.WHITE);
        labelConstraint = formatConstraint(labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 20, 20, 0, 0);
        protoPanel.add(protoLabel, labelConstraint);

        motorTextField = new JTextField();
        motorTextField.setFont(font.deriveFont(24f));
        motorTextField.setBackground(Color.WHITE);
        motorTextField.setForeground(Color.BLACK);
        textFieldConstraint = formatConstraint(textFieldConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 1);
        protoPanel.add(motorTextField, textFieldConstraint);

        enterButton = new JButton("Enter");
        enterButton.setFont(font.deriveFont(24f));
        enterButton.setBackground(Color.red);
        enterButton.setForeground(Color.WHITE);
        enterButton.setOpaque(true);
        enterButton.setFocusPainted(false);
        enterButton.setBorderPainted(false);
        buttonConstraint = formatConstraint(buttonConstraint, GridBagConstraints.LINE_END, GridBagConstraints.HORIZONTAL, 20, 0, 1, 1);
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

    public static GridBagConstraints formatConstraint(GridBagConstraints buttonConstraint, int anchor, int fillType, int ipadx, int ipady, int gridx, int gridy) {
        buttonConstraint.anchor = anchor;
        buttonConstraint.fill = fillType;
        buttonConstraint.ipadx = ipadx;
        buttonConstraint.ipady = ipady;
        buttonConstraint.gridx = gridx;
        buttonConstraint.gridy = gridy;
        return buttonConstraint;
    }

    public static JButton formatButton(JButton button, float fontSize) {
        button.setFont(font.deriveFont(fontSize));
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    public static JLabel formatLabel(JLabel label, float fontSize) {
        label.setFont(CustomGUI.font.deriveFont(fontSize));
        label.setForeground(Color.WHITE);
        return label;
    }

    public static JTextField formatTextField(JTextField textField, float fontSize) {
        textField.setFont(font.deriveFont(fontSize));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        return textField;
    }

}
