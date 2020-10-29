package com.github.nikel90.directory_listener.listener;

import com.github.nikel90.directory_listener.handlers.JsonFileHandler;
import com.github.nikel90.directory_listener.handlers.RemoverHandler;
import com.github.nikel90.directory_listener.handlers.XmlFileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryListener {
    private static final Logger LOGGER = Logger.getLogger(DirectoryListener.class.getName());
    private final ExecutorService executorService = Executors.newFixedThreadPool(16);
    private final File file;

    public DirectoryListener(File file) {
        this.file = file;
    }


    public void start() {
        WatchService watchService = createWatchService();

        boolean poll = true;
        while (poll) {
            WatchKey key = null;
            try {
                key = Objects.requireNonNull(watchService).take();
            } catch (InterruptedException exception) {
                LOGGER.log(Level.SEVERE, "Exception - " + exception.getMessage() + " Cause - " + exception.getCause());
            }
            Path filePath = (Path) Objects.requireNonNull(key).watchable();
            for (WatchEvent<?> event : key.pollEvents()) {
                LOGGER.log(Level.SEVERE, "New file : " + event.context() + " was discovered " + " - [time : " + LocalDateTime.now() + "]");
                Runnable handler = selectHandler(filePath.resolve((Path) event.context()).toFile());
                executorService.execute(handler);
            }
            poll = key.reset();
        }
    }

    private WatchService createWatchService() {
        WatchService watchService = null;
        Path path;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path = Paths.get(file.getPath());
            path.register(watchService, ENTRY_CREATE);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Exception - " + exception.getMessage() + " Cause - " + exception.getCause());
        }
        return Objects.requireNonNull(watchService);
    }

    private static String getFileExtension(String file) {
        int index = file.indexOf('.');
        return index == -1 ? null : file.substring(index);
    }

    private Runnable selectHandler(File file) {
        if (".json".equals(getFileExtension(file.toString()))) {
            return new JsonFileHandler(file);
        } else if (".xml".equals(getFileExtension(file.toString()))) {
            return new XmlFileHandler(file);
        } else {
            return new RemoverHandler(file);
        }
    }
}