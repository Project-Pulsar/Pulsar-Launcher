package me.geuxy.gui.panels.other;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public final class SidePanel extends JPanel {

    private final JList<Object> list;

    public SidePanel(ImageIcon[] icons, ListSelectionListener listener) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        this.list = new JList<>(icons);
        ((DefaultListCellRenderer)list.getCellRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        list.setFixedCellWidth(64);
        list.setFixedCellHeight(64);
        list.setSelectedIndex(1);
        list.setSelectionMode(0);
        list.setDragEnabled(false);
        list.addListSelectionListener(listener);
        list.setSelectionBackground(0xFF64C3FF);

        this.setBackground(list.getBackground());
        this.add(list);
    }

    public int getValue() {
        return list.getSelectedIndex();
    }

}
