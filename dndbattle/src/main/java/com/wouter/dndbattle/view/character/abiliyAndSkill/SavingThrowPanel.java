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
package com.wouter.dndbattle.view.character.abiliyAndSkill;

import java.awt.event.ItemEvent;

import javax.swing.DefaultComboBoxModel;

import com.wouter.dndbattle.objects.enums.AbilityType;
import static com.wouter.dndbattle.objects.enums.Dice.D20;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;
import com.wouter.dndbattle.utils.Characters;
import com.wouter.dndbattle.utils.GlobalUtils;
import com.wouter.dndbattle.view.character.IUpdateablePanel;
import com.wouter.dndbattle.view.character.abiliyAndSkill.DicePopup;

/**
 *
 * @author Wouter
 */
public class SavingThrowPanel extends javax.swing.JPanel implements IUpdateablePanel {

    private final AbilityAndSkillPanel abilityAndSkillPanel;

    private final AbilityType abilityType;
    private final AbstractCharacter character;

    public SavingThrowPanel(AbstractCharacter character, AbilityType abilityType, AbilityAndSkillPanel abilityAndSkillPanel) {
        this.character = character;
        this.abilityType = abilityType;
        this.abilityAndSkillPanel = abilityAndSkillPanel;
        initComponents();
        updateLabel();
    }

    @Override
    public void update() {
        updateLabel();
    }

    private void updateLabel() {
        lModifier.setText(GlobalUtils.modifierToString(character.getSavingThrowModifier(abilityType)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lModifier = new javax.swing.JLabel();
        cbProficiency = new com.wouter.dndbattle.view.comboboxes.ProficiencyComboBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), abilityType.getFullName(), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        lModifier.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lModifier.setText("0");
        lModifier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lModifierMouseClicked(evt);
            }
        });

        cbProficiency.setSelectedItem(character.getSavingThrowProficiency(abilityType));
        cbProficiency.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProficiencyItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(cbProficiency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lModifier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lModifier)
                .addComponent(cbProficiency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbProficiencyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProficiencyItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            character.setSavingThrowProficiency(abilityType, cbProficiency.getSelectedItem());
            Characters.updateCharacter(character);
            abilityAndSkillPanel.updatePanels();
        }
    }//GEN-LAST:event_cbProficiencyItemStateChanged

    private void lModifierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lModifierMouseClicked
        DicePopup dicePopup = new DicePopup(character.getSavingThrowModifier(abilityType), D20, abilityType.getFullName());
        dicePopup.setLocationRelativeTo(lModifier);
        dicePopup.setVisible(true);
    }//GEN-LAST:event_lModifierMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.wouter.dndbattle.view.comboboxes.ProficiencyComboBox cbProficiency;
    private javax.swing.JLabel lModifier;
    // End of variables declaration//GEN-END:variables
}
