package me.geuxy.main;

import me.geuxy.Launcher;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Launcher.INSTANCE::init);
    }

}
