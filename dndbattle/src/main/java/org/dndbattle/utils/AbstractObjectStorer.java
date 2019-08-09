/*
 * Copyright (C) 2018 wverl
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
package com.wouter.dndbattle.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.wouter.dndbattle.objects.ISaveableClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract class for creating a central
 *
 * @author wverleur
 * @param <T> the class of the objects to store using this class.
 */
public abstract class AbstractObjectStorer<T extends ISaveableClass> extends Initializable {

    private static final Logger log = LoggerFactory.getLogger(AbstractObjectStorer.class);

    private final Map<File, FileWriterThread<T>> writerThreadMap = new HashMap<>();
    private final File presetFolder;

    protected AbstractObjectStorer(String subPath) {
        presetFolder = new File(FileManager.getPresetFolder(), subPath);
        if (!presetFolder.exists()) {
            presetFolder.mkdir();
        } else if (!presetFolder.isDirectory()) {
            log.error("The preset folder [{}] exists but is not a directory.", presetFolder);
            System.exit(1);
        }
    }

    protected List<T> loadFromFiles(Class<? extends T> clazz) {
        return loadFromFiles(clazz, true);
    }

    protected List<T> loadFromFiles(Class<? extends T> clazz, final boolean setProgress) {
        File[] files = presetFolder.listFiles(new ClassFileFilter(clazz));
        List<Callable<T>> callables = new ArrayList<>();
        int totalFiles = files.length;
        AtomicInteger completedFiles = new AtomicInteger();
        for (File file : files) {
            log.debug("Found preset file [{}]", file);
            callables.add((Callable<T>) () -> {
                T fromFile = null;
                try {
                    fromFile = getFromFile(file, clazz);
                    if (setProgress) {
                        setProgress(Math.floorDiv(completedFiles.incrementAndGet() * 100, totalFiles));
                    }
                } catch (ObjectReadException | IllegalArgumentException | ExceptionInInitializerError e) {
                    log.error("Error while reading preset of class [{}] from file [{}]", clazz, file, e);
                }
                return fromFile;
            });
        }

        List<T> returnList = new ArrayList<>();
        final ExecutorService executor = Executors.newWorkStealingPool();
        try {
            for (Future<T> future : executor.invokeAll(callables)) {
                T preset = null;
                try {
                    preset = future.get();
                } catch (ExecutionException e) {
                    log.error("Future caught an exception", e);
                }
                if (preset != null) {
                    returnList.add(preset);
                }
            }
        } catch (InterruptedException e) {
            log.error("Loading interrupted while waiting for loading to complete", e);
        }

        Collections.sort(returnList);
        return returnList;
    }

    public void removeAll(List<T> characters) {
        characters.forEach((character) -> {
            remove(character);
        });
    }

    public abstract void remove(T preset);

    public abstract boolean add(T preset);

    public void updateAll(List<T> presets) {
        for (T preset : presets) {
            update(preset);
        }
    }

    public abstract void update(T preset);

    public abstract List<T> getAll();

    public T getForString(String name) {
        for (T item : getAll()) {
            if (item.toString().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    protected T getFromFile(File file, Class<? extends T> clazz) throws ObjectReadException {
        if (file.length() == 0) {
            return null;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(file, clazz);
            } catch (IOException e) {
                log.error("Exception while reading preset of class [{}] from file [{}]", clazz, file, e);
                String presetName = file.getName();
                presetName = presetName.substring(0, presetName.indexOf('.')).replace('_', ' ');
                switch (JOptionPane.showConfirmDialog(null,
                        "The preset " + presetName + " seems to be incompatible with the current version of the software.\n"
                        + "The preset can probably be converted to the current version, but some information may be lost in the process.\n"
                        + "Would you like to continue?", "Error reading preset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    case JOptionPane.YES_OPTION:
                        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                        try {
                            T object = mapper.readValue(file, clazz);
                            store(object, true);
                            return object;
                        } catch (IOException e2) {
                            log.error("Error while converting the preset from file [{}]", file, e2);
                        }
                        break;
                    default:
                        log.debug("User did not want to attempt a conversion of the file [{}]", file);
                        break;
                }
                throw new ObjectReadException("Error while reading preset from file " + file.getAbsolutePath(), e);
            }
        }
    }

    protected void store(T object, boolean storeRightNow) {
        File file = getFile(object);
        FileWriterThread writerThread = writerThreadMap.get(file);
        if (writerThread == null) {
            writerThread = new FileWriterThread(file, object);
            if (storeRightNow) {
                writerThread.saveToFile();
            } else {
                writerThreadMap.put(file, writerThread);
                writerThread.start();
            }
        } else {
            if (writerThread.changeObject(object) || writerThread.getState() == Thread.State.TERMINATED) {
                writerThreadMap.remove(file);
                store(object, storeRightNow);
            } else if (storeRightNow) {
                writerThread.saveToFile();
            }
        }
    }

    public boolean canCreate(T object) {
        return !getFile(object).exists();
    }

    protected File getFile(ISaveableClass object) {
        log.debug("Returning file for preset [{}]", object);
        final String filename = object.getSaveFileName() + '.' + object.getClass().getSimpleName();
        return new File(presetFolder, filename);
    }

    /*
     * Internal Classes
     */
    public static class ObjectReadException extends Exception {

        public ObjectReadException(String message) {
            super(message);
        }

        public ObjectReadException(Throwable cause) {
            super(cause);
        }

        public ObjectReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class ClassFileFilter implements FilenameFilter {

        private final Class clazz;

        public ClassFileFilter(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean accept(File file, String string) {
            return GlobalUtils.getFileExtension(string).equalsIgnoreCase(clazz.getSimpleName());
        }
    }

    private static class FileWriterThread<T extends ISaveableClass> extends AbstractFileWriterThread {

        private T object;
        private final Object syncObject = new Object();
        private boolean saved = true;

        public FileWriterThread(File file, T object) {
            super(file);
            this.object = object;
            saved = false;
        }

        public boolean changeObject(T object) {
            synchronized (syncObject) {
                this.object = object;
                resetTimer();
            }
            return saved;
        }

        @Override
        public void saveToFile() {
            synchronized (syncObject) {
                if (!saved) {
                    try {
                        log.debug("Exporting preset [{}] to file [{}]", object, getFile());
                        final ObjectMapper mapper = new ObjectMapper();
                        mapper.writerFor(object.getClass()).writeValue(getFile(), object);
                        saved = true;
                    } catch (IOException e) {
                        log.error("Error while writing preset [{}] to [{}]", object, getFile(), e);
                    }
                }
            }
        }
    }
}
