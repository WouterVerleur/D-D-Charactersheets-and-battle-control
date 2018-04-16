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
        if (getCharacterFile(character).exists()) {
            return false;
        }
        final List<ICharacter> characters = CLASS_CHARACTER_MAP.get(character.getClass());
        characters.add(character);
        Collections.sort(characters);
        storeCharacter(character, false);
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
                /*
                 * if (JOptionPane.showConfirmDialog(null, "The character " + GlobalUtils.getFileNameWithoutExtention(file) + " resulted in an error.\nIs it op to delete this character?", "Character
                 * Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION) { file.delete(); }
                 */
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
        File file = getCharacterFile(preset);
        if (file.exists()) {
            file.delete();
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
        String name = character.getName().replaceAll("[^a-zA-Z0-9]+", "_") + '.' + character.getClass().getSimpleName();
        return new File(PRESET_FOLDER, name);
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
