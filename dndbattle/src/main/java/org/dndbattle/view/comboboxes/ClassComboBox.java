/*
 * Copyright (C) 2018 wverl
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
package org.dndbattle.view.comboboxes;

import org.dndbattle.objects.impl.character.Aberration;
import org.dndbattle.objects.impl.character.Elemental;
import org.dndbattle.objects.impl.character.Fey;
import org.dndbattle.objects.impl.character.Enemy;
import org.dndbattle.objects.impl.character.Dragon;
import org.dndbattle.objects.impl.character.Player;
import org.dndbattle.objects.impl.character.Swarm;
import org.dndbattle.objects.impl.character.Beast;
import org.dndbattle.objects.impl.character.Construct;
import org.dndbattle.objects.impl.character.Ooze;
import org.dndbattle.objects.impl.character.Npc;
import org.dndbattle.objects.impl.character.Celestial;
import org.dndbattle.objects.impl.character.Plant;
import org.dndbattle.objects.impl.character.Monstrosity;
import org.dndbattle.objects.impl.character.Giant;
import org.dndbattle.objects.impl.character.Undead;
import org.dndbattle.objects.impl.character.Fiend;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.dndbattle.objects.ICharacter;

/**
 *
 * @author wverl
 */
public class ClassComboBox extends CustomComboBox<Class<? extends ICharacter>> {

    private static final Class<? extends ICharacter>[] CLASSES = new Class[]{
        Player.class,
        Npc.class,
        Enemy.class,
        Aberration.class,
        Beast.class,
        Celestial.class,
        Construct.class,
        Dragon.class,
        Elemental.class,
        Fey.class,
        Fiend.class,
        Giant.class,
        Monstrosity.class,
        Ooze.class,
        Plant.class,
        Swarm.class,
        Undead.class};
    private static final ClassCellRenderer RENDERER = new ClassCellRenderer();

    public ClassComboBox() {
        super(CLASSES);
        setRenderer(RENDERER);
    }

    public static Class<? extends ICharacter>[] getAllClasses() {
        return CLASSES;
    }

    private static class ClassCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Object item = value;
            if (item instanceof Class) {
                item = ((Class) value).getSimpleName();
            }
            return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
        }

    }
}
