/*
 * Copyright (C) 2019 wverl
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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wverl
 */
public abstract class Initializable {

    private static final Logger log = LoggerFactory.getLogger(Initializable.class);
    private boolean initialized = false;
    private int progress = 0;
    private final Object syncObject = new Object();
    private List<IProgressKeeper> progressKeepers = new ArrayList<>();

    public void initialize() {
        synchronized (syncObject) {
            if (!initialized) {
                initializeHook();
            }
            progress = 100;
            initialized = true;
        }
    }

    protected abstract void initializeHook();

    public boolean isInitialized() {
        return initialized;
    }

    public int getProgress() {
        return progress;
    }

    protected void setProgress(int progress) {
        if (progress > this.progress) {
            if (progress < 100) {
                this.progress = progress;
            } else {
                this.progress = 100;
            }
            for (IProgressKeeper progressKeeper : progressKeepers) {
                progressKeeper.notifyProgress(this.progress);
            }
            log.debug("Progress for [{}] is now [{}]", getClass(), this.progress);
        }
    }

    public void registerProgressKeeper(IProgressKeeper progressKeeper) {
        progressKeepers.add(progressKeeper);
    }

    public void unregisterProgressKeeper(IProgressKeeper progressKeeper) {
        progressKeepers.remove(progressKeeper);
    }

    public static interface IProgressKeeper {

        public void notifyProgress(int progress);
    }
}
