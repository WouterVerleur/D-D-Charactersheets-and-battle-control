/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.impl;

import static com.wouter.dndbattle.utils.Settings.ROLLFORDEATH;
import static com.wouter.dndbattle.utils.Settings.SLAVE_TITLE;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import com.wouter.dndbattle.IMaster;
import com.wouter.dndbattle.IMasterConnectionInfo;
import com.wouter.dndbattle.ISlave;
import com.wouter.dndbattle.objects.ICombatant;
import com.wouter.dndbattle.objects.impl.Combatant;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.master.MasterFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Master extends AbstractRemoteConnector implements IMaster {

    private static final Logger log = LoggerFactory.getLogger(Master.class);

    private static final Settings SETTINGS = Settings.getInstance();
    private boolean battleStarted;
    private List<ICombatant> combatants = new ArrayList<>();
    private final MasterFrame frame;
    private final List<ISlave> slaves = new ArrayList<>();
    private int activeIndex = 0;

    public Master(MasterFrame frame) {
        this.frame = frame;
    }

    public void addCombatant(Combatant combatant) {
        ICombatant currectActive = null;
        if (combatants.size() > 0 && battleStarted) {
            currectActive = combatants.get(activeIndex);
        }
        combatants.add(combatant);
        Collections.sort(combatants);
        if (currectActive != null) {
            activeIndex = combatants.indexOf(currectActive);
        }
        updateAll();
    }

    @Override
    public IMasterConnectionInfo connect(ISlave slave) throws RemoteException {
        return connect(slave, SLAVE_TITLE);
    }

    @Override
    public IMasterConnectionInfo connect(ISlave slave, String name) throws RemoteException {
        slaves.add(slave);
        slave.refreshView(combatants, activeIndex);
        boolean localhost = false;
        try {
            localhost = RemoteServer.getClientHost().equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress());
        } catch (ServerNotActiveException | UnknownHostException e) {
            log.error("Error while determining if connection if from localhost", e);
        }
        log.debug("Recieved new slave connection from [{}] for witch localhost was [{}]", name, localhost);
        return new MasterConnectionInfo(SETTINGS.getProperty(SLAVE_TITLE, "Slave"), localhost);
    }

    @Override
    public void disconnect(ISlave slave) throws RemoteException {
        slaves.remove(slave);
    }

    @Override
    public int getProperty(String key, int defaultValue) throws RemoteException {
        return SETTINGS.getProperty(key, defaultValue);
    }

    @Override
    public void setProperty(String key, int value) throws RemoteException {
        SETTINGS.setProperty(key, value);
    }

    public void nextTurn() {
        battleStarted = true;
        boolean keepSearching = true;
        activeIndex++;
        while (keepSearching) {
            if (combatants.isEmpty()) {
                break;
            }
            if (activeIndex >= combatants.size()) {
                activeIndex = 0;
            }
            ICombatant next = combatants.get(activeIndex);
            if (next.isDead()) {
                combatants.remove(next);
                continue;
            }
            if (next.getHealth() == 0 && !SETTINGS.getProperty(ROLLFORDEATH, true)) {
                log.debug("Adding deathroll to [{}]", next);
                JOptionPane.showMessageDialog(frame, "An automatic deathroll was added to " + next, "Automatic deathroll.", JOptionPane.INFORMATION_MESSAGE);
                ((Combatant) next).addDeathRoll();
                if (next.isDead()) {
                    combatants.remove(next);
                } else {
                    activeIndex++;
                }
                continue;
            }
            keepSearching = false;
        }
        if (combatants.isEmpty()) {
            startNewBattle();
        }
        updateAll();
    }

    public void shutdown() {
        System.exit(0);
    }

    public void startNewBattle() {
        combatants = new ArrayList<>();
        activeIndex = 0;
        battleStarted = false;
        updateAll();
    }

    public void updateAll() {
        frame.refreshBattle(combatants, activeIndex);
        for (ISlave slave : slaves) {
            try {
                slave.refreshView(combatants, activeIndex);
            } catch (RemoteException e) {
                System.out.println("Unable to refresh a slave " + e);
            }
        }
    }

    @Override
    protected void shutdownHook() {
        for (ISlave slave : slaves) {
            try {
                slave.shutdown();
            } catch (RemoteException e) {
                System.out.println("Unable to shutdown slave " + e);
            }
        }
    }

    public List<ICombatant> getCombatants() {
        return combatants;
    }

    public void setCombatants(List<ICombatant> combatants) {
        Collections.sort(combatants);
        activeIndex = 0;
        this.combatants = combatants;
    }

}
