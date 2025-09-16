/*
 * Copyright (C) 2018 Wouter
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dndbattle.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Settings extends Properties {

  public static final String CARRYING_CAPACITY_MULTIPLIER = "gameplay.carryingcapacity.multiplier";
  public static final String CHARACTER_SPELLS_COLUMNS = "gui.character.spells.columns";
  public static final String CLIENT_LOCATION_X = "gui.client.location.x";
  public static final String CLIENT_LOCATION_Y = "gui.client.location.y";
  public static final String CLIENT_SIZE_HEIGHT = "gui.client.size.height";
  public static final String CLIENT_SIZE_STATE = "gui.client.size.state";
  public static final String CLIENT_SIZE_WIDTH = "gui.client.size.width";
  public static final String CLIENT_SPELLS_SEPERATOR = "gui.client.spells.seperator";
  public static final String CLIENT_TITLE = "gui.client.title";
  public static final String CONNECTION_HOST = "network.connect.host";
  public static final String CONNECTION_NAME = "network.connect.name";
  public static final String CONNECTION_PORT = "network.connect.port";
  public static final String EXPORT_FILESELECTION = "gui.export.fileselection";
  public static final String EXPORT_WEAPONSELECTION = "gui.export.weaponselection";
  public static final String FILE_WRITER_SAVE_TIMEOUT = "storage.presets.timeout";
  public static final String HOST_LOCATION_X = "gui.host.location.x";
  public static final String HOST_LOCATION_Y = "gui.host.location.y";
  public static final String HOST_SIZE_HEIGHT = "gui.host.size.height";
  public static final String HOST_SIZE_STATE = "gui.host.size.state";
  public static final String HOST_SIZE_WIDTH = "gui.host.size.width";
  public static final String HOST_TITLE = "gui.host.title";
  public static final String INPUT_FILESELECTION = "gui.input.fileselection";
  public static final String LOOKANDFEEL = "gui.lookandfeel";
  public static final String PRESETS_KNOWN_LOCATIONS = "storage.presets.known";
  public static final String PRESETFOLDER = "storage.presets.home";
  public static final String ROLL_FOR_DEATH = "gameplay.rollfordeath";
  public static final String SPELLS_GRID_COLUMNS = "gui.spells.grid.columns";
  public static final String WEBSITE = "gui.website";

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

  private final List<File> knownPresetLocations;

  private Settings() {
    List<File> knownLocations = new ArrayList<>();
    try {
      if (!PROPERTIES_FILE.exists()) {
        if (!PROPERTIES_FILE.getParentFile().exists()) {
          PROPERTIES_FILE.getParentFile().mkdirs();
        }
        PROPERTIES_FILE.createNewFile();
      }
      super.load(new FileInputStream(PROPERTIES_FILE));
      for (String path : getProperty(PRESETS_KNOWN_LOCATIONS, "").split(",")) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
          knownLocations.add(file);
        }
      }
    } catch (IOException e) {
      log.error("Exception while openings settings", e);
    }
    knownPresetLocations = knownLocations;
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

  public File getProperty(String key, File defaultValue) {
    return new File(super.getProperty(key, defaultValue.getPath()));
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

  public void addKnownPresetLocation(File location) {
    if (!knownPresetLocations.contains(location)) {
      knownPresetLocations.add(location);
      Collections.sort(knownPresetLocations);
      StringBuilder builder = new StringBuilder();
      for (File knownPresetLocation : knownPresetLocations) {
        builder.append(knownPresetLocation.getPath()).append(',');
      }
      if (builder.length() > 0) {
        builder.setLength(builder.length() - 1);
      }
      setProperty(PRESETS_KNOWN_LOCATIONS, builder.toString());
    }
  }

  public List<File> getKnownPresetLocations() {
    return Collections.unmodifiableList(knownPresetLocations);
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

  /**
   * @return the alpha
   */
  public static boolean isAlpha() {
    return alpha;
  }

  /**
   * @param aAlpha the alpha to set
   */
  public static void setAlpha(boolean aAlpha) {
    alpha = aAlpha;
  }
}
