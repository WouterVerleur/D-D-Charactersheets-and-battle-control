/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Wouter
 */
public interface IMaster extends Remote {

    public void connect(ISlave slave) throws RemoteException;

    public void connect(ISlave slave, String name) throws RemoteException;

    public void disconnect(ISlave slave) throws RemoteException;

    public int getProperty(String key, int defaultValue) throws RemoteException;

    public void setProperty(String key, int value) throws RemoteException;

}
