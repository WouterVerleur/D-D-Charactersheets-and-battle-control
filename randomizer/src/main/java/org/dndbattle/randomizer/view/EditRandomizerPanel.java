/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.randomizer.view;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.wouter.randomizer.randomizers.RandomItemsList;
import com.wouter.randomizer.randomizers.Randomizer;
import com.wouter.randomizer.randomizers.TimedRandomizer;

/**
 *
 * @author wverl
 */
public class EditRandomizerPanel extends javax.swing.JPanel {

  private final Randomizer randomizer;

  public EditRandomizerPanel(Randomizer randomizer) {
    this.randomizer = randomizer;
    initComponents();
    refreshListsList();
  }

  public Randomizer getRandomizer() {
    return randomizer;
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    lName = new javax.swing.JLabel();
    lMinTime = new javax.swing.JLabel();
    sMinTime = new javax.swing.JSpinner();
    lMaxTime = new javax.swing.JLabel();
    sMaxTime = new javax.swing.JSpinner();
    spLists = new javax.swing.JScrollPane();
    lLists = new javax.swing.JList<>();
    spListItems = new javax.swing.JScrollPane();
    lListItems = new javax.swing.JList<>();
    bEditList = new javax.swing.JButton();
    bAddList = new javax.swing.JButton();
    bDeleteList = new javax.swing.JButton();
    bEditListItem = new javax.swing.JButton();
    bAddListItem = new javax.swing.JButton();
    bDeleteListItem = new javax.swing.JButton();

    setLayout(new java.awt.GridBagLayout());

    lName.setText(randomizer.getName());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 8;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
    add(lName, gridBagConstraints);

    lMinTime.setText("Timer min");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    add(lMinTime, gridBagConstraints);

    sMinTime.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
    sMinTime.setEnabled(randomizer instanceof TimedRandomizer);
    sMinTime.setValue((randomizer instanceof TimedRandomizer)?((TimedRandomizer)randomizer).getMinTime():0);
    sMinTime.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        sMinTimeStateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    add(sMinTime, gridBagConstraints);

    lMaxTime.setText("Timer max");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    add(lMaxTime, gridBagConstraints);

    sMaxTime.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
    sMaxTime.setEnabled(randomizer instanceof TimedRandomizer);
    sMaxTime.setValue((randomizer instanceof TimedRandomizer)?((TimedRandomizer)randomizer).getMaxTime():0);
    sMaxTime.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        sMaxTimeStateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
    add(sMaxTime, gridBagConstraints);

    lLists.setModel(new DefaultListModel());
    lLists.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        lListsValueChanged(evt);
      }
    });
    spLists.setViewportView(lLists);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    add(spLists, gridBagConstraints);

    lListItems.setModel(new DefaultListModel());
    lListItems.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        lListItemsValueChanged(evt);
      }
    });
    spListItems.setViewportView(lListItems);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
    add(spListItems, gridBagConstraints);

    bEditList.setText("Edit");
    bEditList.setEnabled(false);
    bEditList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bEditListActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.weightx = 0.5;
    add(bEditList, gridBagConstraints);

    bAddList.setText("+");
    bAddList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bAddListActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    add(bAddList, gridBagConstraints);

    bDeleteList.setText("-");
    bDeleteList.setEnabled(false);
    bDeleteList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bDeleteListActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
    add(bDeleteList, gridBagConstraints);

    bEditListItem.setText("Edit");
    bEditListItem.setEnabled(false);
    bEditListItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bEditListItemActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.weightx = 0.5;
    add(bEditListItem, gridBagConstraints);

    bAddListItem.setText("+");
    bAddListItem.setEnabled(false);
    bAddListItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bAddListItemActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    add(bAddListItem, gridBagConstraints);

    bDeleteListItem.setText("-");
    bDeleteListItem.setEnabled(false);
    bDeleteListItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bDeleteListItemActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 7;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    add(bDeleteListItem, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

  private void bAddListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddListActionPerformed
    String name = JOptionPane.showInputDialog(this, "Name of the list", "List name", JOptionPane.QUESTION_MESSAGE);
    if (name != null && !name.isEmpty()) {
      randomizer.addItemList(new RandomItemsList<>(name));
      refreshListsList();
    }
  }//GEN-LAST:event_bAddListActionPerformed

  private void bAddListItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddListItemActionPerformed
    String name = JOptionPane.showInputDialog(this, "Name of the item", "Item name", JOptionPane.QUESTION_MESSAGE);
    if (name != null && !name.isEmpty()) {
      lLists.getSelectedValue().addItem(name);
      refreshItemsList();
    }
  }//GEN-LAST:event_bAddListItemActionPerformed

  private void bDeleteListItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteListItemActionPerformed
    lLists.getSelectedValue().removeItem(lListItems.getSelectedValue());
    refreshItemsList();
  }//GEN-LAST:event_bDeleteListItemActionPerformed

  private void bDeleteListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteListActionPerformed
    randomizer.removeItemList(lLists.getSelectedValue());
    refreshListsList();
    refreshItemsList();
  }//GEN-LAST:event_bDeleteListActionPerformed

  private void sMinTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sMinTimeStateChanged
    if (randomizer instanceof TimedRandomizer) {
      ((TimedRandomizer) randomizer).setMinTime((int) sMinTime.getValue());
    }
  }//GEN-LAST:event_sMinTimeStateChanged

  private void sMaxTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sMaxTimeStateChanged
    if (randomizer instanceof TimedRandomizer) {
      ((TimedRandomizer) randomizer).setMaxTime((int) sMaxTime.getValue());
    }
  }//GEN-LAST:event_sMaxTimeStateChanged

  private void lListsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lListsValueChanged
    if (!evt.getValueIsAdjusting()) {
      refreshItemsList();
    }
  }//GEN-LAST:event_lListsValueChanged

  private void lListItemsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lListItemsValueChanged
    if (!evt.getValueIsAdjusting()) {
      bDeleteListItem.setEnabled(lListItems.getSelectedValue() != null);
      bEditListItem.setEnabled(lListItems.getSelectedValue() != null);
    }
  }//GEN-LAST:event_lListItemsValueChanged

  private void bEditListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditListActionPerformed
    RandomItemsList<String> selection = lLists.getSelectedValue();
    String name = JOptionPane.showInputDialog(this, "What should the name be?", "Enter name", JOptionPane.QUESTION_MESSAGE, null, null, selection.getName()).toString();
    if (name != null && !name.isEmpty()) {
      selection.setName(name);
      randomizer.resortList();
      refreshListsList();
    }
  }//GEN-LAST:event_bEditListActionPerformed

  private void bEditListItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditListItemActionPerformed
    String selection = lListItems.getSelectedValue();
    String name = JOptionPane.showInputDialog(this, "What should the name be?", "Enter name", JOptionPane.QUESTION_MESSAGE, null, null, selection).toString();
    if (name != null && !name.isEmpty()) {
      lLists.getSelectedValue().removeItem(selection);
      lLists.getSelectedValue().addItem(name);
      refreshItemsList();
    }
  }//GEN-LAST:event_bEditListItemActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton bAddList;
  private javax.swing.JButton bAddListItem;
  private javax.swing.JButton bDeleteList;
  private javax.swing.JButton bDeleteListItem;
  private javax.swing.JButton bEditList;
  private javax.swing.JButton bEditListItem;
  private javax.swing.JList<String> lListItems;
  private javax.swing.JList<RandomItemsList<String>> lLists;
  private javax.swing.JLabel lMaxTime;
  private javax.swing.JLabel lMinTime;
  private javax.swing.JLabel lName;
  private javax.swing.JSpinner sMaxTime;
  private javax.swing.JSpinner sMinTime;
  private javax.swing.JScrollPane spListItems;
  private javax.swing.JScrollPane spLists;
  // End of variables declaration//GEN-END:variables

  private void refreshListsList() {
    RandomItemsList<String> selection = lLists.getSelectedValue();
    DefaultListModel<RandomItemsList<String>> model = (DefaultListModel<RandomItemsList<String>>) lLists.getModel();
    model.removeAllElements();
    randomizer.getItemLists().forEach((itemList) -> {
      model.addElement(itemList);
    });
    lLists.setSelectedValue(selection, true);
  }

  private void refreshItemsList() {
    String selection = lListItems.getSelectedValue();
    DefaultListModel<String> model = (DefaultListModel<String>) lListItems.getModel();
    model.removeAllElements();
    final RandomItemsList<String> selectedValue = lLists.getSelectedValue();
    if (selectedValue != null) {
      selectedValue.getItems().forEach((item) -> {
        model.addElement(item);
      });
      lListItems.setSelectedValue(selection, true);
    }
    bEditList.setEnabled(selectedValue != null);
    bDeleteList.setEnabled(selectedValue != null);
    bAddListItem.setEnabled(selectedValue != null);
  }
}
