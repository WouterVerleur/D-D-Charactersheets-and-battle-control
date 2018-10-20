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
package com.wouter.dndbattle.view.master.character.spells;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.objects.enums.AbilityType;
import com.wouter.dndbattle.objects.enums.SpellLevel;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;
import com.wouter.dndbattle.objects.impl.Spell;
import com.wouter.dndbattle.utils.Characters;
import com.wouter.dndbattle.utils.GlobalUtils;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.utils.Spells;
import com.wouter.dndbattle.view.IUpdateablePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.wouter.dndbattle.utils.Settings.CHARACTER_SPELLS_COLUMNS;

/**
 *
 * @author Wouter
 */
public class SpellOverviewPanel extends javax.swing.JPanel implements IUpdateablePanel {

    private static final Logger log = LoggerFactory.getLogger(SpellOverviewPanel.class);

    public static final String ABILITY_FORMAT = "%d / %s";
    private static final int DEFAULT_COLUMNS = 5;
    private static final Settings SETTINGS = Settings.getInstance();

    private final AbstractCharacter character;
    private final GridLayout spellColumnsLayout = new GridLayout(0, getSpellColumns());

    public SpellOverviewPanel(AbstractCharacter character) {
        this.character = character;
        initComponents();
        updatePanels();
    }

    @Override
    public void update() {
        updatePanels();
        updateLabels();
    }

    private void updatePanels() {
        pSpells.removeAll();

        List<ISpell> mySpells = character.getSpells();
        List<ISpell> spells = Spells.getInstance().getAll();

        final Border eBorder = new EtchedBorder(EtchedBorder.LOWERED);

        SpellLevel currentLevel = null;
        JPanel currentPanel = null;

        for (int i = 0; i < spells.size(); i++) {
            ISpell spell = spells.get(i);

            if (currentPanel == null || currentLevel == null || !currentLevel.equals(spell.getLevel())) {
                currentPanel = new JPanel(spellColumnsLayout);
                currentPanel.setBorder(new TitledBorder(eBorder, spell.getLevel().toString(), TitledBorder.LEADING, TitledBorder.TOP));

                GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 1;
                gridBagConstraints.weighty = 1;
                gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);

                pSpells.add(currentPanel, gridBagConstraints);

                currentLevel = spell.getLevel();
            }

            SpellCheckBox checkBox = new SpellCheckBox(spell);
            checkBox.setSelected(mySpells.contains(spell));
            currentPanel.add(checkBox);
        }
    }

    private static int getSpellColumns() {
        return SETTINGS.getProperty(CHARACTER_SPELLS_COLUMNS, DEFAULT_COLUMNS);
    }

    private void updateLabels() {
        lSpellcastingAbility.setText(getAbilityString());
        lSpellSaveDC.setText(getSpellSaveDC());
        lSpellAttackBonus.setText(getSpellAttackBonus());
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

        cbSpellModifier = new com.wouter.dndbattle.view.comboboxes.AbilityTypeComboBox();
        lSpellcastingAbility = new javax.swing.JLabel();
        lSpellSaveDC = new javax.swing.JLabel();
        lSpellAttackBonus = new javax.swing.JLabel();
        spSpells = new javax.swing.JScrollPane();
        pSpells = new javax.swing.JPanel();
        lSpellSlots = new javax.swing.JLabel();
        spSpellSlots = new javax.swing.JScrollPane();
        pSpellSlots = new javax.swing.JPanel();
        sSpellColumns = new javax.swing.JSlider();

        setLayout(new java.awt.GridBagLayout());

        cbSpellModifier.setSelectedItem(character.getSpellCastingAbility());
        cbSpellModifier.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSpellModifierItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(cbSpellModifier, gridBagConstraints);

        lSpellcastingAbility.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lSpellcastingAbility.setText(getAbilityString());
        lSpellcastingAbility.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Spellcasting Ability", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lSpellcastingAbility, gridBagConstraints);

        lSpellSaveDC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lSpellSaveDC.setText(getSpellSaveDC());
        lSpellSaveDC.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Spell Save DC", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lSpellSaveDC, gridBagConstraints);

        lSpellAttackBonus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lSpellAttackBonus.setText(getSpellAttackBonus());
        lSpellAttackBonus.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Spell Attack Bonus", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(lSpellAttackBonus, gridBagConstraints);

        spSpells.setBorder(null);

        pSpells.setLayout(new java.awt.GridBagLayout());
        spSpells.setViewportView(pSpells);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(spSpells, gridBagConstraints);

        lSpellSlots.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lSpellSlots.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lSpellSlots.setText("SpellSlots");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(lSpellSlots, gridBagConstraints);

        spSpellSlots.setBorder(null);

        pSpellSlots.setLayout(new java.awt.GridLayout(0, 1));

        for (SpellLevel level: SpellLevel.values()){
            if (level != SpellLevel.CANTRIP && level != SpellLevel.FEATURE){
                pSpellSlots.add(new SpellSlotPanel(character, level));
            }
        }

        spSpellSlots.setViewportView(pSpellSlots);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(spSpellSlots, gridBagConstraints);

        sSpellColumns.setMaximum(10);
        sSpellColumns.setMinimum(1);
        sSpellColumns.setMinorTickSpacing(1);
        sSpellColumns.setPaintTicks(true);
        sSpellColumns.setSnapToTicks(true);
        sSpellColumns.setValue(getSpellColumns());
        sSpellColumns.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sSpellColumnsStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        add(sSpellColumns, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cbSpellModifierItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSpellModifierItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            character.setSpellCastingAbility(cbSpellModifier.getSelectedItem());
            saveCharacter();
            update();
        }
    }//GEN-LAST:event_cbSpellModifierItemStateChanged

    private void sSpellColumnsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sSpellColumnsStateChanged
        SETTINGS.setProperty(CHARACTER_SPELLS_COLUMNS, sSpellColumns.getValue());
        spellColumnsLayout.setColumns(sSpellColumns.getValue());
        for (Component component : pSpells.getComponents()) {
            component.revalidate();
        }
    }//GEN-LAST:event_sSpellColumnsStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.wouter.dndbattle.view.comboboxes.AbilityTypeComboBox cbSpellModifier;
    private javax.swing.JLabel lSpellAttackBonus;
    private javax.swing.JLabel lSpellSaveDC;
    private javax.swing.JLabel lSpellSlots;
    private javax.swing.JLabel lSpellcastingAbility;
    private javax.swing.JPanel pSpellSlots;
    private javax.swing.JPanel pSpells;
    private javax.swing.JSlider sSpellColumns;
    private javax.swing.JScrollPane spSpellSlots;
    private javax.swing.JScrollPane spSpells;
    // End of variables declaration//GEN-END:variables

    public void saveCharacter() {
        character.sortSpells();
        Characters.getInstance().update(character);
    }

    private String getAbilityString() {
        AbilityType spellAbility = character.getSpellCastingAbility();
        if (spellAbility != null) {
            return String.format(ABILITY_FORMAT, character.getAbilityScore(spellAbility), GlobalUtils.modifierToString(character.getAbilityModifier(spellAbility)));
        }
        return " ";
    }

    private String getSpellAttackBonus() {
        AbilityType spellAbility = character.getSpellCastingAbility();
        int modifier = character.getProficiencyScore();
        if (spellAbility != null) {
            modifier += character.getAbilityModifier(spellAbility);
        }
        return GlobalUtils.modifierToString(modifier);
    }

    private String getSpellSaveDC() {
        AbilityType spellAbility = character.getSpellCastingAbility();
        int modifier = 8 + character.getProficiencyScore();
        if (spellAbility != null) {
            modifier += character.getAbilityModifier(spellAbility);
        }
        return Integer.toString(modifier);
    }

    public void removeSpell(Spell spell) {
        character.removeSpell(spell);
    }

    private class SpellCheckBox extends JCheckBox {

        public SpellCheckBox(ISpell spell) {
            super(spell.getName());
            log.trace("Creating checkbox for spell [{}]", spell);
            addActionListener((ActionEvent e) -> {
                if (isSelected()) {
                    character.addSpell(spell);
                } else {
                    character.removeSpell(spell);
                }
                saveCharacter();
            });
        }
    }
}
