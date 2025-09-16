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
package org.dndbattle.core.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.dndbattle.core.IClient;
import org.dndbattle.core.IHostConnectionInfo;

/**
 *
 * @author wverl
 */
public class HostConnectionInfo implements IHostConnectionInfo {

  private final IClient client;
  private final String clientTitle;
  private final boolean localhost;
  private final String playerName;
  private final AtomicInteger failureCounter = new AtomicInteger();

  /**
   * Default constructor for HostConnectionInfo.
   *
   * @param clientTitle The title the client should have.
   * @param localhost   True if the connection is to the same computer.
   * @param playerName  The name of the player.
   * @param client      The client that is connected.
   */
  public HostConnectionInfo(String clientTitle, boolean localhost, String playerName, IClient client) {
    this.clientTitle = clientTitle;
    this.localhost = localhost;
    this.playerName = playerName;
    this.client = client;
  }

  public IClient getClient() {
    return client;
  }

  @Override
  public String getClientTitle() {
    return clientTitle;
  }

  @Override
  public boolean isLocalhost() {
    return localhost;
  }

  /**
   * @return the playerName that can be used to determine if a character is played by the current user.
   */
  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String toString() {
    return clientTitle + '(' + localhost + ')' + playerName;
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
