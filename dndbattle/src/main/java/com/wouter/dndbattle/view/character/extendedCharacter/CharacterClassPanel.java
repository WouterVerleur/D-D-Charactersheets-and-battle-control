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
package com.wouter.dndbattle.view.character.extendedCharacter;

import com.wouter.dndbattle.objects.impl.CharacterClass;
import java.awt.event.ItemEvent;

/**
 *
 * @author wverl
 */
public class CharacterClassPanel extends javax.swing.JPanel {

    private final CharacterClass characterClass;
    private final ExtendedCharacterPanel extendedCharacterPanel;

    public CharacterClassPanel(CharacterClass characterClass, ExtendedCharacterPanel extendedCharacterPanel) {
        this.characterClass = characterClass;
        this.extendedCharacterPanel = extendedCharacterPanel;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfName = new javax.swing.JTextField();
        cbDice = new com.wouter.dndbattle.view.comboboxes.DiceComboBox();
        sLevel = new javax.swing.JSpinner();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tfName.setText(characterClass.getName());
        tfName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNameActionPerformed(evt);
            }
        });

        cbDice.setSelectedItem(characterClass.getHitDice());
        cbDice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDiceItemStateChanged(evt);
            }
        });

        sLevel.setModel(new javax.swing.SpinnerNumberModel(characterClass.getLevel(), 1, null, 1));
        sLevel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sLevelStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tfName, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
            .addComponent(cbDice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sLevel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNameActionPerformed
        if (tfName.getText() == null || tfName.getText().isEmpty()) {
            characterClass.setName(tfName.getText());
            extendedCharacterPanel.saveCharacter();
        } else {
            extendedCharacterPanel.removeClass(characterClass);
        }
    }//GEN-LAST:event_tfNameActionPerformed

    private void sLevelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sLevelStateChanged
        characterClass.setLevel((int) sLevel.getValue());
        extendedCharacterPanel.saveCharacter();
    }//GEN-LAST:event_sLevelStateChanged

    private void cbDiceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDiceItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            characterClass.setHitDice(cbDice.getSelectedItem());
            extendedCharacterPanel.saveCharacter();
        }
    }//GEN-LAST:event_cbDiceItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.wouter.dndbattle.view.comboboxes.DiceComboBox cbDice;
    private javax.swing.JSpinner sLevel;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
