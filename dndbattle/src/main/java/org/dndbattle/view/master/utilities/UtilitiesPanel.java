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
package org.dndbattle.view.master.utilities;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import org.dndbattle.objects.IUtility;
import org.dndbattle.objects.impl.Utility;
import org.dndbattle.utils.Settings;
import org.dndbattle.utils.Utilities;
import org.dndbattle.view.IUpdateablePanel;

/**
 *
 * @author Wouter
 */
public class UtilitiesPanel extends javax.swing.JPanel implements IUpdateablePanel {

    private static final Settings SETTINGS = Settings.getInstance();
    private static final Utilities UTILITIES = Utilities.getInstance();
    private static final String DIVIDER_LOCATION_PROPERTY = "gui.master.presetpanel.utilities.deviderlocation";

    public UtilitiesPanel() {
        initComponents();
        update();
    }

    @Override
    public final void update() {
        Utility selection = (Utility) lUtilities.getSelectedValue();

        DefaultListModel<Utility> model = (DefaultListModel<Utility>) lUtilities.getModel();
        model.removeAllElements();

        for (IUtility utility : UTILITIES.getAll()) {
            if (utility instanceof Utility) {
                model.addElement((Utility) utility);
            }
        }
        if (selection != null) {
            lUtilities.setSelectedValue(selection, true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        spUtilities = new javax.swing.JSplitPane();
        pUtilityList = new javax.swing.JPanel();
        spUtilitiesList = new javax.swing.JScrollPane();
        lUtilities = new javax.swing.JList<Utility>();
        bNew = new javax.swing.JButton();
        spEdit = new javax.swing.JScrollPane();

        spUtilities.setDividerLocation(SETTINGS.getProperty(DIVIDER_LOCATION_PROPERTY, 150));
        spUtilities.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spUtilitiesPropertyChange(evt);
            }
        });

        pUtilityList.setLayout(new java.awt.GridBagLayout());

        lUtilities.setModel(new DefaultListModel<Utility>());
        lUtilities.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lUtilities.setToolTipText("");
        lUtilities.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lUtilitiesValueChanged(evt);
            }
        });
        spUtilitiesList.setViewportView(lUtilities);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pUtilityList.add(spUtilitiesList, gridBagConstraints);

        bNew.setText("New");
        bNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pUtilityList.add(bNew, gridBagConstraints);

        spUtilities.setLeftComponent(pUtilityList);
        spUtilities.setRightComponent(spEdit);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spUtilities, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spUtilities, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lUtilitiesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lUtilitiesValueChanged
        Utility selection = lUtilities.getSelectedValue();
        if (!evt.getValueIsAdjusting() && selection != null) {
            spEdit.setViewportView(new UtilityPanel(selection, this));
        }
    }//GEN-LAST:event_lUtilitiesValueChanged

    private void bNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewActionPerformed
        String input = JOptionPane.showInputDialog(this, "Please enter a name for the utility.", "Please enter name", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            Utility utility = new Utility();
            utility.setName(input);
            if (UTILITIES.add(utility)) {
                update();
                lUtilities.setSelectedValue(utility, true);
            }
        }
    }//GEN-LAST:event_bNewActionPerformed

    private void spUtilitiesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spUtilitiesPropertyChange
        if (evt.getPropertyName().equalsIgnoreCase(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
            SETTINGS.setProperty(DIVIDER_LOCATION_PROPERTY, spUtilities.getDividerLocation());
        }
    }//GEN-LAST:event_spUtilitiesPropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bNew;
    private javax.swing.JList<Utility> lUtilities;
    private javax.swing.JPanel pUtilityList;
    private javax.swing.JScrollPane spEdit;
    private javax.swing.JSplitPane spUtilities;
    private javax.swing.JScrollPane spUtilitiesList;
    // End of variables declaration//GEN-END:variables
}
