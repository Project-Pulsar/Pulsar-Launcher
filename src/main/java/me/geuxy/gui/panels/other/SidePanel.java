package me.geuxy.gui.panels.other;

import javafx.scene.control.SelectionMode;

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
        list.setSelectionMode(SelectionMode.SINGLE.ordinal());
        list.setDragEnabled(false);
        list.addListSelectionListener(listener);

        this.setBackground(list.getBackground());
        this.add(list);
    }

    public int getValue() {
        return list.getSelectedIndex();
    }

}
