/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Characters {

    private static final Logger log = LoggerFactory.getLogger(Characters.class);

    private static final File PRESET_FOLDER = FileManager.getPresetFolder();
    private static final Map<File, CharacterFileWriterThread> WRITER_THREAD_MAP = new HashMap<>();
    private static final Map<Class<? extends ICharacter>, List<ICharacter>> CLASS_CHARACTER_MAP = new HashMap<>();

    static {
        if (!PRESET_FOLDER.exists()) {
            PRESET_FOLDER.mkdir();
        } else if (!PRESET_FOLDER.isDirectory()) {
            log.error("The preset file [{}] exists but is not a file.", PRESET_FOLDER);
            System.exit(1);
        }
    }

    public static boolean addCharacter(ICharacter character) {
        if (!canCreateCharacter(character)) {
            return false;
        }
        final List<ICharacter> characters = getCharacters(character.getClass());
        characters.add(character);
        Collections.sort(characters);
        storeCharacter(character, true);
        return true;
    }

    public static void updateCharacter(ICharacter character) {
        if (getCharacterFile(character).exists()) {
            storeCharacter(character, false);
        }
    }

    public static List<ICharacter> getCharacters(Class<? extends ICharacter> clazz) {
        if (!CLASS_CHARACTER_MAP.containsKey(clazz)) {
            log.debug("Loading list of characters of class [{}]", clazz.getSimpleName());
            CLASS_CHARACTER_MAP.put(clazz, loadFromFiles(clazz));
        }
        log.debug("Returning list of characters of class [{}]", clazz.getSimpleName());
        return CLASS_CHARACTER_MAP.get(clazz);
    }

    private static List<ICharacter> loadFromFiles(Class<? extends ICharacter> clazz) {
        File[] files = PRESET_FOLDER.listFiles(new ClassFileFilter(clazz));
        List<ICharacter> returnList = new ArrayList<>();
        for (File file : files) {
            try {
                log.debug("Found character file [{}]", file);
                ICharacter character = getCharacterFromFile(file);
                if (character != null) {
                    returnList.add(character);
                }
            } catch (CharacterReadException | IllegalArgumentException e) {
                log.error("Error while reading character from file [{}]", file, e);
            }
        }
        Collections.sort(returnList);
        return returnList;
    }

    public static void removeAll(List<ICharacter> characters) {
        characters.forEach((character) -> {
            remove(character);
        });
    }

    public static void remove(ICharacter preset) {
        storeCharacter(preset, true);// to make sure any change does not undo this remove
        File file = getCharacterFile(preset);
        if (file.exists()) {
            file.delete();
            getCharacters(preset.getClass()).remove(preset);
            log.debug("Character [{}] has been deleted.", preset);
        }
    }

    private static ICharacter getCharacterFromFile(File file) throws CharacterReadException {
        if (file.length() == 0) {
            return null;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(file, AbstractCharacter.class);
            } catch (IOException e) {
                log.error("Exception while reading character from file [{}]", file, e);
                String charname = file.getName();
                charname = charname.substring(0, charname.indexOf('.')).replace('_', ' ');
                switch (JOptionPane.showConfirmDialog(null,
                        "The character " + charname + " seems to be incompatible with the current version of the software.\n"
                        + "The character can be converted to the current version, but some information may be lost in the process.\n"
                        + "Would you like to continue?", "Error reading character", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    case JOptionPane.YES_OPTION:
                        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                        try {
                            AbstractCharacter character = mapper.readValue(file, AbstractCharacter.class);
                            storeCharacter(character, true);
                        } catch (IOException e2) {
                            log.error("Error while converting the character from file [{}]", file, e2);
                        }
                        break;
                }
                throw new CharacterReadException("Error while character presets from file " + file.getAbsolutePath(), e);
            }
        }
    }

    private static void storeCharacter(ICharacter character, boolean storeRightNow) {
        File file = getCharacterFile(character);
        CharacterFileWriterThread writerThread = WRITER_THREAD_MAP.get(file);
        if (writerThread == null) {
            writerThread = new CharacterFileWriterThread(file, character);
            if (storeRightNow) {
                writerThread.saveToFile();
            } else {
                writerThread.start();
            }
            WRITER_THREAD_MAP.put(file, writerThread);
        } else {
            if (writerThread.changeCharacter(character)) {
                if (writerThread.getState() == Thread.State.TERMINATED) {
                    WRITER_THREAD_MAP.remove(file);
                    storeCharacter(character, storeRightNow);
                } else {
                    writerThread.start();
                }
            }
        }
    }

    private static File getCharacterFile(ICharacter character) {
        log.debug("Returning file for character [{}]", character);
        final String filename = character.getSaveFileName() + '.' + character.getClass().getSimpleName();
        return new File(PRESET_FOLDER, filename);

    }

    public static boolean canCreateCharacter(ICharacter character) {
        return !getCharacterFile(character).exists();
    }

    public static class CharacterReadException extends Exception {

        public CharacterReadException(String message) {
            super(message);
        }

        public CharacterReadException(Throwable cause) {
            super(cause);
        }

        public CharacterReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class ClassFileFilter implements FilenameFilter {

        private final Class clazz;

        public ClassFileFilter(Class<? extends ICharacter> clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean accept(File file, String string) {
            return GlobalUtils.getFileExtension(string).equalsIgnoreCase(clazz.getSimpleName());
        }
    }

    private static class CharacterFileWriterThread extends FileWriterThread {

        private ICharacter character;
        private final Object syncObject = new Object();
        private boolean saved = true;

        public CharacterFileWriterThread(File file, ICharacter character) {
            super(file);
            this.character = character;
            saved = false;
        }

        public boolean changeCharacter(ICharacter character) {
            synchronized (syncObject) {
                this.character = character;
                resetTimer();
            }
            return saved;
        }

        @Override
        public void saveToFile() {
            synchronized (syncObject) {
                if (!saved) {
                    try {
                        log.debug("Exporting character [{}] to file [{}]", character, getFile());
                        final ObjectMapper mapper = new ObjectMapper();
                        mapper.writerFor(AbstractCharacter.class).writeValue(getFile(), character);
                        saved = true;
                    } catch (IOException e) {
                        log.error("Error while writing character [{}] to [{}]", character, getFile(), e);
                    }
                }
            }
        }
    }
}
