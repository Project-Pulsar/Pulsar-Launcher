package me.geuxy.gui;

import lombok.Getter;
import lombok.Setter;

import me.geuxy.Launcher;
import me.geuxy.actions.LaunchAction;
import me.geuxy.gui.components.Button;

import javax.swing.*;
import java.awt.*;

@Getter @Setter
public class Window extends JFrame {

    private final JPanel panel, settings;
    private final JSlider minRam, maxRam;
    private final JCheckBox hideOnLaunch;

    private int minimumRam = 1, maximumRam = 1;

    private boolean hide;

    public Window() {
        super("Pulsar Launcher");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(650, 350);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setResizable(false);

        JLabel label = new JLabel("Pulsar", JLabel.CENTER);
        label.setFont(new Font("kilton", Font.PLAIN, 165));

        /*
         * Home
         */
        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(this.getWidth() - 10, 140));
        this.panel.setLayout(new GridLayout(3, 1));

        Font font = new Font("Arial", Font.PLAIN, 20);

        Button launchButton = new Button("Launch");
        launchButton.setFont(font);
        launchButton.addActionListener(new LaunchAction(this));
        this.panel.add(launchButton);

        Button settingsButton = new Button("Settings");
        settingsButton.setFont(font);
        settingsButton.addActionListener(e -> setupSettings());
        this.panel.add(settingsButton);

        Button quitButton = new Button("Quit");
        quitButton.setFont(font);
        quitButton.addActionListener(e -> System.exit(0));
        this.panel.add(quitButton);

        /*
         * Settings
         */
        this.settings = new JPanel();
        this.settings.setPreferredSize(new Dimension(this.getWidth() - 10, 140));
        this.settings.setLayout(new GridLayout(3, 2));

        this.settings.add(new JLabel("Minimum Ram"));
        this.settings.add(new JLabel("Maximum Ram"));

        this.minRam = new JSlider(1, 8);
        this.minRam.addChangeListener(e -> this.minimumRam = minRam.getValue());
        this.settings.add(minRam);

        this.maxRam = new JSlider(1, 8);
        this.maxRam.addChangeListener(e -> this.maximumRam = maxRam.getValue());
        this.settings.add(maxRam);

        this.hideOnLaunch = new JCheckBox("Hide on launch");
        this.hideOnLaunch.addActionListener(e -> hide = !hide);
        this.settings.add(hideOnLaunch);

        Button test = new Button("Back");
        test.addActionListener(e -> setupHome());
        this.settings.add(test);

        /*
         * Setup
         */
        this.setupHome();

        this.add(label);
        this.add(panel);

        this.repaint();

        Launcher.INSTANCE.getConfig().load(Launcher.INSTANCE.getDirectory(), this);
        this.setVisible(true);
    }

    private void setupHome() {
        this.remove(settings);
        this.add(panel);
        validate();
        repaint();

    }

    private void setupSettings() {
        this.remove(panel);
        this.add(settings);
        validate();
        repaint();
    }

    public void setValues(int minimumRam, int maximumRam, boolean hide) {
        this.minimumRam = minimumRam;
        this.maximumRam = maximumRam;
        this.minRam.setValue(minimumRam);
        this.maxRam.setValue(maximumRam);
        this.hide = hide;
        this.hideOnLaunch.setSelected(hide);
    }

}
