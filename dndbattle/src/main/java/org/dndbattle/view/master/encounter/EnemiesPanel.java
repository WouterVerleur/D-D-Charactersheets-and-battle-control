/*
 * Copyright (C) 2019 wverl
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
package org.dndbattle.view.master.encounter;

import static org.dndbattle.utils.EncounterXpCalculator.getEncounterMultiplier;
import static org.dndbattle.view.master.encounter.EncounterCalculator.getListModel;

import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.dndbattle.objects.ICharacter;
import org.dndbattle.objects.impl.character.Enemy;
import org.dndbattle.utils.Characters;

/**
 *
 * @author wverl
 */
public class EnemiesPanel extends javax.swing.JPanel implements IEncounterCombatantPanelParent {

    private static final Characters CHARACTERS = Characters.getInstance();

    private final EncounterCalculator calculator;
    private final Map<ICharacter, EncounterCombantantPanel> characterMap = new HashMap<>();
    private int totalXp;

    /**
     * Creates new form EnemiesPanel
     *
     * @param calculator
     */
    public EnemiesPanel(final EncounterCalculator calculator) {
        this.calculator = calculator;
        initComponents();
    }

    @Override
    public void update() {
        int amountOfEnemies = 0;
        int totalExp = 0;
        for (EncounterCombantantPanel entry : characterMap.values()) {
            if (entry.getAmount() <= 0) {
                removeCharacter(entry);
            } else {
                amountOfEnemies += entry.getAmount();
                totalExp += (entry.getExp() * entry.getAmount());
            }
        }
        lTotal.setText(Integer.toString(totalExp));

        double multiplier = getEncounterMultiplier(calculator.getPartySize(), amountOfEnemies);
        lMultiplier.setText(Double.toString(multiplier));
        int adjusted = (int) (multiplier * (double) totalExp);
        lAdjusted.setText(Integer.toString(adjusted));

        this.totalXp = adjusted;

        calculator.updateEnemies();
    }

    public int getTotalXp() {
        return totalXp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        spEnemyCombatants = new javax.swing.JScrollPane();
        pEnemyCombatants = new javax.swing.JPanel();
        bAddEnemy = new javax.swing.JButton();
        cbEnemyClass = new org.dndbattle.view.comboboxes.ClassComboBox();
        splEnemy = new javax.swing.JScrollPane();
        lEnemy = new javax.swing.JList<>();
        lTotal = new javax.swing.JLabel();
        lTotalText = new javax.swing.JLabel();
        lMultiplier = new javax.swing.JLabel();
        lMultiplierText = new javax.swing.JLabel();
        lAdjusted = new javax.swing.JLabel();
        lAdjustedText = new javax.swing.JLabel();
        lResult = new javax.swing.JLabel();
        lResultText = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        pEnemyCombatants.setLayout(new java.awt.GridLayout(0, 1));
        spEnemyCombatants.setViewportView(pEnemyCombatants);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(spEnemyCombatants, gridBagConstraints);

        bAddEnemy.setText("<");
        bAddEnemy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddEnemyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bAddEnemy, gridBagConstraints);

        cbEnemyClass.setSelectedItem(Enemy.class);
        cbEnemyClass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEnemyClassItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(cbEnemyClass, gridBagConstraints);

        lEnemy.setModel(getListModel(Enemy.class));
        splEnemy.setViewportView(lEnemy);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(splEnemy, gridBagConstraints);

        lTotal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lTotal, gridBagConstraints);

        lTotalText.setText("Total XP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(lTotalText, gridBagConstraints);

        lMultiplier.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lMultiplier, gridBagConstraints);

        lMultiplierText.setText("Multiplier");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(lMultiplierText, gridBagConstraints);

        lAdjusted.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lAdjusted, gridBagConstraints);

        lAdjustedText.setText("Adjusted XP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(lAdjustedText, gridBagConstraints);

        lResult.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        add(lResult, gridBagConstraints);

        lResultText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lResultText.setText("Result");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lResultText, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bAddEnemyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddEnemyActionPerformed
        addCharacter(lEnemy);
    }//GEN-LAST:event_bAddEnemyActionPerformed

    private void addCharacter(JList<ICharacter> list) {
        ICharacter selectedValue = list.getSelectedValue();
        if (selectedValue != null) {
            if (characterMap.containsKey(selectedValue)) {
                characterMap.get(selectedValue).addOne();
            } else {
                final EncounterCombantantPanel ecp = new EncounterCombantantPanel(this, selectedValue);
                characterMap.put(selectedValue, ecp);
                pEnemyCombatants.add(ecp);
            }
            update();
        }
    }

    public void removeCharacter(EncounterCombantantPanel panel) {
        characterMap.remove(panel.getCharacter());
        pEnemyCombatants.remove(panel);
    }

    private void cbEnemyClassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEnemyClassItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            final DefaultListModel model = (DefaultListModel) lEnemy.getModel();
            model.removeAllElements();
            lEnemy.clearSelection();
            CHARACTERS.getCharacters(cbEnemyClass.getSelectedItem()).forEach((character) -> {
                model.addElement(character);
            });
        }
    }//GEN-LAST:event_cbEnemyClassItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddEnemy;
    private org.dndbattle.view.comboboxes.ClassComboBox cbEnemyClass;
    private javax.swing.JLabel lAdjusted;
    private javax.swing.JLabel lAdjustedText;
    private javax.swing.JList<ICharacter> lEnemy;
    private javax.swing.JLabel lMultiplier;
    private javax.swing.JLabel lMultiplierText;
    private javax.swing.JLabel lResult;
    private javax.swing.JLabel lResultText;
    private javax.swing.JLabel lTotal;
    private javax.swing.JLabel lTotalText;
    private javax.swing.JPanel pEnemyCombatants;
    private javax.swing.JScrollPane spEnemyCombatants;
    private javax.swing.JScrollPane splEnemy;
    // End of variables declaration//GEN-END:variables

    public void setResult(String result) {
        lResult.setText(result);
    }
}
