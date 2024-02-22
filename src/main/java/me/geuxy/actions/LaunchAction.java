package me.geuxy.actions;

import lombok.RequiredArgsConstructor;

import me.geuxy.Launcher;
import me.geuxy.gui.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@RequiredArgsConstructor
public class LaunchAction implements ActionListener {

    private final Window window;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(window.isHide()) {
            window.setVisible(false);
        }

        int minimumRam = window.getMinimumRam();
        int maximumRam = window.getMaximumRam();

        if(minimumRam > maximumRam) {
            int tempMinimumRam = minimumRam;

            minimumRam = maximumRam;
            maximumRam = tempMinimumRam;
        }

        Launcher.INSTANCE.getConfig().save(new File("config.json"), minimumRam, maximumRam, window.isHide());

        new Thread(() -> Launcher.INSTANCE.startClient(getFixedRam()[0], getFixedRam()[1])).start();

        window.setVisible(true);
    }

    private int[] getFixedRam() {
        int minimumRam = window.getMinimumRam();
        int maximumRam = window.getMaximumRam();

        if(minimumRam > maximumRam) {
            int tempMinimumRam = minimumRam;

            minimumRam = maximumRam;
            maximumRam = tempMinimumRam;
        }

        return new int[] {minimumRam, maximumRam};
    }

}
