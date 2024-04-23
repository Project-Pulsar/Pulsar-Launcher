package me.geuxy.gui.panels.main;

import lombok.Getter;
import lombok.Setter;

import me.geuxy.config.Config;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

@Getter @Setter
public final class SettingsPanel extends JPanel {

    private final JLabel minLabel;
    private final JLabel maxLabel;

    private final JSlider minRamSlider;
    private final JSlider maxRamSlider;

    private final JCheckBox hideOnLaunch;
    private final JCheckBox singleThreaded;

    private final JTextField mcPath;

    private int minRam = 1;
    private int maxRam = 1;

    private boolean hide;
    private boolean isSingleThread;

    public SettingsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        this.add(minLabel = new JLabel(getMinRamText()));

        // Minimum ram slider
        this.minRamSlider = new JSlider(1, 8);
        this.minRamSlider.addChangeListener(onMinRamChange());
        this.add(this.minRamSlider);

        this.add(maxLabel = new JLabel(getMaxRamText()));

        // Maximum ram slider
        this.maxRamSlider = new JSlider(1, 8);
        this.maxRamSlider.addChangeListener(onMaxRamChange());
        this.add(this.maxRamSlider);

        this.mcPath = new JTextField();
        this.mcPath.setMaximumSize(new Dimension(400, 30));
        this.add(new JLabel("Minecraft path"));
        this.add(this.mcPath);

        // Hide on launch checkbox
        this.hideOnLaunch = new JCheckBox("Hide on launch");
        this.hideOnLaunch.addActionListener(e -> this.hide = !this.hide);
        this.add(this.hideOnLaunch);

        // Single threaded
        this.singleThreaded = new JCheckBox("Single threaded");
        this.singleThreaded.addActionListener(e -> this.isSingleThread = !this.isSingleThread);
        this.add(this.singleThreaded);
    }


    private String getMinRamText() {
        return "Minimum Ram (" + minRam + "GB)";
    }

    private String getMaxRamText() {
        return "Maximum Ram (" + maxRam + "GB)";
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

    public void updateSettings(Config config) {
        this.minRam = config.getMinRam();
        this.maxRam = config.getMaxRam();
        this.minRamSlider.setValue(this.minRam);
        this.maxRamSlider.setValue(this.maxRam);
        this.onMinRamChange().stateChanged(null);
        this.onMaxRamChange().stateChanged(null);

        this.hide = config.isHide();
        this.hideOnLaunch.setSelected(this.hide);

        this.mcPath.setText(config.getMcPath());

        this.isSingleThread = config.isSingleThreaded();
        this.singleThreaded.setSelected(this.isSingleThread);
    }

}
