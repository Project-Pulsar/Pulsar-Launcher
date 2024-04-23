package me.geuxy.api;

import me.geuxy.Launcher;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.system.OSHelper;

public final class GithubAPI {

    /*
     * All URLs the launcher uses
     */
    public final String RAW_URL = "https://github.com/Project-Pulsar/Cloud/raw/main/PulsarLauncher/";
    public final String BIN_URL = RAW_URL + "bin/";
    public final String CHANGELOG_URL = RAW_URL + "changelog.txt";
    public final String LIBRARIES_URL = RAW_URL + "libraries.json";

    /**
     * Gets the required natives depending on the users OS
     *
     * @return url link to the compressed bin zip file
     */
    public String getNativesByOS() {
        String extension = ".zip";

        return BIN_URL + OSHelper.getOSName() + extension;
    }

    /**
     * Gets the JSON contents as a string
     *
     * @return contents of the libraries JSON
     */
    public String getLibrariesJson() {
        return FileUtil.read(Launcher.getInstance().getGithubAPI().LIBRARIES_URL);
    }

}
