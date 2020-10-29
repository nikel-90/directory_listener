package com.github.nikel90.directory_listener;

import com.github.nikel90.directory_listener.listener.DirectoryListener;
import com.github.nikel90.directory_listener.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) throws IOException {

        try (FileInputStream ins = new FileInputStream("log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        File file = Util.inputFile();
        DirectoryListener listener = new DirectoryListener(file);
        listener.start();
    }
}
