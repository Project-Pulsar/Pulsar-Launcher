package me.geuxy.gui;

import lombok.Getter;
import lombok.Setter;

import me.geuxy.Launcher;
import me.geuxy.actions.LaunchAction;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@Getter @Setter
public class Window extends JFrame {

    private final JPanel homePanel;
    private final JPanel settingsPanel;

    private final JSlider minimumRamSlider;
    private final JSlider maximumRamSlider;

    private final JCheckBox hideOnLaunch;

    private int minimumRam = 1;
    private int maximumRam = 1;

    private boolean hide;

    public Window() {
        super("Pulsar Launcher");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(650, 350);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setResizable(false);

        JLabel label = new JLabel("Pulsar", JLabel.CENTER);

        try {
            label.setFont(Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResource("assets/kilton.otf").openStream()).deriveFont(165F));
        } catch(Exception ignored) {
        }

        /*
         * Home
         */
        this.homePanel = new JPanel();
        this.homePanel.setPreferredSize(new Dimension(this.getWidth() - 10, 140));
        this.homePanel.setLayout(new GridLayout(3, 1));

        Font font = new Font("Arial", Font.PLAIN, 20);

        JButton launchButton = new JButton("Launch");
        launchButton.setFocusPainted(false);
        launchButton.setFont(font);
        launchButton.addActionListener(new LaunchAction(this));
        this.homePanel.add(launchButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFocusPainted(false);
        settingsButton.setFont(font);
        settingsButton.addActionListener(e -> setupSettings());
        this.homePanel.add(settingsButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setFocusPainted(false);
        quitButton.setFont(font);
        quitButton.addActionListener(e -> System.exit(0));
        this.homePanel.add(quitButton);

        /*
         * Settings
         */
        this.settingsPanel = new JPanel();
        this.settingsPanel.setPreferredSize(new Dimension(this.getWidth() - 10, 140));
        this.settingsPanel.setLayout(new GridLayout(3, 2));

        this.settingsPanel.add(new JLabel("Minimum Ram"));
        this.settingsPanel.add(new JLabel("Maximum Ram"));

        this.minimumRamSlider = new JSlider(1, 8);
        this.minimumRamSlider.addChangeListener(e -> this.minimumRam = minimumRamSlider.getValue());
        this.settingsPanel.add(minimumRamSlider);

        this.maximumRamSlider = new JSlider(1, 8);
        this.maximumRamSlider.addChangeListener(e -> this.maximumRam = maximumRamSlider.getValue());
        this.settingsPanel.add(maximumRamSlider);

        this.hideOnLaunch = new JCheckBox("Hide on launch");
        this.hideOnLaunch.addActionListener(e -> hide = !hide);
        this.settingsPanel.add(hideOnLaunch);

        Button test = new Button("Back");
        test.addActionListener(e -> setupHome());
        this.settingsPanel.add(test);

        /*
         * Setup
         */
        this.setupHome();

        this.add(label);
        this.add(homePanel);

        this.repaint();

        Launcher.INSTANCE.getConfig().load(new File("config.json"), this);

        this.setVisible(true);
    }

    private void setupHome() {
        this.remove(settingsPanel);
        this.add(homePanel);
        validate();
        repaint();

    }

    private void setupSettings() {
        this.remove(homePanel);
        this.add(settingsPanel);
        validate();
        repaint();
    }

    public void setValues(int minimumRam, int maximumRam, boolean hide) {
        this.minimumRam = minimumRam;
        this.maximumRam = maximumRam;
        this.minimumRamSlider.setValue(minimumRam);
        this.maximumRamSlider.setValue(maximumRam);
        this.hide = hide;
        this.hideOnLaunch.setSelected(hide);
    }

}
