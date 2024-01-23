package me.geuxy.utils.console;

public class Logger {

    public static void debug(Object object) {
        System.out.println("[PulsarLauncher/DEBUG] " + object);
    }

    public static void info(Object object) {
        System.out.println("[PulsarLauncher/INFO] " + object);
    }

    public static void warn(Object object) {
        System.out.println("[PulsarLauncher/WARNING] " + object);
    }

    public static void error(Object object) {
        System.err.println("[PulsarLauncher/ERROR] " + object);
    }

}
