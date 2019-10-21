/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dndbattle.view.slave;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JPanel;
import org.dndbattle.core.IMaster;
import org.dndbattle.core.IMasterConnectionInfo;
import org.dndbattle.core.ISlave;
import org.dndbattle.core.impl.Slave;
import org.dndbattle.objects.ICombatant;
import static org.dndbattle.utils.Settings.SLAVE_LOCATION_X;
import static org.dndbattle.utils.Settings.SLAVE_LOCATION_Y;
import static org.dndbattle.utils.Settings.SLAVE_SIZE_HEIGHT;
import static org.dndbattle.utils.Settings.SLAVE_SIZE_STATE;
import static org.dndbattle.utils.Settings.SLAVE_SIZE_WIDTH;
import org.dndbattle.view.slave.character.SlaveCharacterPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public final class SlaveFrame extends javax.swing.JFrame {

    private static final String SINGLE = "single";

    private static final Logger log = LoggerFactory.getLogger(SlaveFrame.class);

    private final Slave slave;

    public SlaveFrame(IMaster master, String ip) {
        this.slave = new Slave(master, this, ip);
        initComponents();
        setLocation(slave.getProperty(SLAVE_LOCATION_X, Integer.MIN_VALUE), slave.getProperty(SLAVE_LOCATION_Y, Integer.MIN_VALUE));
        int width = slave.getProperty(SLAVE_SIZE_WIDTH, getPreferredSize().width);
        int height = slave.getProperty(SLAVE_SIZE_HEIGHT, getPreferredSize().width);
        setSize(width, height);
        setExtendedState(slave.getProperty(SLAVE_SIZE_STATE, 0));
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

    public ISlave getSlave() {
        return slave;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spSlave = new javax.swing.JSplitPane();
        spCombatants = new javax.swing.JScrollPane();
        pCombatants = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        spSlave.setDividerLocation(1d);

        spCombatants.setBorder(null);
        spCombatants.setName("Battle"); // NOI18N

        pCombatants.setLayout(new javax.swing.BoxLayout(pCombatants, javax.swing.BoxLayout.Y_AXIS));
        spCombatants.setViewportView(pCombatants);

        spSlave.setLeftComponent(spCombatants);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spSlave, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spSlave, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );

        //spSlave.setLeftComponent(spCombatants);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        if (getExtendedState() == NORMAL) {
            slave.setProperty(SLAVE_LOCATION_X, getLocation().x);
            slave.setProperty(SLAVE_LOCATION_Y, getLocation().y);
        }
    }//GEN-LAST:event_formComponentMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (getExtendedState() == NORMAL) {
            slave.setProperty(SLAVE_SIZE_WIDTH, getWidth());
            slave.setProperty(SLAVE_SIZE_HEIGHT, getHeight());
        }
        if (getExtendedState() != ICONIFIED) {
            slave.setProperty(SLAVE_SIZE_STATE, getExtendedState());
        }
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pCombatants;
    private javax.swing.JScrollPane spCombatants;
    private javax.swing.JSplitPane spSlave;
    // End of variables declaration//GEN-END:variables

    public void refreshBattle(List<ICombatant> combatants, int activeIndex) {
        pCombatants.removeAll();
        log.debug("Removed all from view to leave a total of [{}] components in the view", pCombatants.getComponents().length);
        for (int i = activeIndex; i < combatants.size(); i++) {
            final ICombatant combatant = combatants.get(i);
            addCombatant(combatant);
        }
        for (int i = 0; i < activeIndex; i++) {
            final ICombatant combatant = combatants.get(i);
            addCombatant(combatant);
        }
        log.debug("Added all combatants to get a new total of [{}] components in the view", pCombatants.getComponents().length);
        if (pCombatants.getComponents().length == 0) {
            pCombatants.add(new JPanel());
        }
        pCombatants.revalidate();
    }

    public void refreshCombatants(List<ICombatant> combatants) {
        spSlave.setRightComponent(null);
        spSlave.setDividerLocation(1d);
    }

    private boolean checkOwnCharacter(ICombatant combatant) {
        IMasterConnectionInfo connectionInfo = slave.getConnectionInfo();
        return connectionInfo != null && combatant.ownedbyPlayer(connectionInfo.getPlayerName());
    }

    public void createCharacterPanel(ICombatant combatant) {
        final SlaveCharacterPanel panel = new SlaveCharacterPanel(combatant.getCombatantCharacter());
        setRightPanel(panel);
    }

    private void setRightPanel(JPanel panel) {
        spSlave.setRightComponent(panel);
    }

    private void addCombatant(ICombatant combatant) {
        log.debug("Adding {} of class {}", combatant, combatant.getClass());
        pCombatants.add(new SlaveSubPanel(combatant, checkOwnCharacter(combatant) ? this : null));
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}
