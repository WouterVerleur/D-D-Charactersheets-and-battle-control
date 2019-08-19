/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dndbattle.randomizer.utils;

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

  private static final Logger log = LoggerFactory.getLogger(Settings.class);

  private static final Settings INSTANCE;
  private static final File FILE_STORAGE;
  private static final File PROPERTIES_FILE;

  private static SettingsFileWriterThread writerThread;

  static {
    String folder;
    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
      folder = System.getenv("APPDATA");
    } else {
      folder = System.getProperty("user.home");
    }
    FILE_STORAGE = new File(folder, "/.randomizer");
    if (!FILE_STORAGE.exists()) {
      FILE_STORAGE.mkdir();
    } else if (!FILE_STORAGE.isDirectory()) {
      System.exit(1);
    }
    PROPERTIES_FILE = new File(FILE_STORAGE, "properties.conf");
  }

  static {
    INSTANCE = new Settings();
  }

  public static Settings getInstance() {
    return INSTANCE;
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
      e.printStackTrace();
    }
  }

  public int getProperty(String key, int defaultValue) {
    String property = super.getProperty(key, Integer.toString(defaultValue));
    try {
      return Integer.parseInt(property);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return defaultValue;
    }
  }

  public boolean getProperty(String key, boolean defaultValue) {
    String property = super.getProperty(key, Boolean.toString(defaultValue));
    try {
      return Boolean.parseBoolean(property);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return defaultValue;
    }
  }

  public File getProperty(String key, File defaultValue) {
    String prop = super.getProperty(key);
    if (prop == null || prop.isEmpty()) {
      return defaultValue;
    }
    return new File(prop);
  }

  public File getPropertyFile(String key, File defaultValue) {
    return getProperty(key, defaultValue);
  }

  public File getInternalFile(String filename) {
    return new File(FILE_STORAGE, filename);
  }

  @Override
  public synchronized Object setProperty(String key, String value) {
    Object oldValue = super.setProperty(key, value);
    if (oldValue == null || !oldValue.equals(value)) {
      storeFile();
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
    storeFile();
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
        getInstance().store(new FileOutputStream(PROPERTIES_FILE), "Properties for D&D charactersheets & Battle Control");
      } catch (IOException e) {
        log.error("Exception while saving properties to file [{}]", PROPERTIES_FILE, e);
      }
    }
  }

}
