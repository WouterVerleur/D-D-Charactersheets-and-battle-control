/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.view.slave;

import com.wouter.dndbattle.objects.ICharacter;

import java.awt.Color;

import com.wouter.dndbattle.objects.ICombatant;

/**
 *
 * @author Wouter
 */
public class SlaveSubPanel extends javax.swing.JPanel {

    private final ICombatant combatant;
    private final ICharacter character;

    public SlaveSubPanel(ICombatant combatant) {
        this.combatant = combatant;
        this.character = combatant.getCharacter();
        initComponents();
        if (character != null) {
            pbHealth.setVisible(character.isFriendly());
            pbHealthBuff.setVisible(character.isFriendly());
            if (combatant.isDead()) {
                setBackground(Color.DARK_GRAY);
            } else if (combatant.getHealth() == 0) {
                setBackground(Color.GRAY);
            }
        } else {
            setBackground(Color.RED);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lName = new javax.swing.JLabel();
        lDescription = new javax.swing.JLabel();
        pbHealth = new javax.swing.JProgressBar();
        pbHealthBuff = new javax.swing.JProgressBar();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(""), javax.swing.BorderFactory.createEtchedBorder()));

        lName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lName.setText(combatant.getName());

        lDescription.setText(character.getDescription());

        pbHealth.setMaximum(character.getMaxHealth());
        pbHealth.setValue(combatant.getHealth());
        pbHealth.setString(combatant.getHealthString());
        pbHealth.setStringPainted(true);

        pbHealthBuff.setMaximum(1);
        pbHealthBuff.setValue(combatant.getHealthBuff());
        pbHealthBuff.setString(combatant.getHealthBuff()>0?"+"+combatant.getHealthBuff():"");
        pbHealthBuff.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pbHealth, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pbHealthBuff, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pbHealth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbHealthBuff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lDescription;
    private javax.swing.JLabel lName;
    private javax.swing.JProgressBar pbHealth;
    private javax.swing.JProgressBar pbHealthBuff;
    // End of variables declaration//GEN-END:variables
}
