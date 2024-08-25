package me.geuxy.gui;

import me.geuxy.Launcher;
import me.geuxy.utils.render.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class SplashWindow extends JFrame {

    public SplashWindow() {
        super(Launcher.getInstance().getName() + " - Splash");
        this.setSize(360, 360);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        ImageIcon clientIcon = ImageUtil.getScaledImage("assets/icon.png", 180, 180);

        panel.add(new JLabel(clientIcon, JLabel.CENTER), BorderLayout.CENTER);

        this.add(panel);
        this.setVisible(true);

        panel.paintImmediately(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
    }

}
