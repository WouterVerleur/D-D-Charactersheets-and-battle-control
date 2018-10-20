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
package com.wouter.dndbattle.view.master.character;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;
import com.wouter.dndbattle.objects.impl.AbstractExtendedCharacter;
import com.wouter.dndbattle.utils.Characters;
import com.wouter.dndbattle.utils.GlobalUtils;
import com.wouter.dndbattle.view.IUpdateablePanel;
import com.wouter.dndbattle.view.comboboxes.ClassComboBox;
import com.wouter.dndbattle.view.master.MasterCharactersPanel;
import com.wouter.dndbattle.view.master.character.abiliyAndSkill.AbilityAndSkillPanel;
import com.wouter.dndbattle.view.master.character.extendedCharacter.ExtendedCharacterPanel;
import com.wouter.dndbattle.view.master.character.spells.SpellOverviewPanel;
import com.wouter.dndbattle.view.master.character.weapon.WeaponProficiencyPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class CharacterPanel extends javax.swing.JPanel implements IUpdateablePanel {

    private static final Logger log = LoggerFactory.getLogger(CharacterPanel.class);

    private final AbstractCharacter character;
    private final MasterCharactersPanel presetPanel;

    public CharacterPanel(AbstractCharacter character) {
        this(character, null);
    }

    public CharacterPanel(AbstractCharacter character, MasterCharactersPanel presetPanel) {
        this.character = character;
        this.presetPanel = presetPanel;
        initComponents();
    }

    public void updateAll() {
        if (presetPanel != null) {
            presetPanel.updateList();
        }
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

    private void createTabs() {
        tpCharacterPages.addTab("Abilities", new AbilityAndSkillPanel(character, this));
        if (character instanceof AbstractExtendedCharacter) {
            tpCharacterPages.addTab("Character", new ExtendedCharacterPanel((AbstractExtendedCharacter) character, this));
        }
        tpCharacterPages.addTab("Weapons", new WeaponProficiencyPanel(character));
        tpCharacterPages.addTab("Spells", new SpellOverviewPanel(character));
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

        lName = new javax.swing.JLabel();
        tpCharacterPages = new javax.swing.JTabbedPane();
        bRename = new javax.swing.JButton();
        bRoll20 = new javax.swing.JButton();
        bChangeClass = new javax.swing.JButton();
        bDelete = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        lName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lName.setText(character.getName());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lName, gridBagConstraints);

        tpCharacterPages.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tpCharacterPages.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        createTabs();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(tpCharacterPages, gridBagConstraints);

        bRename.setText("Rename");
        bRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRenameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bRename, gridBagConstraints);

        bRoll20.setText("Roll20.net");
        bRoll20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRoll20ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(bRoll20, gridBagConstraints);

        bChangeClass.setText("Change class");
        bChangeClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bChangeClassActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(bChangeClass, gridBagConstraints);

        bDelete.setText("Delete");
        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bDelete, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bRoll20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRoll20ActionPerformed
        GlobalUtils.browseCharacter(character);
    }//GEN-LAST:event_bRoll20ActionPerformed

    private void bRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRenameActionPerformed
        String newName = JOptionPane.showInputDialog(this, "Please enter the new name", "Rename", JOptionPane.QUESTION_MESSAGE);
        ICharacter newChar = character.clone();
        if (newChar instanceof AbstractCharacter) {
            ((AbstractCharacter) newChar).setName(newName);
            if (Characters.getInstance().add(newChar)) {
                Characters.getInstance().remove(character);
            }
        }
        presetPanel.updateList();
    }//GEN-LAST:event_bRenameActionPerformed

    private void bDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Are you sure you wish to remove " + character + "?\nThis cannot be undone!",
                "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            Characters.getInstance().remove(character);
        }
    }//GEN-LAST:event_bDeleteActionPerformed

    private void bChangeClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bChangeClassActionPerformed
        ClassComboBox comboBox = new ClassComboBox();
        comboBox.setSelectedItem(character.getClass());
        JOptionPane.showMessageDialog(this, comboBox, "Please select the class", JOptionPane.QUESTION_MESSAGE);
        Class<? extends ICharacter> selection = comboBox.getSelectedItem();
        if (selection != null && selection != character.getClass()
                && JOptionPane.showConfirmDialog(this, "Are you sure you wish to change " + character + " into a " + selection.getSimpleName() + ". Some information may be lost in the process.", "Please confirm change", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                ICharacter newChar = selection.getDeclaredConstructor(ICharacter.class).newInstance(character);
                if (Characters.getInstance().add(newChar)) {
                    Characters.getInstance().remove(character);
                }
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                log.error("Woops this went wrong", e);
            }
        }
        presetPanel.updateList();
    }//GEN-LAST:event_bChangeClassActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bChangeClass;
    private javax.swing.JButton bDelete;
    private javax.swing.JButton bRename;
    private javax.swing.JButton bRoll20;
    private javax.swing.JLabel lName;
    private javax.swing.JTabbedPane tpCharacterPages;
    // End of variables declaration//GEN-END:variables
}
