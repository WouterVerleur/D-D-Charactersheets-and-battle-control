/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.core;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.wouter.dndbattle.objects.ICombatant;

/**
 *
 * @author Wouter
 */
public interface IMaster extends Remote {

    public void connect(ISlave slave) throws RemoteException;

    public void connect(ISlave slave, String name) throws RemoteException;

    public void connect(ISlave slave, String name, String slaveIp) throws RemoteException;

    public void disconnect(IMasterConnectionInfo connectionInfo) throws RemoteException;

    public int getProperty(String key, int defaultValue) throws RemoteException;

    public void setProperty(String key, int value) throws RemoteException;

    public List<ICombatant> getCombatants() throws RemoteException;

    public int getCurrentIndex() throws RemoteException;

}
