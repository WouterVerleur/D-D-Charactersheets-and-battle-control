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
package org.dndbattle.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class EnabledJComboBoxRenderer extends DefaultListCellRenderer {

  private final ListSelectionModel enabledItems;
  private Color disabledColor = Color.lightGray;

  public EnabledJComboBoxRenderer(ListSelectionModel enabled) {
    super();
    this.enabledItems = enabled;
  }

  public void setDisabledColor(Color disabledColor) {
    this.disabledColor = disabledColor;
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    if (!enabledItems.isSelectedIndex(index)) {
      if (isSelected) {
        c.setBackground(UIManager.getColor("ComboBox.background"));
      } else {
        c.setBackground(super.getBackground());
      }
      c.setForeground(disabledColor);
    } else {
      c.setBackground(super.getBackground());
      c.setForeground(super.getForeground());
    }
    return c;
  }
}
