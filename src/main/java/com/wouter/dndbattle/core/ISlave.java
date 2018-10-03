/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.core;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.wouter.dndbattle.objects.ICombatant;

/**
 *
 * @author Wouter
 */
public interface ISlave extends Remote, Serializable {

    public void refreshView(List<ICombatant> combatants, int activeIndex) throws RemoteException;

    public void setConnectionInfo(IMasterConnectionInfo connectionInfo) throws RemoteException;

    public void shutdown() throws RemoteException;

    public String getIp() throws RemoteException;

    public String getName() throws RemoteException;
}
