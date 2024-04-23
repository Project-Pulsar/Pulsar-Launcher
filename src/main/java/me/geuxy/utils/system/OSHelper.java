package me.geuxy.utils.system;

import java.io.File;

public enum OSHelper {

    /*
     * Supported Operating Systems
     */
    WINDOWS("AppData", "Roaming", ".minecraft"),
    MAC("Library", "Application Support", "minecraft"),
    LINUX(".minecraft");

    /*
     * OS Minecraft folder directory
     */
    private final String directory;

    OSHelper(String... dir) {
        StringBuilder builder = new StringBuilder();

        for(String d : dir) {
            builder.append(d).append(File.separator);
        }
        this.directory = builder.toString();
    }

    /**
     * Gets the users operating system
     *
     * @return OS enum that matches os.name property
     */
    public static OSHelper getOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows") ? WINDOWS : (osName.contains("mac") ? MAC : LINUX);
    }

    /**
     * Gets the Minecraft folder directory
     *
     * @return minecraft folder directory
     */
    public String getMinecraft() {
        return System.getProperty("user.home") + File.separator + directory;
    }

    /**
     * Gets the name of an operating system
     *
     * @return name of OS
     */
    public static String getOSName() {
        String osName = System.getProperty("os.name").toLowerCase();

        return osName.contains("windows") ? "windows" : (osName.contains("mac") ? "macos" : "linux");
    }

}
