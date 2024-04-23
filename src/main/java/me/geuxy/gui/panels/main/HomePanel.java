package me.geuxy.gui.panels.main;

import me.geuxy.Launcher;
import me.geuxy.actions.LaunchAction;
import me.geuxy.gui.Window;
import me.geuxy.utils.file.FileUtil;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

public final class HomePanel extends JPanel {

    public HomePanel(Window window) {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

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
        this.add(scrollPane);

        // Launch button
        JButton launchButton = new JButton("Launch");
        launchButton.setFocusPainted(false);
        launchButton.setFont(new Font("Arial", Font.PLAIN, 20));
        launchButton.addActionListener(new LaunchAction(window));

        this.add(launchButton, BorderLayout.SOUTH);
    }
}
