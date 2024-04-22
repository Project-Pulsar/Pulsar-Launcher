package me.geuxy.api;

import javafx.scene.shape.DrawMode;
import me.geuxy.utils.system.OSHelper;

public class GithubAPI {

    public final String RAW_URL = "https://github.com/Project-Pulsar/Cloud/raw/main/PulsarLauncher/";
    public final String BIN_URL = RAW_URL + "bin/";
    public final String VERSION_URL = RAW_URL + "versions/";
    public final String CHANGELOG_URL = "https://raw.githubusercontent.com/Project-Pulsar/Cloud/main/PulsarLauncher/changelog.txt";

    public String getNativesByOS() {
        String extension = ".zip";

        return BIN_URL + OSHelper.getOSName() + extension;
    }

}
