package me.geuxy.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public final class OutputPanel extends JPanel {

    private final JTextArea textArea;

    public OutputPanel() {
        this.setSize(600, 450);
        this.setLayout(new BorderLayout());

        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setFocusable(false);

        JScrollPane pane = new JScrollPane(textArea) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if(textArea.getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g;

                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    String text = "No Output";

                    g2.setFont(new Font("Arial", Font.PLAIN, 40));
                    Rectangle2D rect = g2.getFontMetrics(g2.getFont()).getStringBounds(text, g2);

                    g2.drawString(text, (int) (getX() + (getWidth() / 2) - (rect.getWidth() / 2)), (getY() + (getHeight() / 2) + 12));

                    g2.dispose();
                }
            }
        };
        pane.setFocusable(false);

        this.add(pane);
    }

    /*
     * Creates a new line of text to the output
     */
    public void append(String str) {
        if(textArea != null) {
            this.textArea.append(str + "\n");
        }
    }

    /*
     * Deletes all output
     */
    public void clear() {
        this.textArea.setText("");
    }

}
