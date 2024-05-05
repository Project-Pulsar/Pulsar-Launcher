package me.geuxy.gui.panels.main;

import lombok.Getter;
import lombok.Setter;

import me.geuxy.Launcher;
import me.geuxy.config.Config;
import me.geuxy.utils.swing.SwingUtil;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.util.stream.Stream;

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
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Minecraft Path Label
        JLabel mcPathLabel = new JLabel("Minecraft Path");
        mcPathLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Minecraft Path
        this.mcPath = new JTextField();
        this.mcPath.setMaximumSize(new Dimension(400, 30));
        this.mcPath.setAlignmentX(LEFT_ALIGNMENT);

        // Minimum Ram Label
        minLabel = new JLabel(getMinRamText());
        minLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Minimum Ram Slider
        this.minRamSlider = new JSlider(1, 8);
        this.minRamSlider.setMaximumSize(new Dimension(400, 40));
        this.minRamSlider.addChangeListener(onMinRamChange());
        this.minRamSlider.setAlignmentX(LEFT_ALIGNMENT);

        // Maximum Ram Label
        maxLabel = new JLabel(getMaxRamText());
        maxLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Maximum ram slider
        this.maxRamSlider = new JSlider(1, 8);
        this.maxRamSlider.addChangeListener(onMaxRamChange());
        this.maxRamSlider.setAlignmentX(LEFT_ALIGNMENT);
        this.maxRamSlider.setMaximumSize(new Dimension(400, 40));

        // Hide on launch checkbox
        this.hideOnLaunch = new JCheckBox("Hide on launch");
        this.hideOnLaunch.addActionListener(e -> this.hide = !this.hide);
        this.hideOnLaunch.setAlignmentX(LEFT_ALIGNMENT);

        // Single threaded
        this.singleThreaded = new JCheckBox("Single threaded");
        this.singleThreaded.addActionListener(e -> this.isSingleThread = !this.isSingleThread);
        this.singleThreaded.setAlignmentX(LEFT_ALIGNMENT);

        // Regenerate Natives
        JButton regenNatives = new JButton("Regenerate Natives");
        regenNatives.addActionListener(e -> Launcher.getInstance().regenerateNatives());
        regenNatives.setAlignmentX(LEFT_ALIGNMENT);

        // Clear Cache
        JButton clearCache = new JButton("Clear Cache");
        clearCache.addActionListener(e -> Launcher.getInstance().clearCache());
        clearCache.setAlignmentX(LEFT_ALIGNMENT);

        Component[][] comps = {
            {mcPathLabel, mcPath},
            {minLabel, minRamSlider},
            {maxLabel, maxRamSlider},
            {hideOnLaunch},
            {singleThreaded},
            {regenNatives},
            {clearCache},
        };

        for(Component[] compList : comps) {
            for(Component comp : compList) {
                this.add(comp);
            }

            this.add(Box.createRigidArea(new Dimension(0, 8)));
        }
    }

    private String getMinRamText() {
        return "Minimum Ram (" + minRam + "GB)";
    }

    private String getMaxRamText() {
        return "Maximum Ram (" + maxRam + "GB)";
    }

    private ChangeListener onMinRamChange() {
        return ignored -> {
            this.minRam = minRamSlider.getValue();
            this.minLabel.setText(getMinRamText());
        };
    }

    private ChangeListener onMaxRamChange() {
        return ignored -> {
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
