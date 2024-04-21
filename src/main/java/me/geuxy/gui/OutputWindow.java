package me.geuxy.gui;

import javax.swing.*;

public class OutputWindow extends JFrame {

    private final JTextArea textArea;

    public OutputWindow() {
        this.setTitle("Console output");
        this.setSize(600, 450);
        this.setLocationRelativeTo(null);

        this.textArea = new JTextArea();
        this.textArea.setEditable(false);

        JScrollPane pane = new JScrollPane(textArea);

        this.add(pane);
    }

    public void append(String str) {
        if(textArea != null) {
            this.textArea.append(str + "\n");
        }
    }

    public void clear() {
        this.textArea.setText("");
    }

}
