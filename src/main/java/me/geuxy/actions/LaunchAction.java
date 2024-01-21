package me.geuxy.actions;

import lombok.RequiredArgsConstructor;

import me.geuxy.Launcher;
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

        int minimumRam = window.getMinimumRam();
        int maximumRam = window.getMaximumRam();

        if(minimumRam > maximumRam) {
            int tempMinimumRam = minimumRam;

            minimumRam = maximumRam;
            maximumRam = tempMinimumRam;
        }

        Launcher.INSTANCE.getConfig().save(Launcher.INSTANCE.getDirectory(), minimumRam, maximumRam, window.isHide());

        Launcher.INSTANCE.startClient(minimumRam, maximumRam);

        window.setVisible(true);
    }

}
