/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.wouter.dndbattle.utils.Settings;

/**
 *
 * @author Wouter
 */
public interface IMaster extends Remote {

    public IMasterConnectionInfo connect(ISlave slave) throws RemoteException;

    public IMasterConnectionInfo connect(ISlave slave, String name) throws RemoteException;

    public void disconnect(ISlave slave) throws RemoteException;

    public Settings getSettings() throws RemoteException;
}
