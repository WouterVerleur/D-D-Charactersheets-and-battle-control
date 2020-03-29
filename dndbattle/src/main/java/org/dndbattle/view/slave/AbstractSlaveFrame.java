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
package org.dndbattle.view.slave;

import static org.dndbattle.utils.Settings.SLAVE_LOCATION_X;
import static org.dndbattle.utils.Settings.SLAVE_LOCATION_Y;
import static org.dndbattle.utils.Settings.SLAVE_SIZE_HEIGHT;
import static org.dndbattle.utils.Settings.SLAVE_SIZE_STATE;
import static org.dndbattle.utils.Settings.SLAVE_SIZE_WIDTH;

import java.util.List;

import javax.swing.JFrame;

import org.dndbattle.core.IMaster;
import org.dndbattle.core.impl.Slave;
import org.dndbattle.objects.ICombatant;

/**
 *
 * @author Wouter
 */
public abstract class AbstractSlaveFrame extends JFrame {

    private final Slave slave;

    protected AbstractSlaveFrame(IMaster master, String ip) {
        this.slave = new Slave(master, this, ip);
        setLocation(slave.getProperty(SLAVE_LOCATION_X, Integer.MIN_VALUE), slave.getProperty(SLAVE_LOCATION_Y, Integer.MIN_VALUE));
        int width = slave.getProperty(SLAVE_SIZE_WIDTH, getPreferredSize().width);
        int height = slave.getProperty(SLAVE_SIZE_HEIGHT, getPreferredSize().width);
        setSize(width, height);
        setExtendedState(slave.getProperty(SLAVE_SIZE_STATE, 0));
    }

    public abstract void refreshBattle(List<ICombatant> combatants, int currentIndex);

    public abstract void refreshCombatants(List<ICombatant> combatants);

    public Slave getSlave() {
        return slave;
    }

}
