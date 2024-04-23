package me.geuxy.utils.console;

import lombok.experimental.UtilityClass;

import org.apache.logging.log4j.LogManager;

@UtilityClass
public class Logger {

    /*
     * Log4J Logger
     */
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);

    /**
     * Prints a debug message
     *
     * @param object message to print to console
     */
    public static void debug(Object object) {
        logger.info("[DEBUG] {}", object);
    }

    /**
     * Prints an info message
     *
     * @param object message to print to console
     */
    public static void info(Object object) {
        logger.info(object);
    }

    /**
     * Prints a warning message
     *
     * @param object message to print to console
     */
    public static void warn(Object object) {
        logger.warn(object);
    }

    /**
     * Prints an error message
     *
     * @param object message to print to console
     */
    public static void error(Object object) {
        logger.error(object);
    }

}
