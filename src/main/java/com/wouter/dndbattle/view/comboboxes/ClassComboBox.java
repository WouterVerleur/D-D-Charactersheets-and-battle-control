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
package com.wouter.dndbattle.view.comboboxes;

import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.impl.Beast;
import com.wouter.dndbattle.objects.impl.Enemy;
import com.wouter.dndbattle.objects.impl.Npc;
import com.wouter.dndbattle.objects.impl.Player;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author wverl
 */
public class ClassComboBox extends CustomComboBox<Class<? extends ICharacter>> {

    private static final Class<? extends ICharacter>[] CLASSES = new Class[]{Beast.class, Enemy.class, Npc.class, Player.class};
    private static final ClassCellRenderer RENDERER = new ClassCellRenderer();

    public ClassComboBox() {
        super(CLASSES);
        setRenderer(RENDERER);
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
