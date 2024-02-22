package me.geuxy.utils.system;

import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public enum OSUtil {

    WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".minecraft" + File.separator),
    MAC("Library" + File.separator + "Application Support" + File.separator + "minecraft" + File.separator),
    LINUX(".minecraft" + File.separator);

    private final String directory;

    public static OSUtil getOS() {
        String osName = System.getProperty("os.name").toLowerCase();

        return osName.contains("windows") ? WINDOWS : osName.contains("mac") ? MAC : LINUX;
    }

    public String getMinecraft() {
        return System.getProperty("user.home") + File.separator + directory;
    }

}
