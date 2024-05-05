package me.geuxy.gui;

import me.geuxy.Launcher;
import me.geuxy.utils.render.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class SplashWindow extends JFrame {

    private final JProgressBar progress;

    public SplashWindow() {
        super(Launcher.getInstance().getName() + " - Splash");
        this.setSize(420, 240);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        this.progress = new JProgressBar();

        panel.add(new JLabel(ImageUtil.getScaledImage("assets/icon.png", 180, 180), JLabel.CENTER), BorderLayout.CENTER);
        panel.add(progress, BorderLayout.SOUTH);

        this.add(panel);
        this.setVisible(true);
        panel.paintImmediately(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
    }

    public void setProgress(int progress) {
        this.progress.setValue(progress);
        this.progress.paintImmediately(this.progress.getX(), this.progress.getY(), this.progress.getWidth(), this.progress.getHeight());
    }

}
