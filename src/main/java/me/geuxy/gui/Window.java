package me.geuxy.gui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

import lombok.Getter;

import me.geuxy.Launcher;
import me.geuxy.actions.LaunchAction;
import me.geuxy.config.Config;

@Getter
public final class Window extends JFrame {

    private final JPanel homePanel;
    private final JPanel settingsPanel;

    private final JSlider minimumRamSlider;
    private final JSlider maximumRamSlider;

    private final JCheckBox hideOnLaunch;

    private int minRam = 1;
    private int maxRam = 1;

    private boolean hide;

    public Window() {
        super("Pulsar Launcher");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(650, 350);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setResizable(false);
        JLabel label = new JLabel("Pulsar", SwingConstants.CENTER);
        try {
            label.setFont(Font.createFont(0, ClassLoader.getSystemResourceAsStream("assets/kilton.otf")).deriveFont(165.0F));
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.homePanel = new JPanel();
        this.homePanel.setPreferredSize(new Dimension(getWidth() - 10, 140));
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
        settingsButton.addActionListener(setupSettings());
        this.homePanel.add(settingsButton);
        JButton quitButton = new JButton("Quit");
        quitButton.setFocusPainted(false);
        quitButton.setFont(font);
        quitButton.addActionListener(e -> System.exit(0));
        this.homePanel.add(quitButton);
        this.settingsPanel = new JPanel();
        this.settingsPanel.setPreferredSize(new Dimension(getWidth() - 10, 140));
        this.settingsPanel.setLayout(new GridLayout(3, 2));
        this.settingsPanel.add(new JLabel("Minimum Ram"));
        this.settingsPanel.add(new JLabel("Maximum Ram"));
        this.minimumRamSlider = new JSlider(1, 8);
        this.minimumRamSlider.addChangeListener(e -> this.minRam = this.minimumRamSlider.getValue());
        this.settingsPanel.add(this.minimumRamSlider);
        this.maximumRamSlider = new JSlider(1, 8);
        this.maximumRamSlider.addChangeListener(e -> this.maxRam = this.maximumRamSlider.getValue());
        this.settingsPanel.add(this.maximumRamSlider);
        this.hideOnLaunch = new JCheckBox("Hide on launch");
        this.hideOnLaunch.addActionListener(e -> this.hide = !this.hide);
        this.settingsPanel.add(this.hideOnLaunch);
        Button test = new Button("Back");
        test.addActionListener(setupHome());
        this.settingsPanel.add(test);
        setupHome().actionPerformed(null);
        add(label);
        add(this.homePanel);
        repaint();
        Launcher.getInstance().getConfigManager().load(this);
        setVisible(true);
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

    public void setConfig(Config config) {
        this.minRam = config.getMinRam();
        this.maxRam = config.getMaxRam();
        this.minimumRamSlider.setValue(this.minRam);
        this.maximumRamSlider.setValue(this.maxRam);
        this.hide = config.isHide();
        this.hideOnLaunch.setSelected(this.hide);
    }

}
