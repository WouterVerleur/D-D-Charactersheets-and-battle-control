/*
 * Copyright (C) 2020 Wouter
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
package org.dndbattle.view.client;


import java.util.List;

import javax.swing.JFrame;

import org.dndbattle.core.IHost;
import org.dndbattle.core.impl.Client;
import org.dndbattle.objects.ICombatant;

import static org.dndbattle.utils.Settings.CLIENT_LOCATION_X;
import static org.dndbattle.utils.Settings.CLIENT_LOCATION_Y;
import static org.dndbattle.utils.Settings.CLIENT_SIZE_HEIGHT;
import static org.dndbattle.utils.Settings.CLIENT_SIZE_STATE;
import static org.dndbattle.utils.Settings.CLIENT_SIZE_WIDTH;

/**
 *
 * @author Wouter
 */
public abstract class AbstractClientFrame extends JFrame {

  private final Client client;

  protected AbstractClientFrame(IHost host, String ip) {
    this.client = new Client(host, this, ip);
    setLocation(client.getProperty(CLIENT_LOCATION_X, Integer.MIN_VALUE), client.getProperty(CLIENT_LOCATION_Y, Integer.MIN_VALUE));
    int width = client.getProperty(CLIENT_SIZE_WIDTH, getPreferredSize().width);
    int height = client.getProperty(CLIENT_SIZE_HEIGHT, getPreferredSize().width);
    setSize(width, height);
    setExtendedState(client.getProperty(CLIENT_SIZE_STATE, 0));
  }

  public abstract void refreshBattle(List<ICombatant> combatants, int currentIndex);

  public abstract void refreshCombatants(List<ICombatant> combatants);

  public Client getClient() {
    return client;
  }

}
