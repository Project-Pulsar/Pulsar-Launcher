package me.geuxy.actions;

import lombok.RequiredArgsConstructor;

import me.geuxy.Launcher;
import me.geuxy.config.Config;
import me.geuxy.gui.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class LaunchAction implements ActionListener {

    private final Window window;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(window.isHide()) {
            window.setVisible(false);
        }

        int minimumRam = window.getMinRam();
        int maximumRam = window.getMaxRam();

        if(minimumRam > maximumRam) {
            int tempMinimumRam = minimumRam;

            minimumRam = maximumRam;
            maximumRam = tempMinimumRam;
        }

        Launcher.getInstance().getConfigManager().save(new Config(window.getMinRam(), window.getMaxRam(), window.isHide()));

        new Thread(() -> Launcher.getInstance().startClient(getFixedRam())).start();

        window.setVisible(true);
    }

    private int[] getFixedRam() {
        int minimumRam = window.getMinRam();
        int maximumRam = window.getMaxRam();

        if(minimumRam > maximumRam) {
            int tempMinimumRam = minimumRam;

            minimumRam = maximumRam;
            maximumRam = tempMinimumRam;
        }

        return new int[] {minimumRam, maximumRam};
    }

}
