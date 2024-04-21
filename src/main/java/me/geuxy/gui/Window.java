package me.geuxy.gui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeListener;

import lombok.Getter;

import me.geuxy.Launcher;
import me.geuxy.actions.LaunchAction;
import me.geuxy.config.Config;
import me.geuxy.utils.console.Logger;

@Getter
public final class Window extends JFrame {

    private final JPanel homePanel;
    private final JPanel settingsPanel;

    private final JLabel minLabel;
    private final JLabel maxLabel;

    private final JSlider minRamSlider;
    private final JSlider maxRamSlider;

    private final JCheckBox hideOnLaunch;

    private int minRam = 1;
    private int maxRam = 1;

    private boolean hide;

    public Window() {
        super("Pulsar Launcher");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(650, 370);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setResizable(false);

        // Title label
        JLabel title = new JLabel("Pulsar", SwingConstants.CENTER);
        try {
            title.setFont(Font.createFont(0, ClassLoader.getSystemResourceAsStream("assets/kilton.otf")).deriveFont(165.0F));
        } catch(Exception e) {
            Logger.error(e.getMessage());
        }

        // Home panel
        this.homePanel = new JPanel();
        this.homePanel.setPreferredSize(new Dimension(getWidth() - 10, 140));
        this.homePanel.setLayout(new GridLayout(4, 1));
        this.homePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        Font font = new Font("Arial", Font.PLAIN, 20);

        // Launch button
        JButton launchButton = new JButton("Launch");
        launchButton.setFocusPainted(false);
        launchButton.setFont(font);
        launchButton.addActionListener(new LaunchAction(this));
        this.homePanel.add(launchButton);

        // Settings button
        JButton settingsButton = new JButton("Settings");
        settingsButton.setFocusPainted(false);
        settingsButton.setFont(font);
        settingsButton.addActionListener(setupSettings());
        this.homePanel.add(settingsButton);

        // Settings button
        JButton outputButton = new JButton("Output");
        outputButton.setFocusPainted(false);
        outputButton.setFont(font);
        outputButton.addActionListener(e -> Launcher.getInstance().getOutputWindow().setVisible(true));
        this.homePanel.add(outputButton);

        // Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setFocusPainted(false);
        quitButton.setFont(font);
        quitButton.addActionListener(e -> System.exit(0));
        this.homePanel.add(quitButton);

        // Settings panel
        this.settingsPanel = new JPanel();
        this.settingsPanel.setPreferredSize(new Dimension(getWidth() - 10, 140));
        this.settingsPanel.setLayout(new GridLayout(3, 2));
        this.settingsPanel.add(minLabel = new JLabel(getMinRamText(), JLabel.CENTER));
        this.settingsPanel.add(maxLabel = new JLabel(getMaxRamText(), JLabel.CENTER));

        // Minimum ram slider
        this.minRamSlider = new JSlider(1, 8);
        this.minRamSlider.addChangeListener(onMinRamChange());
        this.settingsPanel.add(this.minRamSlider);

        // Maximum ram slider
        this.maxRamSlider = new JSlider(1, 8);
        this.maxRamSlider.addChangeListener(onMaxRamChange());
        this.settingsPanel.add(this.maxRamSlider);

        // Hide on launch checkbox
        this.hideOnLaunch = new JCheckBox("Hide on launch");
        this.hideOnLaunch.addActionListener(e -> this.hide = !this.hide);
        this.settingsPanel.add(this.hideOnLaunch);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(setupHome());
        this.settingsPanel.add(backButton);

        // Set panel to home panel
        setupHome().actionPerformed(null);
        add(title);
        add(this.homePanel);

        Launcher.getInstance().getConfigManager().load(this);

        setVisible(true);
    }

    private ChangeListener onMinRamChange() {
        return e -> {
            this.minRam = minRamSlider.getValue();
            this.minLabel.setText(getMinRamText());
        };
    }

    private ChangeListener onMaxRamChange() {
        return e -> {
            this.maxRam = maxRamSlider.getValue();
            this.maxLabel.setText(getMaxRamText());
        };
    }

    private String getMinRamText() {
        return "Minimum Ram (" + minRam + "GB)";
    }

    private String getMaxRamText() {
        return "Maximum Ram (" + maxRam + "GB)";
    }

    private ActionListener setupHome() {
        return e -> {
            remove(this.settingsPanel);
            add(this.homePanel);
            validate();
            repaint();
        };
    }

    private ActionListener setupSettings() {
        return e -> {
            remove(this.homePanel);
            add(this.settingsPanel);
            validate();
            repaint();
        };
    }

    public void loadConfig(Config config) {
        this.minRam = config.getMinRam();
        this.maxRam = config.getMaxRam();
        this.minRamSlider.setValue(this.minRam);
        this.maxRamSlider.setValue(this.maxRam);
        this.hide = config.isHide();
        this.hideOnLaunch.setSelected(this.hide);
        onMinRamChange().stateChanged(null);
        onMaxRamChange().stateChanged(null);
    }

}
