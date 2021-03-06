/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dndbattle.view.master;

import static org.dndbattle.utils.Settings.ROLL_FOR_DEATH;

import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.dndbattle.core.impl.Master;
import org.dndbattle.objects.ICharacter;
import org.dndbattle.objects.ICombatant;
import org.dndbattle.objects.enums.AbilityType;
import org.dndbattle.objects.enums.Size;
import org.dndbattle.objects.enums.SpellLevel;
import org.dndbattle.objects.impl.Combatant;
import org.dndbattle.objects.impl.character.Ooze;
import org.dndbattle.utils.Characters;
import org.dndbattle.utils.GlobalUtils;
import org.dndbattle.utils.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class MasterCombatantPanel extends javax.swing.JPanel {

    private static final Logger log = LoggerFactory.getLogger(MasterCombatantPanel.class);

    private static final Settings SETTINGS = Settings.getInstance();
    private static final String SPELL_SLOT_BUTTON_FORMAT = "Level %s (%d/%d used)";
    private static final String TITLE_FORMAT = "Initiative %d (DEX: %d)";

    private final Combatant combatant;
    private final ICharacter character;
    private final Master master;

    public MasterCombatantPanel(Master master, Combatant combatant) {
        this.master = master;
        this.combatant = combatant;
        this.character = getCombatantCharacter(combatant);
        setName(combatant.getName());
        initComponents();
        if (combatant.isDead()) {
            setBackground(GlobalUtils.getBackgroundDead());
        } else if (combatant.getHealth() == 0) {
            setBackground(GlobalUtils.getBackgroundDown());
        }
    }

    private static ICharacter getCombatantCharacter(ICombatant combatant) {
        if (combatant.isTransformed()) {
            return getCombatantCharacter(combatant.getTransformation());
        }
        return combatant.getCharacter();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lName = new javax.swing.JLabel();
        bInfo = new javax.swing.JButton();
        lDescription = new javax.swing.JLabel();
        cbHitpoints = new javax.swing.JProgressBar();
        pbTempHitpoints = new javax.swing.JProgressBar();
        bDamage = new javax.swing.JButton();
        bHealth = new javax.swing.JButton();
        lTotalDamageRecieved = new javax.swing.JLabel();
        bTempHitpoints = new javax.swing.JButton();
        bDeathRoll = new javax.swing.JButton();
        bLifeRoll = new javax.swing.JButton();
        bSplit = new javax.swing.JButton();
        bTransform = new javax.swing.JButton();
        bPolyMorph = new javax.swing.JButton();
        bLeaveTransformation = new javax.swing.JButton();
        pUseSpellSlots = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), String.format(TITLE_FORMAT, combatant.getInitiative(), combatant.getCharacter().getAbilityScore(AbilityType.DEX)), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        setLayout(new java.awt.GridBagLayout());

        lName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lName.setText(combatant.getFriendlyName());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lName, gridBagConstraints);

        bInfo.setText("Information");
        bInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInfoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(bInfo, gridBagConstraints);

        lDescription.setText(combatant.getFriendlyDescription());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(lDescription, gridBagConstraints);

        cbHitpoints.setMaximum(character.getMaxHealth());
        cbHitpoints.setValue(combatant.getHealth());
        cbHitpoints.setString(combatant.getHealthString());
        cbHitpoints.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(cbHitpoints, gridBagConstraints);

        pbTempHitpoints.setMaximum(1);
        pbTempHitpoints.setValue(combatant.getHealthBuff());
        pbTempHitpoints.setString(combatant.getHealthBuff()>0?"+"+combatant.getHealthBuff():" ");
        pbTempHitpoints.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(pbTempHitpoints, gridBagConstraints);

        bDamage.setText("Give damage");
        bDamage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDamageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bDamage, gridBagConstraints);

        bHealth.setText("Give health");
        bHealth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHealthActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bHealth, gridBagConstraints);

        lTotalDamageRecieved.setText(combatant.getTotalDamageString());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lTotalDamageRecieved, gridBagConstraints);

        bTempHitpoints.setText("Set temporary hitpoints");
        bTempHitpoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTempHitpointsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(bTempHitpoints, gridBagConstraints);

        bDeathRoll.setText("Add death roll");
        bDeathRoll.setEnabled(SETTINGS.getProperty(ROLL_FOR_DEATH, true));
        bDeathRoll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeathRollActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bDeathRoll, gridBagConstraints);

        bLifeRoll.setText("Add life roll");
        bLifeRoll.setEnabled(SETTINGS.getProperty(ROLL_FOR_DEATH, true));
        bLifeRoll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLifeRollActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bLifeRoll, gridBagConstraints);

        bSplit.setText("Split from " + combatant.getSize());
        bSplit.setEnabled(character instanceof Ooze && ((Ooze) character).isCanSplit() && (combatant.getSize().compareTo(((Ooze) character).getLowestSplitSize()) >= 0));
        bSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSplitActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bSplit, gridBagConstraints);

        bTransform.setText("Transform");
        bTransform.setEnabled(checkCanTransform(combatant));
        bTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTransformActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bTransform, gridBagConstraints);

        bPolyMorph.setText("Polymorph");
        bPolyMorph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPolyMorphActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bPolyMorph, gridBagConstraints);

        bLeaveTransformation.setText("Leave transformation");
        bLeaveTransformation.setEnabled(combatant.isTransformed());
        bLeaveTransformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLeaveTransformationActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(bLeaveTransformation, gridBagConstraints);

        pUseSpellSlots.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Use spell slot", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        pUseSpellSlots.setLayout(new javax.swing.BoxLayout(pUseSpellSlots, javax.swing.BoxLayout.X_AXIS));
        createUseSpellSlotButtons();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(pUseSpellSlots, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bDamageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDamageActionPerformed
        combatant.giveDamage(requestNumber("damage"));
        master.updateAll(false);
    }//GEN-LAST:event_bDamageActionPerformed

    private void bHealthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHealthActionPerformed
        combatant.giveHeal(requestNumber("health"));
        master.updateAll(false);
    }//GEN-LAST:event_bHealthActionPerformed

    private void bTempHitpointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTempHitpointsActionPerformed
        combatant.setHealthBuff(requestNumber("temporary hitpoints"));
        master.updateAll(false);
    }//GEN-LAST:event_bTempHitpointsActionPerformed

    private void bDeathRollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeathRollActionPerformed
        combatant.addDeathRoll();
        master.updateAll(false);
    }//GEN-LAST:event_bDeathRollActionPerformed

    private void bLifeRollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLifeRollActionPerformed
        combatant.addLifeRoll();
        master.updateAll(false);
    }//GEN-LAST:event_bLifeRollActionPerformed

    private void bLeaveTransformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLeaveTransformationActionPerformed
        if (combatant.isTransformed()) {
            combatant.leaveTransformation();
        }
        master.updateAll(false);
    }//GEN-LAST:event_bLeaveTransformationActionPerformed

    private void bTransformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTransformActionPerformed
        if (checkCanTransform(combatant)) {
            List<ICharacter> characters = Characters.getInstance().getCharacters(character.getTransformType());
            for (Iterator<ICharacter> iterator = characters.iterator(); iterator.hasNext();) {
                if (checkChallengeRatingToHigh(iterator.next())) {
                    iterator.remove();
                }
            }
            ICharacter[] presets = characters.toArray(new ICharacter[characters.size()]);
            Object selection = JOptionPane.showInputDialog(this, "Message", "Title", JOptionPane.QUESTION_MESSAGE, null, presets, null);
            if (selection != null) {
                combatant.transform((ICharacter) selection, false);
                master.updateAll(true);
            }
        }
    }//GEN-LAST:event_bTransformActionPerformed

    private boolean checkChallengeRatingToHigh(ICharacter transformation) {
        if (transformation.getChallengeRating() == null) {
            return false;
        }
        if (character.getTransformChallengeRating() == null) {
            return true;
        }
        return transformation.getChallengeRating().compareTo(character.getTransformChallengeRating()) > 0;
    }

    private void bPolyMorphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPolyMorphActionPerformed
        CombatantSelectionPanel selectionPanel = new CombatantSelectionPanel();
        switch (JOptionPane.showConfirmDialog(this, selectionPanel, "Select character", JOptionPane.OK_CANCEL_OPTION)) {
            case JOptionPane.OK_OPTION:
                ICharacter selection = selectionPanel.getSelection();
                combatant.transform(selection, true);
                master.updateAll(true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_bPolyMorphActionPerformed

    private void bInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInfoActionPerformed
        master.getFrame().setBattleTab(combatant.getCharacter().getName());
    }//GEN-LAST:event_bInfoActionPerformed

    private void bSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSplitActionPerformed
        int newHealth = Math.floorDiv(combatant.getHealth(), 2);
        Size newSize = combatant.getSize().getOneSmaller();
        combatant.giveHeal(-1 * (combatant.getHealth() - newHealth));
        combatant.setSize(newSize);
        Combatant splitCombatant = new Combatant(character, combatant.getName() + " (Split)", combatant.getInitiative(), newHealth, newSize);
        master.addCombatant(splitCombatant);
        master.updateAll(true);
    }//GEN-LAST:event_bSplitActionPerformed

    private int requestNumber(String description) {
        int value = 0;
        String input = JOptionPane.showInputDialog(this, "Please enter the amount of " + description + " should be given to " + combatant, "Please enter a number.", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                log.error("The input [{}] could not be parsed as a number.", input, e);
                JOptionPane.showMessageDialog(this, "Your input " + input + " is not a valid number.\nTo cancel enter 0.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return value;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDamage;
    private javax.swing.JButton bDeathRoll;
    private javax.swing.JButton bHealth;
    private javax.swing.JButton bInfo;
    private javax.swing.JButton bLeaveTransformation;
    private javax.swing.JButton bLifeRoll;
    private javax.swing.JButton bPolyMorph;
    private javax.swing.JButton bSplit;
    private javax.swing.JButton bTempHitpoints;
    private javax.swing.JButton bTransform;
    private javax.swing.JProgressBar cbHitpoints;
    private javax.swing.JLabel lDescription;
    private javax.swing.JLabel lName;
    private javax.swing.JLabel lTotalDamageRecieved;
    private javax.swing.JPanel pUseSpellSlots;
    private javax.swing.JProgressBar pbTempHitpoints;
    // End of variables declaration//GEN-END:variables

    private boolean checkCanTransform(Combatant combatant) {
        if (combatant.isTransformed()) {
            return checkCanTransform(combatant.getTransformation());
        }
        return combatant.getCharacter().isCanTransform();
    }

    private void createUseSpellSlotButtons() {
        int totalSpellSlots = 0;
        int totalUsedSpellSlots = 0;
        for (SpellLevel level : SpellLevel.values()) {
            if (level != SpellLevel.CANTRIP && level != SpellLevel.FEATURE) {
                final int spellSlots = combatant.getCharacter().getSpellSlotsByLevel(level);
                final int usedSpellSlots = combatant.getUsedSpellSlots(level);

                totalSpellSlots += spellSlots;
                totalUsedSpellSlots += usedSpellSlots;

                if (spellSlots > 0) {
                    JButton button = new JButton(String.format(SPELL_SLOT_BUTTON_FORMAT, level.toString(), usedSpellSlots, spellSlots));
                    button.addActionListener((evt) -> {
                        combatant.useSpellSlot(level);
                        master.updateAll(false);
                    });
                    button.setEnabled(usedSpellSlots < spellSlots);
                    pUseSpellSlots.add(button);
                }
            }
        }
        if (totalSpellSlots > 0) {
            JButton reset = new JButton("Reset");
            reset.addActionListener((evt) -> {
                combatant.resetSpellSlots();
                master.updateAll(false);
            });
            reset.setEnabled(totalUsedSpellSlots > 0);
            pUseSpellSlots.add(reset);
        } else {
            pUseSpellSlots.setVisible(false);
        }
    }
}
