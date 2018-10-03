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
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.wouter.dndbattle.utils.Settings.FILE_WRITER_SAVE_TIMEOUT;
import org.joda.time.DateTime;

/**
 *
 * @author wverl
 */
public abstract class AbstractFileWriterThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(AbstractFileWriterThread.class);

    private static final Settings SETTINGS = Settings.getInstance();
    private static boolean shutdown = false;

    public static final int DEFAULT_TIMEOUT = 10;

    private final File file;
    private final Thread shutdownHook;
    private DateTime saveTime;

    public AbstractFileWriterThread(File file) {
        this.file = file;
        resetTimer();

        shutdownHook = new Thread() {
            @Override
            public void run() {
                log.debug("Shutting down file writer for [{}]", file);
                shutdown = true;
                saveToFile();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        log.debug("Created shutdown hook to force the saving of to file [{}]", file);
    }

    @Override
    public void run() {
        try {
            while (saveTime.isAfter(DateTime.now()) && !shutdown) {
                // Always sleep 1 second to make sure the programm checks every second if it should save.
                // If we sleep until the savetime then we will always save 10 seconds after the last change and the shutdown hook hsa no use.
                log.debug("Thread for storing file [{}] is going to sleep for 1 more second.", file);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            log.error("Recieved interrupt while waiting to save the file [{}]. Saving the file rightaway.", file, e);
        }
        log.debug("Saving to file [{}].", file);
        saveToFile();
        if (!shutdown) {
            log.debug("Removing the shutdown hook for file [{}].", file);
            Runtime.getRuntime().removeShutdownHook(shutdownHook);
        }
    }

    public File getFile() {
        return file;
    }

    protected final void resetTimer() {
        saveTime = DateTime.now().plusSeconds(SETTINGS.getProperty(FILE_WRITER_SAVE_TIMEOUT, DEFAULT_TIMEOUT));
        log.debug("Changed savetime to [{}]", saveTime);
    }

    public abstract void saveToFile();
}
