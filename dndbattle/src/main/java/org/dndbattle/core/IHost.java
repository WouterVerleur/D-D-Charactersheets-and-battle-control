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
package org.dndbattle.core;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.dndbattle.objects.ICombatant;

/**
 *
 * @author Wouter
 */
public interface IHost extends Remote {

  public void connect(IClient client) throws RemoteException;

  public void connect(IClient client, String name) throws RemoteException;

  public void connect(IClient client, String name, String clientIp) throws RemoteException;

  public void disconnect(IHostConnectionInfo connectionInfo) throws RemoteException;

  public int getProperty(String key, int defaultValue) throws RemoteException;

  public void setProperty(String key, int value) throws RemoteException;

  public List<ICombatant> getCombatants() throws RemoteException;

  public int getCurrentIndex() throws RemoteException;

}
