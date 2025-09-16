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

import static org.dndbattle.utils.Settings.CLIENT_TITLE;
import static org.dndbattle.utils.Settings.ROLL_FOR_DEATH;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import org.dndbattle.core.IClient;
import org.dndbattle.core.IHost;
import org.dndbattle.core.IHostConnectionInfo;
import org.dndbattle.objects.ICombatant;
import org.dndbattle.objects.impl.Combatant;
import org.dndbattle.utils.Settings;
import org.dndbattle.view.host.HostFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Host extends AbstractRemoteConnector implements IHost {

  private static final Logger log = LoggerFactory.getLogger(Host.class);

  private static final Settings SETTINGS = Settings.getInstance();
  private boolean battleStarted;
  private List<ICombatant> combatants = new ArrayList<>();
  private HostFrame frame;
  private final String ip;
  private final List<HostConnectionInfo> clients = new ArrayList<>();
  private int currentIndex = 0;

  private final ExecutorService executor = Executors.newWorkStealingPool();

  public Host(String ip) {
    this.ip = ip;
  }

  /**
   * @return the frame
   */
  public HostFrame getFrame() {
    if (frame == null) {
      createFrame();
    }
    return frame;
  }

  private synchronized void createFrame() {
    if (frame == null) {
      frame = new HostFrame(this);
    }
  }

  public void addCombatant(Combatant combatant) {
    ICombatant currectActive = null;
    if (combatants.size() > 0 && battleStarted) {
      currectActive = combatants.get(currentIndex);
    }
    combatants.add(combatant);
    Collections.sort(combatants);
    if (currectActive != null) {
      currentIndex = combatants.indexOf(currectActive);
    }
    updateAll(true);
  }

  public void removeCombatant(ICombatant combatant) {
    combatants.remove(combatant);
    updateAll(true);
  }

  @Override
  public void connect(IClient client) throws RemoteException {
    connect(client, "");
  }

  @Override
  public void connect(IClient client, String playerName) throws RemoteException {
    connect(client, playerName, client.getIp());
  }

  @Override
  public void connect(IClient client, String playerName, String clientIp) throws RemoteException {
    boolean localhost = clientIp.equalsIgnoreCase(ip);
    String localhostAddress = null;
    if (!localhost) {
      try {
        localhostAddress = InetAddress.getLocalHost().getHostAddress();
        localhost = clientIp.equalsIgnoreCase(localhostAddress);
        InetAddress[] allForLocalhost = InetAddress.getAllByName("localhost");
        int i = 0;
        while (!localhost && i < allForLocalhost.length) {
          localhost = clientIp.equalsIgnoreCase(allForLocalhost[i].getHostAddress());
          i++;
        }
      } catch (UnknownHostException e) {
        log.error("Error while determining if connection is from localhost", e);
      }
    }
    log.debug("Recieved new client connection from [{}] for which localhost was [{}] for remote host [{}] and localhost [{}]", playerName, localhost, clientIp, localhostAddress);
    HostConnectionInfo connectionInfo = new HostConnectionInfo(SETTINGS.getProperty(CLIENT_TITLE, "Client"), localhost, playerName, client);
    clients.add(connectionInfo);
    client.setConnectionInfo(connectionInfo);
    client.refreshView(true);
  }

  @Override
  public void disconnect(IHostConnectionInfo connectionInfo) throws RemoteException {
    if (connectionInfo instanceof HostConnectionInfo) {
      disconnect((HostConnectionInfo) connectionInfo);
    }
  }

  public void disconnect(HostConnectionInfo connectionInfo) {
    clients.remove(connectionInfo);
  }

  @Override
  public int getProperty(String key, int defaultValue) throws RemoteException {
    return SETTINGS.getProperty(key, defaultValue);
  }

  @Override
  public void setProperty(String key, int value) throws RemoteException {
    SETTINGS.setProperty(key, value);
  }

  public List<HostConnectionInfo> getClients() {
    return clients;
  }

  public String getIp() {
    return ip;
  }

  public void nextTurn() {
    battleStarted = true;
    boolean keepSearching = true;
    currentIndex++;
    while (keepSearching) {
      if (combatants.isEmpty()) {
        break;
      }
      if (currentIndex >= combatants.size()) {
        currentIndex = 0;
      }
      ICombatant next = combatants.get(currentIndex);
      if (next.isDead()) {
        removeCombatant(next);
        continue;
      }
      if (next.rollingForDeath() && !SETTINGS.getProperty(ROLL_FOR_DEATH, true)) {
        log.debug("Adding deathroll to [{}]", next);
        JOptionPane.showMessageDialog(getFrame(), "An automatic deathroll was added to " + next, "Automatic deathroll.", JOptionPane.INFORMATION_MESSAGE);
        ((Combatant) next).addDeathRoll();
        if (next.isDead()) {
          removeCombatant(next);
        } else {
          currentIndex++;
        }
        continue;
      }
      keepSearching = false;
    }
    if (combatants.isEmpty()) {
      startNewBattle();
    }
    updateAll(false);
  }

  public void shutdown() {
    System.exit(0);
  }

  public void kick(HostConnectionInfo connectionInfo) {
    try {
      connectionInfo.getClient().shutdown();
    } catch (RemoteException ex) {
      log.error("Attempt to kick [{}] resulted in an error.", connectionInfo, ex);
    }
    clients.remove(connectionInfo);
  }

  public void startNewBattle() {
    combatants = new ArrayList<>();
    currentIndex = 0;
    battleStarted = false;
    updateAll(true);
  }

  public void updateAll(boolean refreshCombatants) {
    getFrame().refreshBattle(combatants, currentIndex);
    clients.forEach((connectionInfo) -> {
      executor.submit(() -> {
        IClient client = connectionInfo.getClient();
        try {
          client.refreshView(refreshCombatants);
          connectionInfo.resetCounter();
        } catch (RemoteException e) {
          int failures = connectionInfo.increaseCounter();
          log.error("Unable to refresh client [{}] [{}] times in a row.", connectionInfo, failures, e);
          if (failures >= 3) {
            kick(connectionInfo);
            log.warn("Client [{}] has [{}] failures and has been kicked.", connectionInfo, failures);
          }
        }
      });
    });
  }

  @Override
  protected void shutdownHook() {
    clients.forEach((connectionInfo) -> {
      try {
        connectionInfo.getClient().shutdown();
      } catch (RemoteException e) {
        log.warn("Unable to shutdown client [{}]", connectionInfo, e);
      }
    });
  }

  @Override
  public List<ICombatant> getCombatants() {
    return combatants;
  }

  public void setCombatants(List<ICombatant> combatants) {
    Collections.sort(combatants);
    currentIndex = 0;
    this.combatants = combatants;
  }

  @Override
  public int getCurrentIndex() {
    return currentIndex;
  }
}
