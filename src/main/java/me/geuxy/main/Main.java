package me.geuxy.main;

import com.formdev.flatlaf.FlatDarculaLaf;

import me.geuxy.Launcher;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.system.OSUtil;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            if(OSUtil.isLinux()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

            } else {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            }

        } catch(Exception e) {
            Logger.error("Failed to apply theme");
        }

        Launcher.INSTANCE.init();
    }

}
