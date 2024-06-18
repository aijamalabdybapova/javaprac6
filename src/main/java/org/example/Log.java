package org.example;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public Logger logger;
    FileHandler fileHandler;

    public Log(String loggerFile) throws SecurityException, IOException {
        File file = new File(loggerFile);
        fileHandler = new FileHandler(loggerFile, true);
        logger = Logger.getLogger(loggerFile);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }
}