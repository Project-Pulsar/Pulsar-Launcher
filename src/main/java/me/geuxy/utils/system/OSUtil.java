package me.geuxy.utils.system;

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

    public String getMinecraft() {
        return System.getProperty("user.home") + File.separator + directory;
    }

    public String getPulsar() {
        return System.getProperty("user.home") + File.separator + directory.replace("minecraft", "pulsarlauncher").replace(".pulsarlauncher", "pulsarlauncher");
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

}
