/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import static com.wouter.dndbattle.utils.Settings.PRESETFOLDER;

import java.io.File;

import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class FileManager {

    private static final String FOLDERNAME = "/.dnd";
    private static final File fileStorage;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FileManager.class);

    static {
        String folder;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            folder = System.getenv("APPDATA");
        } else {
            folder = System.getProperty("user.home");
        }
        fileStorage = new File(folder, FOLDERNAME);
        if (!fileStorage.exists()) {
            fileStorage.mkdir();
        } else if (!fileStorage.isDirectory()) {
            System.out.println("Error storage directory " + fileStorage.getAbsolutePath() + " exists as a file.");
            log.error("Error storage directory [{}] exists as a file", fileStorage);
            System.exit(1);
        }
    }

    public static File getFile(String fileName) {
        return getFile(fileStorage, fileName);
    }

    private static File getFile(File base, String name) {
        return new File(base, name);
    }

    public static File getFolder(String folderName) {
        return getFolder(fileStorage, folderName);
    }

    private static File getFolder(File base, String folderName) {
        File folder = getFile(base, folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    public static File getPresetFolder() {
        File folder = Settings.getInstance().getProperty(PRESETFOLDER, getFolder("presets"));
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    private FileManager() {
    }

}
