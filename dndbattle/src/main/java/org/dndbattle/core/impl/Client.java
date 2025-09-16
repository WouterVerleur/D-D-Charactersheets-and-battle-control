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

import java.rmi.RemoteException;
import java.util.List;

import org.dndbattle.core.IClient;
import org.dndbattle.core.IHost;
import org.dndbattle.objects.ICombatant;
import org.dndbattle.utils.Settings;
import org.dndbattle.view.client.AbstractClientFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dndbattle.core.IHostConnectionInfo;

/**
 *
 * @author Wouter
 */
public class Client extends AbstractRemoteConnector implements IClient {

  private static final Logger log = LoggerFactory.getLogger(Client.class);

  private static final Settings SETTINGS = Settings.getInstance();

  private final AbstractClientFrame frame;
  private IHost host;

  private IHostConnectionInfo connectionInfo;
  private final String ip;

  private List<ICombatant> combatants;

  public Client(IHost host, AbstractClientFrame frame, String ip) {
    this.host = host;
    this.frame = frame;
    this.ip = ip;
  }

  /**
   * @return the connectionInfo
   */
  @Override
  public IHostConnectionInfo getConnectionInfo() {
    return connectionInfo;
  }

  /**
   * @param connectionInfo the connectionInfo to set
   */
  @Override
  public void setConnectionInfo(IHostConnectionInfo connectionInfo) {
    if (frame != null) {
      frame.setTitle(connectionInfo.getClientTitle());
    }
    this.connectionInfo = connectionInfo;
  }

  public int getProperty(String key, int defaultValue) {
    if (connectionInfo != null && connectionInfo.isLocalhost()) {
      try {
        return host.getProperty(key, defaultValue);
      } catch (RemoteException e) {
        log.error("Error while retrieving settings", e);
      }
    }
    return SETTINGS.getProperty(key, defaultValue);
  }

  public void setProperty(String key, int value) {
    if (connectionInfo != null && connectionInfo.isLocalhost()) {
      try {
        host.setProperty(key, value);
        return;
      } catch (RemoteException e) {
        log.error("Error while retrieving settings", e);
      }
    }
    SETTINGS.setProperty(key, value);
  }

  @Override
  protected void shutdownHook() {
    if (host != null) {
      try {
        host.disconnect(connectionInfo);
      } catch (RemoteException e) {
        log.error("Unable to disconnect.", e);
      }
    }
  }

  @Override
  public void refreshView(boolean forceRefresh) throws RemoteException {
    try {
      combatants = host.getCombatants();
      if (forceRefresh || combatants == null) {
        frame.refreshCombatants(combatants);
      }
      frame.refreshBattle(combatants, host.getCurrentIndex());
    } catch (RemoteException e) {
      log.error("Unable to refresh the view", e);
    }

  }

  @Override
  public void shutdown() {
    host = null;
    System.exit(0);
  }

  @Override
  public String getIp() {
    return ip;
  }

  @Override
  public String getName() {
    return connectionInfo.getPlayerName();
  }

  @Override
  public void ping() throws RemoteException {
    // Nothing to do, just let the function return.
  }
}
