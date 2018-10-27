/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.core.impl;

import java.rmi.RemoteException;
import java.util.List;

import com.wouter.dndbattle.core.IMaster;
import com.wouter.dndbattle.core.IMasterConnectionInfo;
import com.wouter.dndbattle.core.ISlave;
import com.wouter.dndbattle.objects.ICombatant;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.slave.SlaveFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Slave extends AbstractRemoteConnector implements ISlave {

    private static final Logger log = LoggerFactory.getLogger(Slave.class);

    private static final Settings SETTINGS = Settings.getInstance();

    private final SlaveFrame frame;
    private IMaster master;

    private IMasterConnectionInfo connectionInfo;
    private final String ip;

    private List<ICombatant> combatants;

    public Slave(IMaster master, SlaveFrame frame, String ip) {
        this.master = master;
        this.frame = frame;
        this.ip = ip;
    }

    /**
     * @return the connectionInfo
     */
    @Override
    public IMasterConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    /**
     * @param connectionInfo the connectionInfo to set
     */
    @Override
    public void setConnectionInfo(IMasterConnectionInfo connectionInfo) {
        if (frame != null) {
            frame.setTitle(connectionInfo.getSlaveTitle());
        }
        this.connectionInfo = connectionInfo;
    }

    public int getProperty(String key, int defaultValue) {
        if (connectionInfo != null && connectionInfo.isLocalhost()) {
            try {
                return master.getProperty(key, defaultValue);
            } catch (RemoteException e) {
                log.error("Error while retrieving settings", e);
            }
        }
        return SETTINGS.getProperty(key, defaultValue);
    }

    public void setProperty(String key, int value) {
        if (connectionInfo != null && connectionInfo.isLocalhost()) {
            try {
                master.setProperty(key, value);
                return;
            } catch (RemoteException e) {
                log.error("Error while retrieving settings", e);
            }
        }
        SETTINGS.setProperty(key, value);
    }

    @Override
    protected void shutdownHook() {
        if (master != null) {
            try {
                master.disconnect(this);
            } catch (RemoteException e) {
                log.error("Unable to disconnect.", e);
            }
        }
    }

    @Override
    public void refreshView(boolean refreshCombatants) throws RemoteException {
        try {
            if (refreshCombatants || combatants == null) {
                combatants = master.getCombatants();
                frame.refreshCombatants(combatants);
            }
            frame.refreshBattle(combatants, master.getCurrentIndex());
        } catch (RemoteException e) {
            log.error("Unable to refresh the view", e);
        }

    }

    @Override
    public void shutdown() {
        master = null;
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
