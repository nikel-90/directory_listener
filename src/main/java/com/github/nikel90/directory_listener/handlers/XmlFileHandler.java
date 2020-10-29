package com.github.nikel90.directory_listener.handlers;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlFileHandler implements FileHandler {
    private static final Logger LOGGER = Logger.getLogger(XmlFileHandler.class.getName());
    private final File file;

    public XmlFileHandler(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        LOGGER.info("XmlFileHandler is launched: " + file.getName());
        LocalDateTime startTimeProcess = LocalDateTime.now();
        LOGGER.info("Start time process - " + startTimeProcess.toString());

        int countLine = 0;
        try {
            FileReader reader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            while(lineNumberReader.readLine() != null) {
                countLine++;
            }
            LOGGER.info("Number of lines in the file = " + countLine);
            LocalDateTime endTimeProcessing = LocalDateTime.now();
            Duration duration = Duration.between(endTimeProcessing, startTimeProcess);
            LOGGER.info("Total time process - " + duration);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE,"Exception - " + exception.getMessage() + " Cause - " + exception.getCause());
        }
    }
}
