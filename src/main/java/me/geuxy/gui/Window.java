package me.geuxy.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javafx.scene.control.SelectionMode;

import lombok.Getter;

import me.geuxy.Launcher;
import me.geuxy.utils.render.ImageUtil;

@Getter
public final class Window extends JFrame implements ListSelectionListener {

    private final HomePanel home;
    private final AboutPanel about;
    private final SettingsPanel settings;
    private final OutputPanel output;

    private final JList list;

    private final ImageIcon[] icons = {
        ImageUtil.getScaledImage("assets/icon.png", 48, 48),
        ImageUtil.getScaledImage("assets/home.png", 32, 32),
        ImageUtil.getScaledImage("assets/settings.png", 32, 32),
        ImageUtil.getScaledImage("assets/output.png", 32, 32),
    };

    public Window() {
        super("Pulsar Launcher");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 550);
        this.setLocationRelativeTo(null);

        this.about = new AboutPanel();

        this.home = new HomePanel();
        this.settings = new SettingsPanel();
        this.output = new OutputPanel();

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        this.list = new JList(icons);
        ((DefaultListCellRenderer)list.getCellRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        list.setFixedCellWidth(64);
        list.setFixedCellHeight(64);
        list.setSelectedIndex(1);
        list.setSelectionMode(SelectionMode.SINGLE.ordinal());
        list.setDragEnabled(false);
        list.addListSelectionListener(this);

        sidePanel.setBackground(list.getBackground());
        sidePanel.add(list);

        this.add(sidePanel, BorderLayout.WEST);

        // Set panel to home panel
        setupHome();
        add(this.home);

        Launcher.getInstance().getConfigManager().load(this);

        this.setVisible(true);

    }

    private void setupHome() {
        remove(this.settings);
        remove(this.output);
        remove(this.about);
        add(this.home);
        validate();
        repaint();
    }

    private void setupSettings() {
        remove(this.home);
        remove(this.output);
        remove(this.about);
        add(this.settings);
        validate();
        repaint();
    }

    private void setupOutput() {
        remove(this.home);
        remove(this.settings);
        remove(this.about);
        add(this.output);
        validate();
        repaint();
    }

    private void setupAbout() {
        remove(this.home);
        remove(this.settings);
        remove(this.output);
        add(this.about);
        validate();
        repaint();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        switch(list.getSelectedIndex()) {
        case 0:
            setupAbout();
            break;
        case 1:
            setupHome();
            break;
        case 2:
            setupSettings();
            break;
        case 3:
            setupOutput();
            break;
        }
    }

}
