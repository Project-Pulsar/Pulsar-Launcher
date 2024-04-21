package me.geuxy.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.geuxy.Launcher;
import me.geuxy.config.Config;
import me.geuxy.gui.Window;

public final class LaunchAction implements ActionListener {

    private final Window window;

    public LaunchAction(Window window) {
        this.window = window;
    }

    public void actionPerformed(ActionEvent e) {
        if(this.window.isHide()) {
            this.window.setVisible(false);
        }

        int[] fixedRam = getFixedRam();

        Launcher.getInstance().getConfigManager().save(new Config(fixedRam[0], fixedRam[1], this.window.isHide()));

        new Thread(() -> Launcher.getInstance().startClient(getFixedRam())).start();

        Runtime.getRuntime().gc();

        this.window.setVisible(true);
    }

    private int[] getFixedRam() {
        int min = this.window.getMinRam();
        int max = this.window.getMaxRam();

        if(min > max) {
            int tempMin = min;
            min = max;
            max = tempMin;
        }
        return new int[]{min, max};
    }

}
