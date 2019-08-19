/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dndbattle.core.impl;

import static org.dndbattle.utils.Settings.SLAVE_TITLE;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import org.dndbattle.core.IMaster;
import org.dndbattle.core.IMasterConnectionInfo;
import org.dndbattle.core.ISlave;
import org.dndbattle.objects.ICombatant;
import org.dndbattle.objects.impl.Combatant;
import org.dndbattle.utils.Settings;
import org.dndbattle.view.master.MasterFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.dndbattle.utils.Settings.ROLL_FOR_DEATH;

/**
 *
 * @author Wouter
 */
public class Master extends AbstractRemoteConnector implements IMaster {

    private static final Logger log = LoggerFactory.getLogger(Master.class);

    private static final Settings SETTINGS = Settings.getInstance();
    private boolean battleStarted;
    private List<ICombatant> combatants = new ArrayList<>();
    private MasterFrame frame;
    private final String ip;
    private final List<MasterConnectionInfo> slaves = new ArrayList<>();
    private int currentIndex = 0;

    private final ExecutorService executor = Executors.newWorkStealingPool();

    public Master(String ip) {
        this.ip = ip;
    }

    /**
     * @return the frame
     */
    public MasterFrame getFrame() {
        if (frame == null) {
            createFrame();
        }
        return frame;
    }

    private synchronized void createFrame() {
        if (frame == null) {
            frame = new MasterFrame(this);
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
    public void connect(ISlave slave) throws RemoteException {
        connect(slave, "");
    }

    @Override
    public void connect(ISlave slave, String playerName) throws RemoteException {
        connect(slave, playerName, slave.getIp());
    }

    @Override
    public void connect(ISlave slave, String playerName, String slaveIp) throws RemoteException {
        boolean localhost = slaveIp.equalsIgnoreCase(ip);
        String localhostAddress = null;
        if (!localhost) {
            try {
                localhostAddress = InetAddress.getLocalHost().getHostAddress();
                localhost = slaveIp.equalsIgnoreCase(localhostAddress);
                InetAddress[] allForLocalhost = InetAddress.getAllByName("localhost");
                int i = 0;
                while (!localhost && i < allForLocalhost.length) {
                    localhost = slaveIp.equalsIgnoreCase(allForLocalhost[i].getHostAddress());
                    i++;
                }
            } catch (UnknownHostException e) {
                log.error("Error while determining if connection is from localhost", e);
            }
        }
        log.debug("Recieved new slave connection from [{}] for which localhost was [{}] for remote host [{}] and localhost [{}]", playerName, localhost, slaveIp, localhostAddress);
        MasterConnectionInfo connectionInfo = new MasterConnectionInfo(SETTINGS.getProperty(SLAVE_TITLE, "Slave"), localhost, playerName, slave);
        slaves.add(connectionInfo);
        slave.setConnectionInfo(connectionInfo);
        slave.refreshView(true);
    }

    @Override
    public void disconnect(IMasterConnectionInfo connectionInfo) throws RemoteException {
        if (connectionInfo instanceof MasterConnectionInfo) {
            disconnect((MasterConnectionInfo) connectionInfo);
        }
    }

    public void disconnect(MasterConnectionInfo connectionInfo) {
        slaves.remove(connectionInfo);
    }

    @Override
    public int getProperty(String key, int defaultValue) throws RemoteException {
        return SETTINGS.getProperty(key, defaultValue);
    }

    @Override
    public void setProperty(String key, int value) throws RemoteException {
        SETTINGS.setProperty(key, value);
    }

    public List<MasterConnectionInfo> getSlaves() {
        return slaves;
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

    public void kick(MasterConnectionInfo connectionInfo) {
        try {
            connectionInfo.getSlave().shutdown();
        } catch (RemoteException ex) {
            log.error("Attempt to kick [{}] resulted in an error.", connectionInfo, ex);
        }
        slaves.remove(connectionInfo);
    }

    public void startNewBattle() {
        combatants = new ArrayList<>();
        currentIndex = 0;
        battleStarted = false;
        updateAll(true);
    }

    public void updateAll(boolean refreshCombatants) {
        getFrame().refreshBattle(combatants, currentIndex);
        slaves.forEach((connectionInfo) -> {
            executor.submit(() -> {
                ISlave slave = connectionInfo.getSlave();
                try {
                    slave.refreshView(refreshCombatants);
                    connectionInfo.resetCounter();
                } catch (RemoteException e) {
                    int failures = connectionInfo.increaseCounter();
                    log.error("Unable to refresh slave [{}] [{}] times in a row.", connectionInfo, failures, e);
                    if (failures >= 3) {
                        kick(connectionInfo);
                        log.warn("Slave [{}] has [{}] failures and has been kicked.", connectionInfo, failures);
                    }
                }
            });
        });
    }

    @Override
    protected void shutdownHook() {
        slaves.forEach((connectionInfo) -> {
            try {
                connectionInfo.getSlave().shutdown();
            } catch (RemoteException e) {
                log.warn("Unable to shutdown slave [{}]", connectionInfo, e);
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
