/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.view.master;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;
import com.wouter.dndbattle.utils.Characters;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.character.CharacterPanel;
import java.awt.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class MasterCharactersPanel extends javax.swing.JPanel {

    private static final Logger log = LoggerFactory.getLogger(MasterCharactersPanel.class);

    private static final Settings SETTINGS = Settings.getInstance();
    private static final String DIVIDER_LOCATION_PROPERTY_FORMAT = "gui.master.presetpanel.%s.deviderlocation";
    private boolean addingPresets;

    private final Class<? extends AbstractCharacter> characterClass;
    DefaultListModel<AbstractCharacter> listModel = new DefaultListModel<>();
    private final String dividerLocationProperty;

    public MasterCharactersPanel(Class<? extends AbstractCharacter> characterClass) {
        this.characterClass = characterClass;
        dividerLocationProperty = String.format(DIVIDER_LOCATION_PROPERTY_FORMAT, getClassname());
        initComponents();
    }

    private AbstractCharacter getSelectedCharacter() {
        int selection = lCharacters.getSelectedIndex();
        if (selection >= 0) {
            return listModel.getElementAt(selection);
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spPreset = new javax.swing.JSplitPane();
        pCharacters = new javax.swing.JPanel();
        bNew = new javax.swing.JButton();
        cpCharacters = new javax.swing.JScrollPane();
        lCharacters = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();

        spPreset.setDividerLocation(getDividerLocation());
        spPreset.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spPresetPropertyChange(evt);
            }
        });

        bNew.setText("New");
        bNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewActionPerformed(evt);
            }
        });

        lCharacters.setModel(listModel);
        lCharacters.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lCharactersValueChanged(evt);
            }
        });
        cpCharacters.setViewportView(lCharacters);

        javax.swing.GroupLayout pCharactersLayout = new javax.swing.GroupLayout(pCharacters);
        pCharacters.setLayout(pCharactersLayout);
        pCharactersLayout.setHorizontalGroup(
            pCharactersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cpCharacters, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pCharactersLayout.setVerticalGroup(
            pCharactersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCharactersLayout.createSequentialGroup()
                .addComponent(cpCharacters)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bNew))
        );

        spPreset.setLeftComponent(pCharacters);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 269, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );

        spPreset.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spPreset, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spPreset, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewActionPerformed
        String inputValue = JOptionPane.showInputDialog(this, "", "Name", JOptionPane.QUESTION_MESSAGE);
        if (inputValue == null || inputValue.isEmpty()) {
            return;
        }
        try {
            AbstractCharacter newCharacter = characterClass.newInstance();
            newCharacter.setName(inputValue);
            if (!Characters.addCharacter(newCharacter)) {
                JOptionPane.showMessageDialog(this, "Unable to create character with name " + inputValue + " because it already exists!", "Character exists.", JOptionPane.WARNING_MESSAGE);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            log.error("Error while creating new character", ex);
        }
        updateList();
    }//GEN-LAST:event_bNewActionPerformed

    private void spPresetPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spPresetPropertyChange
        if (evt.getPropertyName().equalsIgnoreCase(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
            SETTINGS.setProperty(dividerLocationProperty, spPreset.getDividerLocation());
        }
    }//GEN-LAST:event_spPresetPropertyChange

    private void lCharactersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lCharactersValueChanged
        if (!addingPresets) {
            Component oldPanel = spPreset.getRightComponent();
            CharacterPanel panel = new CharacterPanel(getSelectedCharacter(), this);
            if (oldPanel instanceof CharacterPanel) {
                int currentTab = ((CharacterPanel) oldPanel).getCurrentTab();
                panel.setCurrentTab(currentTab);
            }
            spPreset.setRightComponent(panel);
            spPreset.setDividerLocation(getDividerLocation());
        }
    }//GEN-LAST:event_lCharactersValueChanged

    private List<ICharacter> getAllCharacters() {
        return Characters.getCharacters(characterClass);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bNew;
    private javax.swing.JScrollPane cpCharacters;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JList lCharacters;
    private javax.swing.JPanel pCharacters;
    private javax.swing.JSplitPane spPreset;
    // End of variables declaration//GEN-END:variables

    private String getClassname() {
        return characterClass.getSimpleName().toLowerCase();
    }

    public void updateList() {
        addingPresets = true;
        AbstractCharacter currectSelection = getSelectedCharacter();
        listModel.removeAllElements();
        getAllCharacters().forEach((character) -> {
            listModel.addElement((AbstractCharacter) character);
        });
        lCharacters.setSelectedIndex(listModel.indexOf(currectSelection));
        addingPresets = false;
    }

    private int getDividerLocation() {
        return SETTINGS.getProperty(dividerLocationProperty, -1);
    }
}
