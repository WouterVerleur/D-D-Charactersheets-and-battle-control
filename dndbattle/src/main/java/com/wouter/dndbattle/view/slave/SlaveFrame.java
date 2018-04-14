/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.view.slave;

import static com.wouter.dndbattle.utils.Settings.SLAVE_LOCATION_X;
import static com.wouter.dndbattle.utils.Settings.SLAVE_LOCATION_Y;
import static com.wouter.dndbattle.utils.Settings.SLAVE_SIZE_HEIGHT;
import static com.wouter.dndbattle.utils.Settings.SLAVE_SIZE_STATE;
import static com.wouter.dndbattle.utils.Settings.SLAVE_SIZE_WIDTH;

import java.util.List;

import javax.swing.JPanel;

import com.wouter.dndbattle.IMaster;
import com.wouter.dndbattle.IMasterConnectionInfo;
import com.wouter.dndbattle.ISlave;
import com.wouter.dndbattle.impl.Slave;
import com.wouter.dndbattle.objects.ICombatant;
import com.wouter.dndbattle.utils.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class SlaveFrame extends javax.swing.JFrame {

    private static final Logger log = LoggerFactory.getLogger(SlaveFrame.class);

    private Settings settings = Settings.getInstance();
    private IMasterConnectionInfo connectionInfo;

    private final Slave slave;

    public SlaveFrame(IMaster master) {
        this.slave = new Slave(master, this);
        initComponents();
        int x = settings.getProperty(SLAVE_LOCATION_X, 0);
        int y = settings.getProperty(SLAVE_LOCATION_Y, 0);
        setLocation(x, y);
        int width = settings.getProperty(SLAVE_SIZE_WIDTH, getPreferredSize().width);
        int height = settings.getProperty(SLAVE_SIZE_HEIGHT, getPreferredSize().width);
        setSize(width, height);
        setExtendedState(settings.getProperty(SLAVE_SIZE_STATE, 0));
    }

    public void setConnectionInfo(IMasterConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
        if (connectionInfo.isLocalhost()) {
            log.debug("Getting settings from master");
            settings = slave.getSettings();
        }
        setTitle(connectionInfo.getSlaveName());
    }

    public ISlave getSlave() {
        return slave;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pView = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        pView.setLayout(new javax.swing.BoxLayout(pView, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(pView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        if (getExtendedState() == NORMAL) {
            settings.setProperty(SLAVE_LOCATION_X, getLocation().x);
            settings.setProperty(SLAVE_LOCATION_Y, getLocation().y);
        }
    }//GEN-LAST:event_formComponentMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (getExtendedState() == NORMAL) {
            settings.setProperty(SLAVE_SIZE_WIDTH, getWidth());
            settings.setProperty(SLAVE_SIZE_HEIGHT, getHeight());
        }
        if (getExtendedState() != ICONIFIED) {
            settings.setProperty(SLAVE_SIZE_STATE, getExtendedState());
        }
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pView;
    // End of variables declaration//GEN-END:variables

    public void showCombatants(List<ICombatant> combatants, int activeIndex) {
        pView.removeAll();
        log.debug("Removed all from view to leave a total of [{}] components in the view", pView.getComponents().length);
        for (int i = activeIndex; i < combatants.size(); i++) {
            addCombatant(combatants.get(i));
        }
        for (int i = 0; i < activeIndex; i++) {
            addCombatant(combatants.get(i));
        }
        log.debug("Added all combatants to get a new total of [{}] components in the view", pView.getComponents().length);
        if (pView.getComponents().length == 0) {
            pView.add(new JPanel());
        }
        pView.revalidate();
    }

    private void addCombatant(ICombatant combatant) {
        log.debug("Adding {} of class {}", combatant, combatant.getClass());
        pView.add(new SlaveSubPanel(combatant));
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}
