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
package org.dndbattle.view.host.character.equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.dndbattle.objects.IArmor;
import org.dndbattle.objects.IEquipment;
import org.dndbattle.objects.IInventoryItem;
import org.dndbattle.objects.enums.AbilityType;
import org.dndbattle.objects.enums.Equipment;
import org.dndbattle.objects.impl.AbstractCharacter;
import org.dndbattle.objects.impl.AbstractInventoryItem;
import org.dndbattle.utils.Armors;
import org.dndbattle.utils.Characters;
import org.dndbattle.utils.Settings;
import org.dndbattle.utils.Utilities;
import org.dndbattle.utils.Weapons;
import org.dndbattle.view.IUpdateablePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wverl
 */
public class EquipmentPanel extends javax.swing.JPanel implements IUpdateablePanel {

    private static final Logger log = LoggerFactory.getLogger(EquipmentPanel.class);

    private static final Armors ARMORS = Armors.getInstance();
    private static final Characters CHARACTERS = Characters.getInstance();
    private static final Settings SETTINGS = Settings.getInstance();
    private static final Utilities UTILITIES = Utilities.getInstance();
    private static final Weapons WEAPONS = Weapons.getInstance();

    private static final IInventoryItem SPACER = new AbstractInventoryItemImpl("-----");

    private DefaultListModel<IInventoryItem> model;

    private final Map<IInventoryItem, InternalEquipmentPanel> itemPanelMap = new HashMap<>();
    private float carryingCapacity;
    private float inventoryWeight;

    private final AbstractCharacter character;

    public EquipmentPanel(AbstractCharacter character) {
        this.character = character;
        calculateCarryingCapacity();
        calculateInventoryWeight();
        initComponents();
        for (IEquipment equipment : this.character.getInventoryItems()) {
            if (equipment instanceof Equipment) {
                log.debug("Adding equipment [{}]", equipment);
                addPanel((Equipment) equipment);
            }
        }
    }

    @Override
    public void update() {
        calculateCarryingCapacity();
        updateLabels();
    }

    public final void calculateInventoryWeight() {
        inventoryWeight = 0;
        character.getInventoryItems().forEach((inventoryItem) -> {
            inventoryWeight += inventoryItem.getTotalWeight();
        });
    }

    private void calculateCarryingCapacity() {
        carryingCapacity = character.getAbilityScore(AbilityType.STR) * SETTINGS.getProperty(Settings.CARRYING_CAPACITY_MULTIPLIER, 15);
        switch (character.getSize()) {
            case TINY:
                carryingCapacity /= 2;
                break;
            case LARGE:
                carryingCapacity *= 2;
                break;
            case HUGE:
                carryingCapacity *= 4;
                break;
            case GARGANTUAN:
                carryingCapacity *= 8;
                break;
            default:
                break;
        }
        if (character.isPowerfulBuild()) {
            carryingCapacity *= 2;
        }
    }

    private void addItem(IInventoryItem item) {
        if (itemPanelMap.containsKey(item)) {
            itemPanelMap.get(item).increaseAmount();
        } else {
            Equipment equipment = new Equipment();
            equipment.setInventoryItem(item);
            equipment.setAmount(1);
            character.addInventoryItem(equipment);
            addPanel(equipment);
            calculateInventoryWeight();
            save();
        }
    }

    private void addPanel(Equipment equipment) {
        InternalEquipmentPanel internalEquipmentPanel = new InternalEquipmentPanel(equipment, this);
        itemPanelMap.put(equipment.getInventoryItem(), internalEquipmentPanel);
        pEquipment.add(internalEquipmentPanel);
    }

    private void updateLabels() {
        lCarryingCapacityValue.setText("" + carryingCapacity);
        lEquipmentTotalValue.setText("" + inventoryWeight);
        lEquipmentTotalValue.setText("" + (carryingCapacity - inventoryWeight));
    }

    public void save() {
        updateLabels();
        CHARACTERS.update(character);
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

        spItem = new javax.swing.JScrollPane();
        lItems = new javax.swing.JList<>();
        bAdd = new javax.swing.JButton();
        spEquipment = new javax.swing.JScrollPane();
        pEquipment = new javax.swing.JPanel();
        cbPowerfullBuild = new javax.swing.JCheckBox();
        lCarryingCapacity = new javax.swing.JLabel();
        lCarryingCapacityValue = new javax.swing.JLabel();
        lEquipmentTotal = new javax.swing.JLabel();
        lEquipmentTotalValue = new javax.swing.JLabel();
        lOpenCapacity = new javax.swing.JLabel();
        lOpenCapacityValue = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        lItems.setModel(getListModel());
        spItem.setViewportView(lItems);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.weighty = 1.0;
        add(spItem, gridBagConstraints);

        bAdd.setText(">");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(bAdd, gridBagConstraints);

        pEquipment.setLayout(new java.awt.GridLayout(0, 1, 5, 5));
        spEquipment.setViewportView(pEquipment);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(spEquipment, gridBagConstraints);

        cbPowerfullBuild.setSelected(character.isPowerfulBuild());
        cbPowerfullBuild.setText("Powerful build");
        cbPowerfullBuild.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbPowerfullBuildStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(cbPowerfullBuild, gridBagConstraints);

        lCarryingCapacity.setText("Carrying capacity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lCarryingCapacity, gridBagConstraints);

        lCarryingCapacityValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lCarryingCapacityValue.setText(""+carryingCapacity);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lCarryingCapacityValue, gridBagConstraints);

        lEquipmentTotal.setText("Equipment total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lEquipmentTotal, gridBagConstraints);

        lEquipmentTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lEquipmentTotalValue.setText(""+inventoryWeight);
        lEquipmentTotalValue.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lEquipmentTotalValue, gridBagConstraints);

        lOpenCapacity.setText("Open capacity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lOpenCapacity, gridBagConstraints);

        lOpenCapacityValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lOpenCapacityValue.setText("" + (carryingCapacity - inventoryWeight));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(lOpenCapacityValue, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cbPowerfullBuildStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbPowerfullBuildStateChanged
        character.setPowerfulBuild(cbPowerfullBuild.isSelected());
        calculateCarryingCapacity();
        save();
    }//GEN-LAST:event_cbPowerfullBuildStateChanged

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        IInventoryItem selection = lItems.getSelectedValue();
        log.debug("Adding selection [{}]", selection);
        if (selection != null && !(selection instanceof AbstractInventoryItemImpl)) {
            addItem(selection);
        }
    }//GEN-LAST:event_bAddActionPerformed

    public synchronized ListModel<IInventoryItem> getListModel() {
        if (model == null) {
            List<IInventoryItem> items = new ArrayList<>();
            for (IArmor armor : ARMORS.getAll()) {
                if (armor.getArmorType().isEquipment()) {
                    items.add(armor);
                }
            }
            items.add(SPACER);
            items.addAll(WEAPONS.getAll());
            items.add(SPACER);
            items.addAll(UTILITIES.getAll());

            model = new DefaultListModel<>();
            items.forEach((item) -> {
                model.addElement(item);
            });
        }
        return model;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JCheckBox cbPowerfullBuild;
    private javax.swing.JLabel lCarryingCapacity;
    private javax.swing.JLabel lCarryingCapacityValue;
    private javax.swing.JLabel lEquipmentTotal;
    private javax.swing.JLabel lEquipmentTotalValue;
    private javax.swing.JList<IInventoryItem> lItems;
    private javax.swing.JLabel lOpenCapacity;
    private javax.swing.JLabel lOpenCapacityValue;
    private javax.swing.JPanel pEquipment;
    private javax.swing.JScrollPane spEquipment;
    private javax.swing.JScrollPane spItem;
    // End of variables declaration//GEN-END:variables

    public void delete(Equipment equipment) {
        character.removeInventoryItem(equipment);
        InternalEquipmentPanel equipmentPanel = itemPanelMap.remove(equipment.getInventoryItem());
        pEquipment.remove(equipmentPanel);
        calculateInventoryWeight();
        save();
    }

    private static class AbstractInventoryItemImpl extends AbstractInventoryItem {

        public AbstractInventoryItemImpl(String name) {
            super(name);
        }

        @Override
        public String getDescription() {
            return "-----";
        }
    }
}
