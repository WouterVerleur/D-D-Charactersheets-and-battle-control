/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.impl;

import java.rmi.RemoteException;
import java.util.List;

import com.wouter.dndbattle.IMaster;
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
    private final SlaveFrame frame;
    private boolean shutdownRecieved = false;
    private final IMaster master;

    public Slave(IMaster master, SlaveFrame frame) {
        this.master = master;
        this.frame = frame;
    }

    public Settings getSettings() {
        try {
            return master.getSettings();
        } catch (RemoteException e) {
            log.error("Error while retrieving settings", e);
        }
        return Settings.getInstance();
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
