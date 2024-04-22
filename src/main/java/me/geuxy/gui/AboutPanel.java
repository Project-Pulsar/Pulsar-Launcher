package me.geuxy.gui;

import me.geuxy.Launcher;
import me.geuxy.utils.render.ImageUtil;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

public class AboutPanel extends JPanel {

    public AboutPanel() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        this.add(new JLabel(ImageUtil.getScaledImage("assets/icon.png", 128, 128), JLabel.CENTER), BorderLayout.NORTH);

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

        // hardcode this because why not :)
        StringBuilder pulsar = new StringBuilder("Pulsar is a utility mod for minecraft providing high customizability, performance, stability, and bypasses to dominate most clients and anti-cheats with ease.<br>");

        for(String s : new String[] {
            "<b>Credits</b>",
            "Geuxy - Owner",
            "Kolotheegg - Developer",
            "Tabio - Old Developer",
            "Liticane - External Developer",
            "MoonX devs - External Developers (for font renderer)",
            "FelixH2012 - External Developer (for lambda event system)"
        }) {
            pulsar.append("<br>").append(s);
        }

        pulsar.append("<br><br><b>Launcher Version:</b> ").append(Launcher.getInstance().getVersion());

        news.setText(pulsar.toString());

        news.setEditable(false);
        news.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(news);
        scrollPane.setFocusable(false);
        this.add(scrollPane);
    }

}
