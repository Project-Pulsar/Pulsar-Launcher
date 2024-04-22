package me.geuxy.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import javafx.scene.control.SelectionMode;

import lombok.Getter;

import me.geuxy.Launcher;
import me.geuxy.actions.LaunchAction;
import me.geuxy.config.Config;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.render.ImageUtil;

@Getter
public final class Window extends JFrame implements ListSelectionListener {

    private final JPanel homePanel;
    private final JPanel settingsPanel;

    private final JLabel minLabel;
    private final JLabel maxLabel;

    private final JSlider minRamSlider;
    private final JSlider maxRamSlider;

    private final JCheckBox hideOnLaunch;
    private final JCheckBox singleThreaded;

    private int minRam = 1;
    private int maxRam = 1;

    private boolean hide;
    private boolean isSingleThread;

    private final OutputPanel output;
    private final AboutPanel about;

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

        this.homePanel = new JPanel();
        this.homePanel.setPreferredSize(new Dimension(getWidth() - 10, 140));
        this.homePanel.setLayout(new BorderLayout(10, 10));
        this.homePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        Font font = new Font("Arial", Font.PLAIN, 20);

        // News
        JTextPane news = new JTextPane() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0xFF4B6EAF));
                g2.fillRect(0, 0, 2, this.getHeight());
                g2.dispose();
            }
        };
        news.setEditorKit(new HTMLEditorKit());
        news.setText(FileUtil.read(Launcher.getInstance().getGithubAPI().CHANGELOG_URL, "<br>"));
        news.setEditable(false);
        news.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(news);
        scrollPane.setFocusable(false);
        this.homePanel.add(scrollPane);

        // Launch button
        JButton launchButton = new JButton("Launch");
        launchButton.setFocusPainted(false);
        launchButton.setFont(font);
        launchButton.addActionListener(new LaunchAction(this));

        this.homePanel.add(launchButton, BorderLayout.SOUTH);

        // Settings panel
        this.settingsPanel = new JPanel();
        this.settingsPanel.setPreferredSize(new Dimension(getWidth() - 10, 140));
        this.settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        this.settingsPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        this.settingsPanel.add(minLabel = new JLabel(getMinRamText()));

        // Minimum ram slider
        this.minRamSlider = new JSlider(1, 8);
        this.minRamSlider.addChangeListener(onMinRamChange());
        this.settingsPanel.add(this.minRamSlider);

        this.settingsPanel.add(maxLabel = new JLabel(getMaxRamText()));

        // Maximum ram slider
        this.maxRamSlider = new JSlider(1, 8);
        this.maxRamSlider.addChangeListener(onMaxRamChange());
        this.settingsPanel.add(this.maxRamSlider);

        // Hide on launch checkbox
        this.hideOnLaunch = new JCheckBox("Hide on launch");
        this.hideOnLaunch.addActionListener(e -> this.hide = !this.hide);
        this.settingsPanel.add(this.hideOnLaunch);

        // Single threaded
        this.singleThreaded = new JCheckBox("Single threaded");
        this.singleThreaded.addActionListener(e -> this.isSingleThread = !this.isSingleThread);
        this.settingsPanel.add(this.singleThreaded);

        // Set panel to home panel
        setupHome();
        add(this.homePanel);

        Launcher.getInstance().getConfigManager().load(this);

        this.setVisible(true);

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

    private void setupHome() {
        remove(this.settingsPanel);
        remove(this.output);
        remove(this.about);
        add(this.homePanel);
        validate();
        repaint();
    }

    private void setupSettings() {
        remove(this.homePanel);
        remove(this.output);
        remove(this.about);
        add(this.settingsPanel);
        validate();
        repaint();
    }

    private void setupOutput() {
        remove(this.homePanel);
        remove(this.settingsPanel);
        remove(this.about);
        add(this.output);
        validate();
        repaint();
    }

    private void setupAbout() {
        remove(this.homePanel);
        remove(this.settingsPanel);
        remove(this.output);
        add(this.about);
        validate();
        repaint();
    }

    public void loadConfig(Config config) {
        this.minRam = config.getMinRam();
        this.maxRam = config.getMaxRam();
        this.minRamSlider.setValue(this.minRam);
        this.maxRamSlider.setValue(this.maxRam);
        this.onMinRamChange().stateChanged(null);
        this.onMaxRamChange().stateChanged(null);

        this.hide = config.isHide();
        this.hideOnLaunch.setSelected(this.hide);

        this.isSingleThread = config.isSingleThreaded();
        this.singleThreaded.setSelected(this.isSingleThread);
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
