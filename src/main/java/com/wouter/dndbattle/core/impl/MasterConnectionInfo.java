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
package com.wouter.dndbattle.core.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.wouter.dndbattle.core.IMasterConnectionInfo;
import com.wouter.dndbattle.core.ISlave;

/**
 *
 * @author wverl
 */
public class MasterConnectionInfo implements IMasterConnectionInfo {

    private final ISlave slave;
    private final String slaveTitle;
    private final boolean localhost;
    private final String playerName;
    private final AtomicInteger failureCounter = new AtomicInteger();

    /**
     * Default construcotr for MasterConnectionInfo.
     *
     * @param slaveTitle The title the slave should have.
     * @param localhost  True if the connection is to the same computer
     * @param playerName The name of the player.
     */
    public MasterConnectionInfo(String slaveTitle, boolean localhost, String playerName, ISlave slave) {
        this.slaveTitle = slaveTitle;
        this.localhost = localhost;
        this.playerName = playerName;
        this.slave = slave;
    }

    public ISlave getSlave() {
        return slave;
    }

    @Override
    public String getSlaveTitle() {
        return slaveTitle;
    }

    @Override
    public boolean isLocalhost() {
        return localhost;
    }

    /**
     * @return the playerName that can be used to determine if a character is
     *         played by the current user.
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return slaveTitle + '(' + localhost + ')' + playerName;
    }

    public int increaseCounter() {
        return failureCounter.incrementAndGet();
    }

    public int getFailureCount() {
        return failureCounter.get();
    }

    public void resetCounter() {
        int count = failureCounter.get();
        if (count > 0 && count < 3) {
            if (!failureCounter.compareAndSet(count, 0)) {
                resetCounter();
            }
        }
    }
}
