package me.geuxy.gui.panels.main;

import me.geuxy.Launcher;
import me.geuxy.utils.render.ImageUtil;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

public final class AboutPanel extends JPanel {

    // hardcode this because why not :)
    private final String[][] credits = {
        {"Geuxy", "Owner"},
        {"Kolotheegg", "Developer"},
        {"Tabio", "Old Developer"},
        {"Liticane", "External Developer"},
        {"MoonX Devs", "External Developers"},
        {"FelixH2012", "External Developer"}
    };

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

        StringBuilder pulsar = new StringBuilder("Pulsar is a utility mod for minecraft providing high customizability, performance,<br> stability, and bypasses to dominate most anti-cheats with ease.<br>");

        pulsar.append("<br><b>Credits:</b><br>");

        for(String[] s : credits) {
            pulsar.append("<b>").append(s[0]).append("</b> - ").append(s[1]).append("<br>");
        }

        pulsar.append("<br><b>Launcher Version:</b> ").append(Launcher.getInstance().getVersion());

        news.setText(pulsar.toString());

        news.setEditable(false);
        news.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(news);
        scrollPane.setFocusable(false);
        this.add(scrollPane);
    }

}
