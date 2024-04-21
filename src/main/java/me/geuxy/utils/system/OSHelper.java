package me.geuxy.utils.system;

import java.io.File;

public enum OSHelper {
    WINDOWS("AppData", "Roaming", ".minecraft"),
    MAC("Library", "Application Support", "minecraft"),
    LINUX(".minecraft");

    private final String directory;

    OSHelper(String... dir) {
        StringBuilder builder = new StringBuilder();

        for(String d : dir) {
            builder.append(d).append(File.separator);
        }
        this.directory = builder.toString();
    }

    public static OSHelper getOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows") ? WINDOWS : (osName.contains("mac") ? MAC : LINUX);
    }

    public String getMinecraft() {
        return System.getProperty("user.home") + File.separator + directory;
    }

    public static String getOSName() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows") ? "windows" : (osName.contains("mac") ? "macos" : "linux");
    }

}
