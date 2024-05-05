package me.geuxy.utils.swing;

import javax.swing.*;

public class SwingUtil {

    public static void showErrorPopup(String title, String message) {
        JOptionPane.showConfirmDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoPopup(String title, String message) {
        JOptionPane.showConfirmDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

}
