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
package com.wouter.dndbattle.view.slave.character;

import static com.wouter.dndbattle.utils.Settings.SLAVE_SPELLS_SEPERATOR;

import java.awt.Component;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.IExtendedCharacter;
import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.utils.GlobalUtils;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.IUpdateablePanel;

/**
 *
 * @author Wouter
 */
public class SlaveCharacterPanel extends javax.swing.JPanel implements IUpdateablePanel {

    private static final Settings SETTINGS = Settings.getInstance();

    private final ICharacter character;

    public SlaveCharacterPanel(ICharacter character) {
        this.character = character;
        setName(character.getName());
        initComponents();
        fillTables();
    }

    public void updateAll() {
        update();
    }

    public int getCurrentTab() {
        return tpCharacterPages.getSelectedIndex();
    }

    public void setCurrentTab(int currentTab) {
        tpCharacterPages.setSelectedIndex(currentTab);
    }

    @Override
    public void update() {
        for (Component component : tpCharacterPages.getComponents()) {
            if (component instanceof IUpdateablePanel) {
                ((IUpdateablePanel) component).update();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lName = new javax.swing.JLabel();
        tpCharacterPages = new javax.swing.JTabbedPane();
        spWeapon = new javax.swing.JScrollPane();
        tWeapons = new javax.swing.JTable();
        spSpells = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDescription = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tSpells = new javax.swing.JTable();

        lName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lName.setText(character.getName());
        lName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lNameMouseClicked(evt);
            }
        });

        tpCharacterPages.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tpCharacterPages.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tpCharacterPages.addTab("Abilities", new com.wouter.dndbattle.view.slave.character.SlaveAbilityAndSkillPanel(character));
        if (character instanceof IExtendedCharacter){
            tpCharacterPages.addTab("Character", new com.wouter.dndbattle.view.slave.character.SlaveExtendedCharacterPanel((IExtendedCharacter) character));
        }

        tWeapons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Attack bonus", "Damage", "Notes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tWeapons.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tWeapons.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        spWeapon.setViewportView(tWeapons);

        tpCharacterPages.addTab("Weapons", spWeapon);

        spSpells.setDividerLocation(250);
        spSpells.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        taDescription.setEditable(false);
        taDescription.setColumns(20);
        taDescription.setLineWrap(true);
        taDescription.setRows(5);
        taDescription.setWrapStyleWord(true);
        jScrollPane1.setViewportView(taDescription);

        spSpells.setBottomComponent(jScrollPane1);

        tSpells.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type", "Casting time", "Range", "Components", "Duration"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tSpells.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tSpells);

        spSpells.setLeftComponent(jScrollPane2);

        if (!character.getSpells().isEmpty()){

            tpCharacterPages.addTab("Spells", spSpells);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tpCharacterPages, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpCharacterPages))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lNameMouseClicked
        GlobalUtils.browseCharacter(character);
    }//GEN-LAST:event_lNameMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lName;
    private javax.swing.JSplitPane spSpells;
    private javax.swing.JScrollPane spWeapon;
    private javax.swing.JTable tSpells;
    private javax.swing.JTable tWeapons;
    private javax.swing.JTextArea taDescription;
    private javax.swing.JTabbedPane tpCharacterPages;
    // End of variables declaration//GEN-END:variables

    private void fillTables() {
        DefaultTableModel weaponModel = (DefaultTableModel) tWeapons.getModel();
        character.getWeapons().forEach((weapon) -> {
            weaponModel.addRow(GlobalUtils.getWeaponRow(character, weapon));
        });
        List<ISpell> spells = character.getSpells();
        if (!spells.isEmpty()) {
            DefaultTableModel spellModel = (DefaultTableModel) tSpells.getModel();
            spells.forEach((spell) -> {
                spellModel.addRow(new Object[]{spell.getName(), spell.getLevel(), spell.getCastingTime(), spell.getRange(), spell.getComponents(), spell.getDuration()});
            });
            spSpells.setDividerLocation(SETTINGS.getProperty(SLAVE_SPELLS_SEPERATOR, spSpells.getDividerLocation()));
            tSpells.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
                if (!evt.getValueIsAdjusting()) {
                    taDescription.setText(spells.get(evt.getFirstIndex()).getDescription());
                }
            });
        }
    }

    public ICharacter getCharacter() {
        return character;
    }
}
