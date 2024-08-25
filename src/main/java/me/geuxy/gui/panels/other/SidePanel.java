package me.geuxy.gui.panels.other;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public final class SidePanel extends JPanel {

    private final JList<Object> list;

    private final int listCellSize = 64;

    public SidePanel(ImageIcon[] icons, ListSelectionListener listener) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        this.list = new JList<>(icons);
        ((DefaultListCellRenderer)list.getCellRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        list.setFixedCellWidth(listCellSize);
        list.setFixedCellHeight(listCellSize);
        list.setSelectedIndex(1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setDragEnabled(false);
        list.addListSelectionListener(listener);
        list.setSelectionBackground(new Color(0xFF64C3FF));

        this.setBackground(list.getBackground());
        this.add(list);
    }

    public int getValue() {
        return list.getSelectedIndex();
    }

}
