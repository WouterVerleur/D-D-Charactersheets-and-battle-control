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
package com.wouter.dndbattle.view.master.utilities;

import javax.swing.JOptionPane;

import com.wouter.dndbattle.objects.impl.Utility;
import com.wouter.dndbattle.utils.Utilities;

/**
 *
 * @author wverl
 */
class UtilityPanel extends javax.swing.JPanel {

    private static final Utilities UTILITIES = Utilities.getInstance();

    private final Utility utility;
    private final UtilitiesPanel utilitiesPanel;

    UtilityPanel(Utility utility, UtilitiesPanel utilitiesPanel) {
        this.utility = utility;
        this.utilitiesPanel = utilitiesPanel;
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
        java.awt.GridBagConstraints gridBagConstraints;

        lName = new javax.swing.JLabel();
        lNameValue = new javax.swing.JLabel();
        bRename = new javax.swing.JButton();
        bDelete = new javax.swing.JButton();
        lDescription = new javax.swing.JLabel();
        tfDescription = new javax.swing.JTextField();
        lWeight = new javax.swing.JLabel();
        sWeight = new javax.swing.JSpinner();
        lValue = new javax.swing.JLabel();
        tfValue = new javax.swing.JTextField();
        lNotes = new javax.swing.JLabel();
        spNotes = new javax.swing.JScrollPane();
        taNotes = new javax.swing.JTextArea();

        setLayout(new java.awt.GridBagLayout());

        lName.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lName, gridBagConstraints);

        lNameValue.setText(utility.getName());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lNameValue, gridBagConstraints);

        bRename.setText("Rename");
        bRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRenameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(bRename, gridBagConstraints);

        bDelete.setText("Delete");
        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(bDelete, gridBagConstraints);

        lDescription.setText("Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lDescription, gridBagConstraints);

        tfDescription.setText(utility.getDescription());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(tfDescription, gridBagConstraints);

        lWeight.setText("Weight");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lWeight, gridBagConstraints);

        sWeight.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 1.0f));
        sWeight.setValue(utility.getWeight());
        sWeight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sWeightStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(sWeight, gridBagConstraints);

        lValue.setText("Value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lValue, gridBagConstraints);

        tfValue.setText(utility.getValue());
        tfValue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfValueFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(tfValue, gridBagConstraints);

        lNotes.setText("Notes");
        lNotes.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        add(lNotes, gridBagConstraints);

        taNotes.setColumns(20);
        taNotes.setRows(5);
        taNotes.setText(utility.getNotes());
        spNotes.setViewportView(taNotes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(spNotes, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRenameActionPerformed
        Object input = JOptionPane.showInputDialog(this, "Please enter the new name of the armor", "New name", JOptionPane.QUESTION_MESSAGE, null, null, utility.getName());
        if (input != null) {
            String inputStr = input.toString();
            if (!inputStr.isEmpty() && !inputStr.equals(utility.getName())) {
                Utility newUtility = new Utility(utility);
                newUtility.setName(inputStr);
                if (UTILITIES.add(newUtility)) {
                    UTILITIES.remove(utility);
                    utilitiesPanel.update();
                }
            }
        }
    }//GEN-LAST:event_bRenameActionPerformed

    private void bDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + utility.getName() + "?\n\nThis cannot be undone.", "Please confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            UTILITIES.remove(utility);
            utilitiesPanel.update();
        }
    }//GEN-LAST:event_bDeleteActionPerformed

    private void sWeightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sWeightStateChanged
        utility.setWeight((float) sWeight.getValue());
        saveUtility(false);
    }//GEN-LAST:event_sWeightStateChanged

    private void tfValueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfValueFocusLost
        utility.setValue(tfValue.getText());
        saveUtility(false);
    }//GEN-LAST:event_tfValueFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDelete;
    private javax.swing.JButton bRename;
    private javax.swing.JLabel lDescription;
    private javax.swing.JLabel lName;
    private javax.swing.JLabel lNameValue;
    private javax.swing.JLabel lNotes;
    private javax.swing.JLabel lValue;
    private javax.swing.JLabel lWeight;
    private javax.swing.JSpinner sWeight;
    private javax.swing.JScrollPane spNotes;
    private javax.swing.JTextArea taNotes;
    private javax.swing.JTextField tfDescription;
    private javax.swing.JTextField tfValue;
    // End of variables declaration//GEN-END:variables

    private void saveUtility(boolean refresh) {
        UTILITIES.update(utility);
        if (refresh) {
            utilitiesPanel.update();
        }
    }
}
