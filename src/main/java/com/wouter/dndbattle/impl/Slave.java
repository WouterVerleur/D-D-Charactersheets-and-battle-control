/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.impl;

import java.rmi.RemoteException;
import java.util.List;

import com.wouter.dndbattle.IMaster;
import com.wouter.dndbattle.IMasterConnectionInfo;
import com.wouter.dndbattle.ISlave;
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
    private final IMaster master;

    private boolean shutdownRecieved = false;
    private IMasterConnectionInfo connectionInfo;

    public Slave(IMaster master, SlaveFrame frame) {
        this.master = master;
        this.frame = frame;
    }

    /**
     * @return the connectionInfo
     */
    public IMasterConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    /**
     * @param connectionInfo the connectionInfo to set
     */
    public void setConnectionInfo(IMasterConnectionInfo connectionInfo) {
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
        if (!shutdownRecieved && master != null) {
            try {
                master.disconnect(this);
            } catch (RemoteException e) {
                log.error("Unable to disconnect.", e);
            }
        }
    }

    @Override
    public void refreshView(final List<ICombatant> combatants, int activeIndex) throws RemoteException {
        frame.showCombatants(combatants, activeIndex);
    }

    @Override
    public void shutdown() throws RemoteException {
        shutdownRecieved = true;
        System.exit(0);
    }
}
