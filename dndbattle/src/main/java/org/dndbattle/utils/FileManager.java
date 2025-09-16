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

import static org.dndbattle.utils.Settings.PRESETFOLDER;

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
