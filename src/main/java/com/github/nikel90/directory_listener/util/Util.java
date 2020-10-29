package com.github.nikel90.directory_listener.util;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static Logger LOGGER = Logger.getLogger(Util.class.getName());

    public static File inputFile() {
        File file = null;
        boolean isFile = true;

        System.out.println("Enter a directory name:");

        while (isFile) {
            Scanner scanner = new Scanner(System.in);
            file = new File(scanner.nextLine());

            if (file.exists() && !file.isFile()) {
                LOGGER.log(Level.INFO, "In directory is selected successfully!");
                System.out.println("The directory <" + file + "> selected for listening!");
                isFile = false;
            } else {
                System.out.println("Invalid directory name. Enter the directory name again.");
                LOGGER.log(Level.WARNING, "Error in directory selection");
            }
        }
        return Objects.requireNonNull(file);
    }
}
