package me.geuxy.utils.console;

import org.apache.logging.log4j.LogManager;

public class Logger {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static void debug(Object object) {
        logger.info(object);
    }

    public static void info(Object object) {
        logger.info(object);
    }

    public static void warn(Object object) {
        logger.warn(object);
    }

    public static void error(Object object) {
        logger.error(object);
    }

}
