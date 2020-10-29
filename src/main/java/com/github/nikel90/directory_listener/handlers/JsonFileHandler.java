package com.github.nikel90.directory_listener.handlers;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonFileHandler implements FileHandler {
    static Logger LOGGER = Logger.getLogger(JsonFileHandler.class.getName());
    private final File file;


    public JsonFileHandler(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "JsonFileHandler is launched: " + file.getName());
        LocalDateTime startTimeProcess = LocalDateTime.now();
        LOGGER.log(Level.INFO, "Start time process - " + startTimeProcess.toString());


        int countLine = 0;
        try {
            FileReader reader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            while(lineNumberReader.readLine() != null) {
                countLine++;
            }
            LOGGER.log(Level.INFO, "Number of lines in the file <" + file.getName() + "> = " + countLine);
            LocalDateTime endTimeProcessing = LocalDateTime.now();
            Duration duration = Duration.between(endTimeProcessing, startTimeProcess);
            LOGGER.log(Level.INFO, "Total time process - " + duration);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE,"Exception - " + exception.getMessage() + " Cause - " + exception.getCause());
        }
    }
}