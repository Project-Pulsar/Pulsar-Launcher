package me.geuxy.utils.system;

import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public enum OSHelper {

    /*
     * Paths to minecraft directory
     */
    WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".minecraft" + File.separator),
    MAC("Library" + File.separator + "Application Support" + File.separator + "minecraft" + File.separator),
    LINUX(".minecraft" + File.separator);

    private final String directory;

    /**
     * Gets the operating system you are running
     *
     * @return operating system as enum
     */
    public static OSHelper getOS() {
        String osName = System.getProperty("os.name").toLowerCase();

        return osName.contains("windows") ? WINDOWS : osName.contains("mac") ? MAC : LINUX;
    }

    /**
     * Gets the users minecraft directory
     *
     * @return path of minecraft directory
     */
    public String getMinecraft() {
        return System.getProperty("user.home") + File.separator + directory;
    }

}
