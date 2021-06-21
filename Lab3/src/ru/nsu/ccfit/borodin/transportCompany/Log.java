package ru.nsu.ccfit.borodin.transportCompany;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void warning(String msg) {
        logger.log(Level.WARNING, msg);
    }

    public static void severe(String msg, Throwable ex) {
        logger.log(Level.SEVERE, msg, ex);
    }

}
