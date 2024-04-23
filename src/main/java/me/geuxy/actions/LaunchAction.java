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

    /*
     * Action performed after the launch button is interacted with
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int[] fixedRam = getFixedRam();

        boolean singleThreaded = window.getSettings().isSingleThread();
        boolean hide = window.getSettings().isHide();
        String mcPath = window.getSettings().getMcPath().getText();

        Launcher.getInstance().getConfigManager().save(new Config(fixedRam[0], fixedRam[1], mcPath, hide, singleThreaded));

        Runnable runnable = () -> Launcher.getInstance().startClient(getFixedRam());

        if(singleThreaded) {
            runnable.run();

        } else {
            new Thread(runnable).start();
        }
    }

    /**
     * Get fixed RAM values (fixes min from being larger than max)
     *
     * @return fixed RAM values
     */
    private int[] getFixedRam() {
        int min = this.window.getSettings().getMinRam();
        int max = this.window.getSettings().getMaxRam();

        if(min > max) {
            int tempMin = min;
            min = max;
            max = tempMin;
        }
        return new int[] {min, max};
    }

}
