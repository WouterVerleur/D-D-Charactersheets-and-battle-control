/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.view.master;

import static com.wouter.dndbattle.utils.Settings.MASTER_LOCATION_X;
import static com.wouter.dndbattle.utils.Settings.MASTER_LOCATION_Y;
import static com.wouter.dndbattle.utils.Settings.MASTER_SIZE_HEIGHT;
import static com.wouter.dndbattle.utils.Settings.MASTER_SIZE_STATE;
import static com.wouter.dndbattle.utils.Settings.MASTER_SIZE_WIDTH;
import static com.wouter.dndbattle.utils.Settings.MASTER_TITLE;

import java.awt.CardLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import com.wouter.dndbattle.core.IMaster;
import com.wouter.dndbattle.core.impl.Master;
import com.wouter.dndbattle.core.impl.MasterConnectionInfo;
import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.ICombatant;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;
import com.wouter.dndbattle.objects.impl.Combatant;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.comboboxes.ClassComboBox;
import com.wouter.dndbattle.view.slave.character.SlaveCharacterPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class MasterFrame extends javax.swing.JFrame {

    private static final Logger log = LoggerFactory.getLogger(MasterFrame.class);
    private static final Settings SETTINGS = Settings.getInstance();
    private static final int DEFAULT_REFRESH_TIME = 1000;

    private final Master master;

    private final Timer refreshTimer = new Timer(DEFAULT_REFRESH_TIME, (ActionEvent e) -> {
        refreshClientsTable();
    });

    public MasterFrame(Master master) {
        this.master = master;
        initComponents();
        spCombatants.getVerticalScrollBar().setUnitIncrement(20);
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

        pView = new javax.swing.JPanel();
        tpBattle = new javax.swing.JTabbedPane();
        pMain = new javax.swing.JPanel();
        bAddCombatant = new javax.swing.JButton();
        bNext = new javax.swing.JButton();
        bNewBattle = new javax.swing.JButton();
        spCombatants = new javax.swing.JScrollPane();
        pCombatants = new javax.swing.JPanel();
        pCharacters = new javax.swing.JPanel();
        pAudio = new com.wouter.dndbattle.view.master.AudioPanel();
        spDice = new javax.swing.JScrollPane();
        dicePanel = new com.wouter.dndbattle.view.master.DicePanel();
        spSettings = new javax.swing.JScrollPane();
        settingsPanel = new com.wouter.dndbattle.view.master.SettingsPanel();
        pClients = new javax.swing.JPanel();
        spClientsTable = new javax.swing.JScrollPane();
        tClients = new javax.swing.JTable();
        bKickClient = new javax.swing.JButton();
        tbRefresh = new javax.swing.JToggleButton();
        armorsPanel = new com.wouter.dndbattle.view.master.armor.ArmorsPanel();
        spellsPanel = new com.wouter.dndbattle.view.master.spells.SpellsPanel();
        weaponsPanel = new com.wouter.dndbattle.view.master.weapons.WeaponsPanel();
        pEncounterCalculator = new com.wouter.dndbattle.view.master.encounter.EncounterCalculator();
        mbMain = new javax.swing.JMenuBar();
        mBattle = new javax.swing.JMenu();
        miBattleView = new javax.swing.JMenuItem();
        sBattle = new javax.swing.JPopupMenu.Separator();
        miBattleNext = new javax.swing.JMenuItem();
        miBattleNew = new javax.swing.JMenuItem();
        miBattleAdd = new javax.swing.JMenuItem();
        mCharacters = new javax.swing.JMenu();
        mAttributes = new javax.swing.JMenu();
        miArmors = new javax.swing.JMenuItem();
        miSpells = new javax.swing.JMenuItem();
        miWeapons = new javax.swing.JMenuItem();
        mView = new javax.swing.JMenu();
        miEncounter = new javax.swing.JMenuItem();
        miDice = new javax.swing.JMenuItem();
        miAudio = new javax.swing.JMenuItem();
        miSettings = new javax.swing.JMenuItem();
        sView1 = new javax.swing.JPopupMenu.Separator();
        miClients = new javax.swing.JMenuItem();
        sView2 = new javax.swing.JPopupMenu.Separator();
        miShutdown = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(SETTINGS.getProperty(MASTER_TITLE, "Master"));
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

        pView.setLayout(new java.awt.CardLayout());

        bAddCombatant.setText("Add combatant");
        bAddCombatant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddCombatantActionPerformed(evt);
            }
        });

        bNext.setText("Next round");
        bNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNextActionPerformed(evt);
            }
        });

        bNewBattle.setText("New Battle");
        bNewBattle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewBattleActionPerformed(evt);
            }
        });

        spCombatants.setBorder(null);

        pCombatants.setLayout(new javax.swing.BoxLayout(pCombatants, javax.swing.BoxLayout.Y_AXIS));
        spCombatants.setViewportView(pCombatants);

        javax.swing.GroupLayout pMainLayout = new javax.swing.GroupLayout(pMain);
        pMain.setLayout(pMainLayout);
        pMainLayout.setHorizontalGroup(
            pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddCombatant)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bNext, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
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
                .addComponent(spCombatants, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
        );

        spCombatants.getAccessibleContext().setAccessibleName("");

        tpBattle.addTab("Battle", pMain);

        pView.add(tpBattle, "Battle");

        pCharacters.setLayout(new java.awt.CardLayout());
        pView.add(pCharacters, "Characters");
        pView.add(pAudio, "Audio");

        spDice.setViewportView(dicePanel);

        pView.add(spDice, "Dice");

        spSettings.setViewportView(settingsPanel);

        pView.add(spSettings, "Settings");

        tClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP", "Name", "Ping"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tClients.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tClients.getTableHeader().setReorderingAllowed(false);
        spClientsTable.setViewportView(tClients);

        bKickClient.setText("Kick");
        bKickClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKickClientActionPerformed(evt);
            }
        });

        tbRefresh.setSelected(refreshTimer.isRunning());
        tbRefresh.setText("Refresh");
        tbRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pClientsLayout = new javax.swing.GroupLayout(pClients);
        pClients.setLayout(pClientsLayout);
        pClientsLayout.setHorizontalGroup(
            pClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pClientsLayout.createSequentialGroup()
                .addComponent(spClientsTable, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tbRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bKickClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pClientsLayout.setVerticalGroup(
            pClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spClientsTable, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
            .addGroup(pClientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bKickClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbRefresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pView.add(pClients, "Clients");
        pClients.getAccessibleContext().setAccessibleName("Clients");

        pView.add(armorsPanel, "Armors");
        pView.add(spellsPanel, "Spells");
        pView.add(weaponsPanel, "Weapons");
        pView.add(pEncounterCalculator, "Encounter");

        mBattle.setText("Battle");

        miBattleView.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK));
        miBattleView.setText("View");
        miBattleView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miBattleViewActionPerformed(evt);
            }
        });
        mBattle.add(miBattleView);
        mBattle.add(sBattle);

        miBattleNext.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, java.awt.event.InputEvent.CTRL_MASK));
        miBattleNext.setText("Next round");
        miBattleNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miBattleNextActionPerformed(evt);
            }
        });
        mBattle.add(miBattleNext);

        miBattleNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        miBattleNew.setText("New battle");
        miBattleNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miBattleNewActionPerformed(evt);
            }
        });
        mBattle.add(miBattleNew);

        miBattleAdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        miBattleAdd.setText("Add combatant");
        miBattleAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miBattleAddActionPerformed(evt);
            }
        });
        mBattle.add(miBattleAdd);

        mbMain.add(mBattle);

        mCharacters.setText("Characters");
        mbMain.add(mCharacters);
        addItemsToMCharacters();

        mAttributes.setText("Attributes");

        miArmors.setText("Armors");
        miArmors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miArmorsActionPerformed(evt);
            }
        });
        mAttributes.add(miArmors);

        miSpells.setText("Spells");
        miSpells.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSpellsActionPerformed(evt);
            }
        });
        mAttributes.add(miSpells);

        miWeapons.setText("Weapons");
        miWeapons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miWeaponsActionPerformed(evt);
            }
        });
        mAttributes.add(miWeapons);

        mbMain.add(mAttributes);

        mView.setText("Other");

        miEncounter.setText("Encounter Calculator");
        miEncounter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEncounterActionPerformed(evt);
            }
        });
        mView.add(miEncounter);

        miDice.setText("Dice");
        miDice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDiceActionPerformed(evt);
            }
        });
        mView.add(miDice);

        miAudio.setText("Audio");
        miAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAudioActionPerformed(evt);
            }
        });
        mView.add(miAudio);

        miSettings.setText("Settings");
        miSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSettingsActionPerformed(evt);
            }
        });
        mView.add(miSettings);
        mView.add(sView1);

        miClients.setText("Clients");
        miClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miClientsActionPerformed(evt);
            }
        });
        mView.add(miClients);
        mView.add(sView2);

        miShutdown.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        miShutdown.setText("Shutdown");
        miShutdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miShutdownActionPerformed(evt);
            }
        });
        mView.add(miShutdown);

        mbMain.add(mView);

        setJMenuBar(mbMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pView, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pView, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
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

    private void bNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextActionPerformed
        master.nextTurn();
    }//GEN-LAST:event_bNextActionPerformed

    private void bAddCombatantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddCombatantActionPerformed
        CombatantSelectionPanel selectionPanel = new CombatantSelectionPanel();
        switch (JOptionPane.showConfirmDialog(this, selectionPanel, "Select character", JOptionPane.OK_CANCEL_OPTION)) {
            case JOptionPane.OK_OPTION:
                AbstractCharacter selection = selectionPanel.getSelection();
                boolean keepAsking = true;
                AddCombatantPanel addCombatantFrame = new AddCombatantPanel(selection);
                do {
                    switch (JOptionPane.showConfirmDialog(this, addCombatantFrame, "Add character", JOptionPane.OK_CANCEL_OPTION)) {
                        case JOptionPane.OK_OPTION:
                            Combatant combatant = addCombatantFrame.getCombatant();
                            if (combatant == null) {
                                continue;
                            }
                            master.addCombatant(combatant);
                            break;
                        default:
                            break;
                    }
                    keepAsking = false;
                } while (keepAsking);
                break;
            default:
                break;
        }
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
                master.updateAll(true);
                break;
            case JOptionPane.NO_OPTION:
                master.startNewBattle();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_bNewBattleActionPerformed

    private void miDiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDiceActionPerformed
        changeView("Dice");
    }//GEN-LAST:event_miDiceActionPerformed

    private void miAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAudioActionPerformed
        changeView("Audio");
    }//GEN-LAST:event_miAudioActionPerformed

    private void miSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSettingsActionPerformed
        changeView("Settings");
    }//GEN-LAST:event_miSettingsActionPerformed

    private void miShutdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miShutdownActionPerformed
        this.dispose();
    }//GEN-LAST:event_miShutdownActionPerformed

    private void miClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miClientsActionPerformed
        changeView("Clients");
        refreshClientsTable();
    }//GEN-LAST:event_miClientsActionPerformed

    private synchronized void refreshClientsTable() {
        DefaultTableModel model = (DefaultTableModel) tClients.getModel();
        model.setRowCount(0);
        for (MasterConnectionInfo connectionInfo : master.getSlaves()) {
            String ip = "Unknown";
            try {
                ip = connectionInfo.isLocalhost() ? "localhost" : connectionInfo.getSlave().getIp();
            } catch (RemoteException ex) {
                log.error("Error retrieveing remote ip", ex);
            }

            String name = connectionInfo.getPlayerName();

            String ping;
            long start = System.currentTimeMillis();
            try {
                connectionInfo.getSlave().ping();
                long time = System.currentTimeMillis() - start;
                ping = (time > 0 ? time + " ms" : "< 1 ms");
            } catch (RemoteException ex) {
                log.error("Error pinging", ex);
                ping = "Error";
            }
            model.addRow(new Object[]{ip, name, ping});
        }
    }

    private void bKickClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKickClientActionPerformed
        int selectedRow = tClients.getSelectedRow();
        if (selectedRow >= 0) {
            master.kick(master.getSlaves().get(selectedRow));
            refreshClientsTable();
        }
    }//GEN-LAST:event_bKickClientActionPerformed

    private void miBattleViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miBattleViewActionPerformed
        changeView("Battle");
    }//GEN-LAST:event_miBattleViewActionPerformed

    private void miBattleNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miBattleNextActionPerformed
        bNextActionPerformed(evt);
        changeView("Battle");
    }//GEN-LAST:event_miBattleNextActionPerformed

    private void miBattleNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miBattleNewActionPerformed
        bNewBattleActionPerformed(evt);
        changeView("Battle");
    }//GEN-LAST:event_miBattleNewActionPerformed

    private void miBattleAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miBattleAddActionPerformed
        bAddCombatantActionPerformed(evt);
        changeView("Battle");
    }//GEN-LAST:event_miBattleAddActionPerformed

    private void miSpellsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSpellsActionPerformed
        changeView("Spells");
    }//GEN-LAST:event_miSpellsActionPerformed

    private void miWeaponsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miWeaponsActionPerformed
        changeView("Weapons");
    }//GEN-LAST:event_miWeaponsActionPerformed

    private void miArmorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miArmorsActionPerformed
        changeView("Armors");
    }//GEN-LAST:event_miArmorsActionPerformed

    private void tbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbRefreshActionPerformed
        if (tbRefresh.isSelected() != refreshTimer.isRunning()) {
            if (tbRefresh.isSelected()) {
                refreshTimer.start();
            } else {
                refreshTimer.stop();
            }
        }
    }//GEN-LAST:event_tbRefreshActionPerformed

    private void miEncounterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEncounterActionPerformed
        changeView("Encounter");
    }//GEN-LAST:event_miEncounterActionPerformed

    private void changeView(String cardName) {
        CardLayout layout = (CardLayout) (pView.getLayout());
        layout.show(pView, cardName);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.wouter.dndbattle.view.master.armor.ArmorsPanel armorsPanel;
    private javax.swing.JButton bAddCombatant;
    private javax.swing.JButton bKickClient;
    private javax.swing.JButton bNewBattle;
    private javax.swing.JButton bNext;
    private com.wouter.dndbattle.view.master.DicePanel dicePanel;
    private javax.swing.JMenu mAttributes;
    private javax.swing.JMenu mBattle;
    private javax.swing.JMenu mCharacters;
    private javax.swing.JMenu mView;
    private javax.swing.JMenuBar mbMain;
    private javax.swing.JMenuItem miArmors;
    private javax.swing.JMenuItem miAudio;
    private javax.swing.JMenuItem miBattleAdd;
    private javax.swing.JMenuItem miBattleNew;
    private javax.swing.JMenuItem miBattleNext;
    private javax.swing.JMenuItem miBattleView;
    private javax.swing.JMenuItem miClients;
    private javax.swing.JMenuItem miDice;
    private javax.swing.JMenuItem miEncounter;
    private javax.swing.JMenuItem miSettings;
    private javax.swing.JMenuItem miShutdown;
    private javax.swing.JMenuItem miSpells;
    private javax.swing.JMenuItem miWeapons;
    private com.wouter.dndbattle.view.master.AudioPanel pAudio;
    private javax.swing.JPanel pCharacters;
    private javax.swing.JPanel pClients;
    private javax.swing.JPanel pCombatants;
    private com.wouter.dndbattle.view.master.encounter.EncounterCalculator pEncounterCalculator;
    private javax.swing.JPanel pMain;
    private javax.swing.JPanel pView;
    private javax.swing.JPopupMenu.Separator sBattle;
    private javax.swing.JPopupMenu.Separator sView1;
    private javax.swing.JPopupMenu.Separator sView2;
    private com.wouter.dndbattle.view.master.SettingsPanel settingsPanel;
    private javax.swing.JScrollPane spClientsTable;
    private javax.swing.JScrollPane spCombatants;
    private javax.swing.JScrollPane spDice;
    private javax.swing.JScrollPane spSettings;
    private com.wouter.dndbattle.view.master.spells.SpellsPanel spellsPanel;
    private javax.swing.JTable tClients;
    private javax.swing.JToggleButton tbRefresh;
    private javax.swing.JTabbedPane tpBattle;
    private com.wouter.dndbattle.view.master.weapons.WeaponsPanel weaponsPanel;
    // End of variables declaration//GEN-END:variables

    public void refreshBattle(final List<ICombatant> combatants, int activeIndex) {
        pCombatants.removeAll();
        while (tpBattle.getTabCount() > 1) {
            tpBattle.remove(1);
        }
        List<ICombatant> characters = new ArrayList<>();
        log.debug("Removed all from view to leave a total of [{}] components in the view", pCombatants.getComponents().length);
        for (int i = activeIndex; i < combatants.size(); i++) {
            final Combatant combatant = (Combatant) combatants.get(i);
            addCombatant(combatant);
            addCharacterToList(characters, combatant);
        }
        for (int i = 0; i < activeIndex; i++) {
            final Combatant combatant = (Combatant) combatants.get(i);
            addCombatant(combatant);
            addCharacterToList(characters, combatant);
        }
        Collections.sort(characters, (ICombatant t, ICombatant t1) -> t.getCharacter().compareTo(t1.getCharacter()));
        ICharacter previousCharacter = null;
        for (ICombatant combatant : characters) {
            if (!combatant.getCharacter().equals(previousCharacter)) {
                tpBattle.add(new SlaveCharacterPanel(combatant));
                previousCharacter = combatant.getCharacter();
            }
        }
        log.debug("Added all combatants to get a new total of [{}] components in the view", pCombatants.getComponents().length);
        if (pCombatants.getComponents().length == 0) {
            pCombatants.add(new JPanel());
        }
        pCombatants.revalidate();
    }

    private void addCharacterToList(List<ICombatant> list, Combatant combatant) {
        list.add(combatant);
        if (combatant.isTransformed()) {
            addCharacterToList(list, combatant.getTransformation());
        }
    }

    private void addCombatant(Combatant combatant) {
        log.debug("Adding {} of class {}", combatant, combatant.getClass());
        pCombatants.add(new MasterCombatantPanel(master, combatant));
    }

    private void addItemsToMCharacters() {
        Class<? extends ICharacter>[] classes = ClassComboBox.getAllClasses();
        for (int i = 0; i < classes.length; i++) {
            Class<? extends ICharacter> clazz = classes[i];
            final String previous = (i == 0 ? classes[classes.length - 1] : classes[i - 1]).getSimpleName();
            final String next = (i == classes.length - 1 ? classes[0] : classes[i + 1]).getSimpleName();
            mCharacters.add(new ClassMenuItem(clazz, previous, next));
        }
    }

    private class ClassMenuItem extends JMenuItem {

        private final MasterCharactersPanel panel;

        public ClassMenuItem(Class clazz, String previous, String next) {
            String name = clazz.getSimpleName();
            setName(name);
            setText(name);

            panel = new MasterCharactersPanel(clazz, previous, next);
            pCharacters.add(panel);
            CardLayout layout = (CardLayout) (pCharacters.getLayout());
            layout.addLayoutComponent(panel, name);
            panel.getAccessibleContext().setAccessibleName(name);
            pCharacters.revalidate();

            addActionListener((java.awt.event.ActionEvent evt) -> {
                actionPerformed(evt);
            });
            panel.updateList();
        }

        private void actionPerformed(ActionEvent evt) {
            log.debug("Recieved action for class item [{}]", getName());
            panel.updateList();
            CardLayout layout = (CardLayout) (pCharacters.getLayout());
            layout.show(pCharacters, getName());
            changeView("Characters");
        }
    }
}
