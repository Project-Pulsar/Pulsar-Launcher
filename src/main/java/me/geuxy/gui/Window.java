package me.geuxy.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lombok.Getter;

import me.geuxy.Launcher;
import me.geuxy.gui.panels.main.AboutPanel;
import me.geuxy.gui.panels.main.HomePanel;
import me.geuxy.gui.panels.main.OutputPanel;
import me.geuxy.gui.panels.main.SettingsPanel;
import me.geuxy.gui.panels.other.SidePanel;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.render.ImageUtil;

@Getter
public final class Window extends JFrame implements ListSelectionListener {

    /*
     * Main panels
     */
    private final HomePanel home;
    private final AboutPanel about;
    private final SettingsPanel settings;
    private final OutputPanel output;

    /*
     * Side panel
     */
    private final SidePanel sidePanel;

    private final JScrollPane scrollPane;

    /*
     * Icons to be on the side panel
     */
    private final ImageIcon[] icons = {
        ImageUtil.getScaledImage("assets/icon.png", 48, 48),
        ImageUtil.getScaledImage("assets/home.png", 32, 32),
        ImageUtil.getScaledImage("assets/settings.png", 32, 32),
        ImageUtil.getScaledImage("assets/output.png", 32, 32),
    };

    /*
     * Window setup
     */
    public Window(SplashWindow splash) {
        super("Pulsar Launcher");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 550);
        this.setLocationRelativeTo(null);

        // Setup main panels
        this.about = new AboutPanel();
        this.home = new HomePanel(this);
        this.settings = new SettingsPanel();
        this.output = new OutputPanel();

        // Setup side panel
        this.sidePanel = new SidePanel(icons, this);
        this.add(sidePanel, BorderLayout.WEST);

        // Load configuration
        Launcher.getInstance().getConfigManager().load(this);

        // Finish window
        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.home);
        this.add(this.scrollPane);
        this.setVisible(true);

        splash.dispose();
    }

    /*
     * Changes main panel like a book with pages
     */
    private void setPanel(JPanel panel) {
        scrollPane.setViewportView(panel);
    }

    /*
     * Changes the main panel when list selection is updated
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selected = sidePanel.getValue();

        this.setPanel(switch(selected) {
            case -1 -> null;
            case 0 -> this.about;
            case 1 -> this.home;
            case 2 -> this.settings;
            case 3 -> this.output;
            default -> throw new IllegalStateException("Unexpected value: " + selected);
        });

        this.validate();
        this.repaint();
    }

}
