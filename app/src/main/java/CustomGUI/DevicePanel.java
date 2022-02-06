package CustomGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.first.networktables.EntryListenerFlags;


public class DevicePanel {

    
    private int port;
    private int type;
    private double speed;

    private final int motorNumber;
    private final NetworkTableClient client;
    private final JPanel motorPanel;
    private final JPanel panel;
    private JLabel portLabel;
    private JButton portButton;
    private JTextField portField;
    
    private JLabel typeLabel;
    private JButton canSparkMaxTypeButton;
    private JButton talonTypeButton;
    private JButton victorTypeButton;
    private JButton solenoidTypeButton;

    private JLabel speedLabel;
    private JSlider speedSlider;
    private JTextField speedField;
    private JLabel kPLabel;
    private JTextField kPField;
    private JLabel kILabel;
    private JTextField kIField;
    private JLabel kDLabel;
    private JTextField kDField;
    private JLabel setPointLabel;
    private JTextField setPointField;
    private JLabel velocityLabel;
    private JLabel solenoidLabel;
    private JButton solenoidToggleButton;
    private Boolean solenoidToggled = false;

    private GridBagConstraints labelConstraint = new GridBagConstraints();
    private GridBagConstraints buttonConstraint = new GridBagConstraints();
    private GridBagConstraints textFieldConstraint = new GridBagConstraints();
    private GridBagConstraints panelConstraint = new GridBagConstraints();

    public DevicePanel(JPanel motorPanel, int motorNumber, NetworkTableClient client) {
        this.motorPanel = motorPanel;
        this.motorNumber = motorNumber;
        this.client = client;
        this.panel = new JPanel();
        this.portButton = new JButton("Enter");
        this.portField = new JTextField();
        this.portLabel = new JLabel("Select Device Port");
        port = -1;

        this.panel.setBackground(Color.BLACK);
        this.panel.setLayout(new GridBagLayout());

        this.typeLabel = new JLabel("Select Type of Device");
        this.canSparkMaxTypeButton = new JButton("CANSPARKMAX");
        this.talonTypeButton = new JButton("TALONSRX");
        this.victorTypeButton = new JButton("VICTORSPX");
        this.solenoidTypeButton = new JButton("SOLENOID");

        this.portLabel = CustomGUI.formatLabel(this.portLabel, 36f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 0);
        this.panel.add(this.portLabel, this.labelConstraint);

        this.portField = CustomGUI.formatTextField(this.portField, 24f);
        this.textFieldConstraint = CustomGUI.formatConstraint(this.textFieldConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 1);
        this.panel.add(this.portField, this.textFieldConstraint);

        this.portButton = CustomGUI.formatButton(this.portButton, 24f);
        this.buttonConstraint = CustomGUI.formatConstraint(this.buttonConstraint, GridBagConstraints.PAGE_END, GridBagConstraints.HORIZONTAL, 20, 20, 0, 2);
        this.panel.add(this.portButton, this.buttonConstraint);

        this.portButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                port = Integer.parseInt(portField.getText());
                client.getEntry("Port for Motor #" + motorNumber + ":").setDouble(port);
            }
        });

        this.typeLabel = CustomGUI.formatLabel(this.typeLabel, 36f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 100, 20, 0, 4);
        this.panel.add(this.typeLabel, this.labelConstraint);

        this.canSparkMaxTypeButton = CustomGUI.formatButton(this.canSparkMaxTypeButton, 24f);
        this.buttonConstraint = CustomGUI.formatConstraint(this.buttonConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 20, 20, 0, 5);
        this.canSparkMaxTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(port != -1) {
                    type = 1;
                    client.getEntry("Type of Motor #" + motorNumber + ":").setDouble(type);
                    hidePortAndType();
                    showPIDMotor();
                }
            }
        });

        this.panel.add(this.canSparkMaxTypeButton, this.buttonConstraint);

        this.talonTypeButton = CustomGUI.formatButton(this.talonTypeButton, 24f);
        this.buttonConstraint = CustomGUI.formatConstraint(this.buttonConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 20, 20, 0, 6);
        this.talonTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(port != -1) {
                    type = 2;
                    client.getEntry("Type of Motor #" + motorNumber + ":").setDouble(type);
                    hidePortAndType();
                    showNormalMotor();
                }
            }
        });
        this.panel.add(this.talonTypeButton, this.buttonConstraint);

        this.victorTypeButton = CustomGUI.formatButton(this.victorTypeButton, 24f);
        this.buttonConstraint = CustomGUI.formatConstraint(this.buttonConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 20, 20, 0, 7);
        this.victorTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(port != -1) {
                    type = 3;
                    client.getEntry("Type of Motor #" + motorNumber + ":").setDouble(type);
                    hidePortAndType();
                    showNormalMotor();
                }
            }
        });
        this.panel.add(this.victorTypeButton, this.buttonConstraint);

        this.solenoidTypeButton = CustomGUI.formatButton(this.solenoidTypeButton, 24f);
        this.buttonConstraint = CustomGUI.formatConstraint(this.buttonConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 20, 20, 0, 8);
        this.solenoidTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(port != -1) {
                    type = 4;
                    client.getEntry("Type of Motor #" + motorNumber + ":").setDouble(type);
                    hidePortAndType();
                    showSolenoid();
                }
            }
        });
        this.panel.add(this.solenoidTypeButton, this.buttonConstraint);

        this.panelConstraint = CustomGUI.formatConstraint(this.panelConstraint, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 100, 0, (motorNumber % 3), (int)((motorNumber) / 3));
        this.panelConstraint.insets = new Insets(3, 3, 3, 3);
        this.motorPanel.add(this.panel, this.panelConstraint);
    }

    private void hidePortAndType() {
        this.portLabel.setVisible(false);
        this.portField.setVisible(false);
        this.portButton.setVisible(false);
        this.typeLabel.setVisible(false);
        this.canSparkMaxTypeButton.setVisible(false);
        this.talonTypeButton.setVisible(false);
        this.victorTypeButton.setVisible(false);
        this.solenoidTypeButton.setVisible(false);
    }

    public void showNormalMotor() {
        this.speedLabel = new JLabel("Speed for Motor "+motorNumber);
        this.speedLabel = CustomGUI.formatLabel(this.speedLabel, 24f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 0);
        this.panel.add(this.speedLabel, this.labelConstraint);

        this.speedSlider = new JSlider(-100, 100, 0);
        this.speedSlider.setBackground(Color.RED);
        this.speedSlider.setForeground(Color.WHITE);
        this.speedSlider.setOpaque(true);
        this.speedSlider.setMajorTickSpacing(25);
        this.speedSlider.setMinorTickSpacing(1);
        this.speedSlider.setPaintTicks(true);
        this.speedSlider.setPaintLabels(true);
        this.speedSlider.setFont(CustomGUI.font.deriveFont(12f));
        this.speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                speed = speedSlider.getValue()/100.0d;
                speedField.setText(String.valueOf(speedSlider.getValue()));
                client.getEntry("Speed for Motor #" + motorNumber + ":").setDouble(speed);
            }
        });
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 1);
        this.panel.add(this.speedSlider, this.labelConstraint);

        this.speedField = new JTextField(String.valueOf((int)speed * 100));
        this.speedField = CustomGUI.formatTextField(this.speedField, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 2);
        this.speedField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                speed = Integer.parseInt(speedField.getText())/100.0d;
                speedSlider.setValue((int)speed * 100);
                client.getEntry("Speed for Motor #" + motorNumber + ":").setDouble(speed);
            }

            @Override
            public void focusGained(FocusEvent arg0) {}
        });
        this.panel.add(this.speedField, this.labelConstraint);
    }

    public void showPIDMotor() {
        showNormalMotor();
        this.kPLabel = new JLabel("kP for Motor "+motorNumber);
        this.kPLabel = CustomGUI.formatLabel(this.kPLabel, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 3);
        this.panel.add(this.kPLabel, this.labelConstraint);

        this.kPField = new JTextField();
        this.kPField = CustomGUI.formatTextField(this.kPField, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 4);
        this.kPField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                client.getEntry("kP for Motor #" + motorNumber + ":").setDouble(type);
            }

            @Override
            public void focusGained(FocusEvent arg0) {}
        });
        this.panel.add(this.kPField, this.labelConstraint);

        this.kILabel = new JLabel("kI for Motor "+motorNumber);
        this.kILabel = CustomGUI.formatLabel(this.kILabel, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 5);
        this.panel.add(this.kILabel, this.labelConstraint);

        this.kIField = new JTextField();
        this.kIField = CustomGUI.formatTextField(this.kIField, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 6);
        this.kIField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                client.getEntry("kI for Motor #" + motorNumber + ":").setDouble(type);
            }

            @Override
            public void focusGained(FocusEvent arg0) {}
        });
        this.panel.add(this.kIField, this.labelConstraint);

        this.kDLabel = new JLabel("kD for Motor "+motorNumber);
        this.kDLabel = CustomGUI.formatLabel(this.kDLabel, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 7);
        this.panel.add(this.kDLabel, this.labelConstraint);

        this.kDField = new JTextField();
        this.kDField = CustomGUI.formatTextField(this.kDField, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 8);
        this.kDField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                client.getEntry("kP for Motor #" + motorNumber + ":").setDouble(type);
            }

            @Override
            public void focusGained(FocusEvent arg0) {}
        });
        this.panel.add(this.kDField, this.labelConstraint);

        this.setPointLabel = new JLabel("Set Point for Motor "+motorNumber);
        this.setPointLabel = CustomGUI.formatLabel(this.setPointLabel, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 9);
        this.panel.add(this.setPointLabel, this.labelConstraint);

        this.setPointField = new JTextField();
        this.setPointField = CustomGUI.formatTextField(this.setPointField, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 10);
        this.setPointField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                client.getEntry("Set Point for Motor #" + motorNumber + ":").setDouble(type);
            }

            @Override
            public void focusGained(FocusEvent arg0) {}
        });
        this.panel.add(this.setPointField, this.labelConstraint);

        client.getEntry("Velocity for Motor #"+motorNumber+":").addListener(event -> {
            this.velocityLabel.setText("Velocity for Motor "+motorNumber+": "+String.valueOf((int)(event.getEntry().getDouble(0)*100)/100.0d));
         }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        this.velocityLabel = new JLabel("Velocity for Motor "+motorNumber+": "+client.getEntry("Velocity for Motor #"+motorNumber+":").getDouble(0));
        this.velocityLabel = CustomGUI.formatLabel(this.velocityLabel, 18f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 11);
        this.panel.add(this.velocityLabel, this.labelConstraint);
    }

    public void showSolenoid() {
        this.solenoidLabel = new JLabel("Solenoid Enabled for Device "+motorNumber);
        this.solenoidLabel = CustomGUI.formatLabel(this.solenoidLabel, 24f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 0);
        this.panel.add(this.solenoidLabel, this.labelConstraint);

        this.solenoidToggleButton = new JButton("Disabled");
        this.solenoidToggleButton = CustomGUI.formatButton(this.solenoidToggleButton, 24f);
        this.labelConstraint = CustomGUI.formatConstraint(this.labelConstraint, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, 100, 20, 0, 1);
        this.solenoidToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solenoidToggled = !solenoidToggled;
                solenoidToggleButton.setBackground(solenoidToggled ? Color.GREEN : Color.RED);
                solenoidToggleButton.setText(solenoidToggled ? "Enabled" : "Disabled");
                client.getEntry("Solenoid #" + motorNumber + " on or off?").setDouble(solenoidToggled ? 1 : 0);
            }
        });
        this.panel.add(this.solenoidToggleButton, this.labelConstraint);
    }

}
