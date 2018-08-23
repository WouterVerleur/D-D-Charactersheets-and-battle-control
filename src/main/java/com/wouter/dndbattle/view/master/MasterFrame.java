/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.view.master;

import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.wouter.dndbattle.core.IMaster;
import com.wouter.dndbattle.core.impl.Master;
import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.ICombatant;
import com.wouter.dndbattle.objects.impl.Combatant;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.comboboxes.ClassComboBox;
import com.wouter.dndbattle.view.slave.character.SlaveCharacterPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.wouter.dndbattle.utils.Settings.MASTER_LOCATION_X;
import static com.wouter.dndbattle.utils.Settings.MASTER_LOCATION_Y;
import static com.wouter.dndbattle.utils.Settings.MASTER_SIZE_HEIGHT;
import static com.wouter.dndbattle.utils.Settings.MASTER_SIZE_STATE;
import static com.wouter.dndbattle.utils.Settings.MASTER_SIZE_WIDTH;
import static com.wouter.dndbattle.utils.Settings.MASTER_TITLE;

/**
 *
 * @author Wouter
 */
public class MasterFrame extends javax.swing.JFrame {

    private static final Logger log = LoggerFactory.getLogger(MasterFrame.class);
    private static final Settings SETTINGS = Settings.getInstance();

    private final Master master;

    /**
     * Creates new form MasterFrame
     */
    public MasterFrame() {
        master = new Master(this);
        initComponents();
        setLocation(SETTINGS.getProperty(MASTER_LOCATION_X, Integer.MIN_VALUE), SETTINGS.getProperty(MASTER_LOCATION_Y, Integer.MIN_VALUE));
        setSize(SETTINGS.getProperty(MASTER_SIZE_WIDTH, getPreferredSize().width), SETTINGS.getProperty(MASTER_SIZE_HEIGHT, getPreferredSize().height));
        setExtendedState(SETTINGS.getProperty(MASTER_SIZE_STATE, 0));
        Rectangle screenBounds = getScreenBounds();
        final Rectangle bounds = this.getBounds();
        if (!screenBounds.contains(bounds)) {
            log.debug("Frame brounds [{}] are not within screen bounds [{}]", bounds, screenBounds);
            setLocationRelativeTo(null);
        }
    }

    private Rectangle getScreenBounds() {
        Rectangle bounds = new Rectangle(0, 0, 0, 0);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice lstGDs[] = ge.getScreenDevices();
        for (GraphicsDevice gd : lstGDs) {
            bounds.add(gd.getDefaultConfiguration().getBounds());
        }
        return bounds;
    }

    public IMaster getMaster() {
        return master;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpBase = new javax.swing.JTabbedPane();
        tpMain = new javax.swing.JTabbedPane();
        pMain = new javax.swing.JPanel();
        bNewBattle = new javax.swing.JButton();
        bAddCombatant = new javax.swing.JButton();
        spCombatants = new javax.swing.JScrollPane();
        pCombatants = new javax.swing.JPanel();
        bNext = new javax.swing.JButton();
        tpCharacters = new javax.swing.JTabbedPane();
        pAudio = new com.wouter.dndbattle.view.master.AudioPanel();
        spDice = new javax.swing.JScrollPane();
        dicePanel = new com.wouter.dndbattle.view.master.DicePanel();
        spSettings = new javax.swing.JScrollPane();
        settingsPanel = new com.wouter.dndbattle.view.master.SettingsPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(SETTINGS.getProperty(MASTER_TITLE, "Master").toString());
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        tpBase.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tpBase.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tpBaseStateChanged(evt);
            }
        });

        bNewBattle.setText("New Battle");
        bNewBattle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewBattleActionPerformed(evt);
            }
        });

        bAddCombatant.setText("Add combatant");
        bAddCombatant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddCombatantActionPerformed(evt);
            }
        });

        spCombatants.setBorder(null);

        pCombatants.setLayout(new javax.swing.BoxLayout(pCombatants, javax.swing.BoxLayout.Y_AXIS));
        spCombatants.setViewportView(pCombatants);

        bNext.setText("Next round");
        bNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pMainLayout = new javax.swing.GroupLayout(pMain);
        pMain.setLayout(pMainLayout);
        pMainLayout.setHorizontalGroup(
            pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddCombatant)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bNext, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bNewBattle)
                .addContainerGap())
            .addComponent(spCombatants)
        );
        pMainLayout.setVerticalGroup(
            pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bNewBattle)
                    .addComponent(bAddCombatant)
                    .addComponent(bNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spCombatants, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );

        tpMain.addTab("Battle", pMain);

        tpBase.addTab("Main", tpMain);

        tpCharacters.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tpCharactersStateChanged(evt);
            }
        });

        addTabsToTpCharacter();

        tpBase.addTab("Characters", tpCharacters);
        tpBase.addTab("Audio", pAudio);

        spDice.setViewportView(dicePanel);

        tpBase.addTab("Dice", spDice);

        spSettings.setViewportView(settingsPanel);

        tpBase.addTab("Settings", spSettings);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpBase)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpBase)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        if (getExtendedState() == NORMAL) {
            SETTINGS.setProperty(MASTER_LOCATION_X, getLocation().x);
            SETTINGS.setProperty(MASTER_LOCATION_Y, getLocation().y);
        }
    }//GEN-LAST:event_formComponentMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (getExtendedState() == NORMAL) {
            SETTINGS.setProperty(MASTER_SIZE_HEIGHT, getHeight());
            SETTINGS.setProperty(MASTER_SIZE_WIDTH, getWidth());
        }
        if (getExtendedState() != ICONIFIED) {
            SETTINGS.setProperty(MASTER_SIZE_STATE, getExtendedState());
        }
    }//GEN-LAST:event_formComponentResized

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        master.shutdown();
    }//GEN-LAST:event_formWindowClosed

    private void tpBaseStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tpBaseStateChanged
        if (tpBase.getSelectedComponent().equals(tpCharacters)) {
            Component selectedComponent = tpCharacters.getSelectedComponent();
            if (selectedComponent != null) {
                ((MasterCharactersPanel) selectedComponent).updateList();
            }
        }
    }//GEN-LAST:event_tpBaseStateChanged

    private void tpCharactersStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tpCharactersStateChanged
        Component selectedComponent = tpCharacters.getSelectedComponent();
        if (selectedComponent != null) {
            ((MasterCharactersPanel) selectedComponent).updateList();
        }
    }//GEN-LAST:event_tpCharactersStateChanged

    private void bNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextActionPerformed
        master.nextTurn();
    }//GEN-LAST:event_bNextActionPerformed

    private void bAddCombatantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddCombatantActionPerformed
        CombatantSelectionFrame frame = new CombatantSelectionFrame(master);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_bAddCombatantActionPerformed

    private void bNewBattleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewBattleActionPerformed
        switch (JOptionPane.showConfirmDialog(this,
                "Do you want to carry over the currect combatants with all settings and reroll for initiative?\n"
                + "If you don't want to start a new battle please select cancel.", "Please confirm.", JOptionPane.YES_NO_CANCEL_OPTION)) {
            case JOptionPane.YES_OPTION:
                List<ICombatant> combatants = master.getCombatants();
                for (Iterator<ICombatant> iterator = combatants.iterator(); iterator.hasNext();) {
                    ICombatant combatant = iterator.next();
                    if (combatant.isDead()) {
                        iterator.remove();
                        continue;
                    }
                    int initiative = Integer.MIN_VALUE;
                    while (initiative == Integer.MIN_VALUE) {
                        try {
                            String input = JOptionPane.showInputDialog(this, "Please enter new initiative for " + combatant, "New initiative", JOptionPane.QUESTION_MESSAGE);
                            initiative = Integer.parseInt(input);
                            ((Combatant) combatant).setInitiative(initiative);
                        } catch (NumberFormatException e) {
                            log.error("User input could not be parsed", e);
                        }
                    }
                }
                master.setCombatants(combatants);
                break;
            case JOptionPane.NO_OPTION:
                master.startNewBattle();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_bNewBattleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddCombatant;
    private javax.swing.JButton bNewBattle;
    private javax.swing.JButton bNext;
    private com.wouter.dndbattle.view.master.DicePanel dicePanel;
    private com.wouter.dndbattle.view.master.AudioPanel pAudio;
    private javax.swing.JPanel pCombatants;
    private javax.swing.JPanel pMain;
    private com.wouter.dndbattle.view.master.SettingsPanel settingsPanel;
    private javax.swing.JScrollPane spCombatants;
    private javax.swing.JScrollPane spDice;
    private javax.swing.JScrollPane spSettings;
    private javax.swing.JTabbedPane tpBase;
    private javax.swing.JTabbedPane tpCharacters;
    private javax.swing.JTabbedPane tpMain;
    // End of variables declaration//GEN-END:variables

    public void refreshBattle(final List<ICombatant> combatants, int activeIndex) {
        pCombatants.removeAll();
        while (tpMain.getTabCount() > 1) {
            tpMain.remove(1);
        }
        List<ICharacter> characters = new ArrayList<>();
        log.debug("Removed all from view to leave a total of [{}] components in the view", pCombatants.getComponents().length);
        for (int i = activeIndex; i < combatants.size(); i++) {
            final Combatant combatant = (Combatant) combatants.get(i);
            addCombatant(combatant);
            characters.add(combatant.getCharacter());
        }
        for (int i = 0; i < activeIndex; i++) {
            final Combatant combatant = (Combatant) combatants.get(i);
            addCombatant(combatant);
            characters.add(combatant.getCharacter());
        }
        Collections.sort(characters);
        ICharacter previousCharacter = null;
        for (ICharacter character : characters) {
            if (!character.equals(previousCharacter)) {
                tpMain.add(new SlaveCharacterPanel(character));
                previousCharacter = character;
            }
        }
        log.debug("Added all combatants to get a new total of [{}] components in the view", pCombatants.getComponents().length);
        if (pCombatants.getComponents().length == 0) {
            pCombatants.add(new JPanel());
        }
        pCombatants.revalidate();
    }

    private void addCombatant(Combatant combatant) {
        log.debug("Adding {} of class {}", combatant, combatant.getClass());
        pCombatants.add(new MasterCombatantPanel(master, combatant));
    }

    private void addTabsToTpCharacter() {
        for (Class clazz : ClassComboBox.getAllClasses()) {
            tpCharacters.addTab(clazz.getSimpleName(), new MasterCharactersPanel(clazz));
        }
    }
}
