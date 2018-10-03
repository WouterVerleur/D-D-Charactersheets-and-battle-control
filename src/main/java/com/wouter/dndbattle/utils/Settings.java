/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Settings extends Properties {

    public static final String CONNECTION_HOST = "network.connect.host";
    public static final String CONNECTION_NAME = "network.connect.name";
    public static final String CONNECTION_PORT = "network.connect.port";
    public static final String FILE_WRITER_SAVE_TIMEOUT = "storage.presets.timeout";
    public static final String LOOKANDFEEL = "gui.lookandfeel";
    public static final String INPUT_FILESELECTION = "gui.input.fileselection";
    public static final String EXPORT_FILESELECTION = "gui.export.fileselection";
    public static final String MASTER_LOCATION_X = "gui.master.location.x";
    public static final String MASTER_LOCATION_Y = "gui.master.location.y";
    public static final String MASTER_SIZE_HEIGHT = "gui.master.size.height";
    public static final String MASTER_SIZE_STATE = "gui.master.size.state";
    public static final String MASTER_SIZE_WIDTH = "gui.master.size.width";
    public static final String MASTER_TITLE = "gui.master.title";
    public static final String PRESETFOLDER = "storage.presets.home";
    public static final String ROLLFORDEATH = "gameplay.rollfordeath";
    public static final String SLAVE_LOCATION_X = "gui.slave.location.x";
    public static final String SLAVE_LOCATION_Y = "gui.slave.location.y";
    public static final String SLAVE_SIZE_HEIGHT = "gui.slave.size.height";
    public static final String SLAVE_SIZE_STATE = "gui.slave.size.state";
    public static final String SLAVE_SIZE_WIDTH = "gui.slave.size.width";
    public static final String SLAVE_SPELLS_SEPERATOR = "gui.slave.spells.seperator";
    public static final String SLAVE_TITLE = "gui.slave.title";
    public static final String SPELLS_GRID_COLUMNS = "gui.spells.gid.columns";

    private static boolean alpha = false;

    private static final Settings INSTANCE;
    private static final Logger log = LoggerFactory.getLogger(Settings.class);
    private static final File PROPERTIES_FILE = FileManager.getFile("properties.conf");

    private static SettingsFileWriterThread writerThread;

    static {
        INSTANCE = new Settings();
    }

    public static Settings getInstance() {
        return INSTANCE;
    }

    public static void setAlpha(boolean alpha) {
        Settings.alpha = alpha;
    }

    public static boolean isAlpha() {
        return alpha;
    }

    private Settings() {
        try {
            if (!PROPERTIES_FILE.exists()) {
                if (!PROPERTIES_FILE.getParentFile().exists()) {
                    PROPERTIES_FILE.getParentFile().mkdirs();
                }
                PROPERTIES_FILE.createNewFile();
            }
            super.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            log.error("Exception while openings settings", e);
        }
    }

    public int getProperty(String key, int defaultValue) {
        String property = super.getProperty(key, Integer.toString(defaultValue));
        try {
            return Integer.parseInt(property);
        } catch (NumberFormatException e) {
            log.error("Property [{}] could not be parsed as a number", property);
            return defaultValue;
        }
    }

    public boolean getProperty(String key, boolean defaultValue) {
        String property = super.getProperty(key, Boolean.toString(defaultValue));
        try {
            return Boolean.parseBoolean(property);
        } catch (NumberFormatException e) {
            log.error("Property [{}] could not be parsed as a number", property);
            return defaultValue;
        }
    }

    @Override
    public synchronized Object setProperty(String key, String value) {
        Object oldValue = super.setProperty(key, value);
        if (oldValue == null || !oldValue.equals(value)) {
            log.debug("Setting [{}] was changed from [{}] to [{}]", key, oldValue, value);
            log.debug("Saving new settings");
            storeFile();
        } else {
            log.debug("Setting [{}] remained unchanged at [{}]", key, oldValue);
        }
        return oldValue;
    }

    public synchronized Object setProperty(String key, int value) {
        return setProperty(key, String.valueOf(value));
    }

    public synchronized Object setProperty(String key, boolean value) {
        return setProperty(key, String.valueOf(value));
    }

    @Override
    public synchronized void clear() {
        super.clear();
        storeFile(true);
    }

    private void storeFile() {
        storeFile(false);
    }

    private synchronized void storeFile(boolean rightNow) {
        if (writerThread == null || writerThread.getState() == Thread.State.TERMINATED) {
            writerThread = new SettingsFileWriterThread();
            writerThread.start();
        } else {
            writerThread.resetTimer();
        }
        if (rightNow) {
            writerThread.saveToFile();
        }
    }

    private static class SettingsFileWriterThread extends AbstractFileWriterThread {

        public SettingsFileWriterThread() {
            super(PROPERTIES_FILE);
        }

        @Override
        public void saveToFile() {
            try {
                log.debug("Saving properties to file [{}]", PROPERTIES_FILE);
                getInstance().store(new FileOutputStream(PROPERTIES_FILE), null);
            } catch (IOException e) {
                log.error("Exception while saving properties to file [{}]", PROPERTIES_FILE, e);
            }
        }
    }
}
