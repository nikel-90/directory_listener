package com.github.nikel90.directory_listener.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class RemoverHandler implements FileHandler {
    private static final Logger LOGGER = Logger.getLogger(RemoverHandler.class.getName());
    private final File file;

    public RemoverHandler(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        LOGGER.info("RemoverHandler is launched: " + file.getName());
        LocalDateTime startTimeProcessing = LocalDateTime.now();
        LOGGER.info("Start time removing - " + startTimeProcessing.toString());

        try {
            Files.delete(Paths.get(file.getPath()));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE,"Exception - " + exception.getMessage() + " Cause - " + exception.getCause());
        }

        if(!file.exists()) {
            LocalDateTime endTimeProcessing = LocalDateTime.now();
            Duration duration = Duration.between(endTimeProcessing, startTimeProcessing);
            LOGGER.info("Total time removing - " + duration);
        } else {
            LOGGER.severe("The file is not deleted.");
        }
    }


}