package me.geuxy.utils;

import java.io.File;

public enum OSUtil {

    WINDOWS("AppData/Roaming/.minecraft/"),
    MAC("Library/Application Support/minecraft/"),
    LINUX(".minecraft/");

    private final String directory;

    OSUtil(String directory) {
        this.directory = directory.replace("/", File.separator);
    }

    public static OSUtil getOS() {
        String osName = System.getProperty("os.name").toLowerCase();

        return osName.contains("windows") ? WINDOWS : osName.contains("mac") ? MAC : LINUX;
    }

    public String getDirectory() {
        return System.getProperty("user.home") + File.separator + directory;
    }

}
