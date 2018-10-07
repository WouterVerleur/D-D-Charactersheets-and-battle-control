/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.core;

import static java.awt.Frame.NORMAL;

import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

import static com.wouter.dndbattle.utils.Settings.CONNECTION_HOST;
import static com.wouter.dndbattle.utils.Settings.CONNECTION_NAME;
import static com.wouter.dndbattle.utils.Settings.CONNECTION_PORT;
import static com.wouter.dndbattle.utils.Settings.LOOKANDFEEL;
import static com.wouter.dndbattle.utils.Settings.MASTER_LOCATION_X;
import static com.wouter.dndbattle.utils.Settings.MASTER_LOCATION_Y;
import static com.wouter.dndbattle.utils.Settings.MASTER_SIZE_STATE;

import java.awt.Color;
import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.wouter.dndbattle.utils.GlobalUtils;
import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.master.MasterFrame;
import com.wouter.dndbattle.view.slave.SlaveFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Main extends javax.swing.JFrame {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final Settings SETTINGS = Settings.getInstance();

    private static final String LOCALHOST = "localhost";
    private static final int DEFAULT_PORT = 12345;
    private static final Main MAIN = new Main();

    private static int port = DEFAULT_PORT;
    private static String ip = null;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MAIN.setVisible(true);
        });
        logToScreen("Attempting to determine primary IP");
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements() && (ip == null || ip.isEmpty())) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> netAdresses = networkInterface.getInetAddresses();
                while (netAdresses.hasMoreElements() && (ip == null || ip.isEmpty())) {
                    InetAddress address = netAdresses.nextElement();
                    if (address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isMulticastAddress()) {
                        continue;
                    }
                    ip = address.getHostAddress();
                    break;
                }
            }
        } catch (SocketException e) {
            log.error("Error during startup", e);
            System.exit(1);
        }
        logToScreen("IP found to be " + ip);
        System.setProperty("java.rmi.server.hostname", ip);
        if (args.length > 0 && args[0].equalsIgnoreCase("--alpha")) {
            Settings.setAlpha(true);
        }

        String lookAndFeel = SETTINGS.getProperty(LOOKANDFEEL, "Nimbus");
        logToScreen("Settings look and feel to " + lookAndFeel);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeel.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Error while setting the look and feel " + e);
        }
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        defaults.put("nimbusOrange", Color.GREEN);

        logToScreen("Starting main program");
        start(false);
    }

    private static void start(boolean requestPort) throws HeadlessException {
        String host;
        if (requestPort) {
            port = 0;
            while (port == 0) {
                logToScreen("Requesting port");
                String portInput = JOptionPane.showInputDialog(MAIN, "What port?", SETTINGS.getProperty(CONNECTION_PORT, DEFAULT_PORT));
                if (portInput == null) {
                    log.debug("User cancelled");
                    System.exit(0);
                }
                log.debug("User input on port request: [{}]", portInput);
                try {
                    port = Integer.parseInt(portInput);
                } catch (NumberFormatException e) {
                    log.error("Error while parsing userinput [{}] as a number.", portInput, e);
                }
            }
        } else {
            port = SETTINGS.getProperty("connection.port", DEFAULT_PORT);
        }
        if (Settings.isAlpha()) {
            host = SETTINGS.getProperty(CONNECTION_HOST, LOCALHOST);
        } else {
            logToScreen("Requesting host");
            host = JOptionPane.showInputDialog(MAIN, "What host?", SETTINGS.getProperty(CONNECTION_HOST, LOCALHOST));
            if (host == null) {
                log.debug("User cancelled");
                System.exit(0);
            }
            SETTINGS.setProperty(CONNECTION_HOST, host);
            log.debug("User input on host request: [{}]", host);
        }
        try {
            connectSlave(host);
        } catch (RemoteException | NotBoundException e) {
            log.debug("Master doesn't seem present [" + e + "] creating now.");
            boolean createMaster = host.equalsIgnoreCase(LOCALHOST);
            if (!createMaster) {
                switch (JOptionPane.showConfirmDialog(MAIN, "No connection could be made to host " + host + ".\nWould you like to create a master on this machine?", "No connection", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    case YES_OPTION:
                        createMaster = true;
                        break;
                    case CANCEL_OPTION:
                        start(true);
                        break;
                    default:
                        System.exit(1);
                        break;
                }
            }
            if (createMaster) {
                createMaster();
            }
        }
    }

    private static void createMaster() throws HeadlessException {
        logToScreen(String.format("Attempting to create a master on port %d", port));
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(port);
            logToScreen("Loading characters, spells and weapons.");
            final MasterFrame masterFrame = new MasterFrame();
            IMaster stub = (IMaster) UnicastRemoteObject.exportObject(masterFrame.getMaster(), port);
            registry.bind("dnd", stub);
            startFrame(masterFrame);
        } catch (RemoteException | AlreadyBoundException ex) {
            log.error("Master can't be started [" + ex + "]");
            JOptionPane.showMessageDialog(MAIN, "Unable to start.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void connectSlave(String host) throws RemoteException, NotBoundException {
        logToScreen(String.format("Attempting to connect with the master on host %s on port %d", host, port));
        Registry registry;
        registry = LocateRegistry.getRegistry(host, port);
        IMaster master = (IMaster) registry.lookup("dnd");
        final SlaveFrame slaveFrame = new SlaveFrame(master, ip);
        ISlave remoteSlave = (ISlave) UnicastRemoteObject.exportObject(slaveFrame.getSlave(), 0);
        String playerName = JOptionPane.showInputDialog(MAIN, "What is your name?", SETTINGS.getProperty(CONNECTION_NAME));
        if (playerName != null && !playerName.isEmpty()) {
            SETTINGS.setProperty(CONNECTION_NAME, playerName);
        }
        master.connect(remoteSlave, playerName);
        log.debug("Connected slave to master [{}]", master);
        startFrame(slaveFrame);
    }

    private static void startFrame(JFrame frame) {
        logToScreen("Startup procedure complete. Opening interface.");
        java.awt.EventQueue.invokeLater(() -> {
            MAIN.dispose();
            frame.setVisible(true);
        });
    }

    private static void logToScreen(String logLine) {
        JTextArea logger = MAIN.taDisplayLog;
        if (logger.getText().isEmpty()) {
            logger.setText(logLine);
            return;
        }
        logger.setText(logger.getText() + '\n' + logLine);
        JScrollBar vertical = MAIN.spDisplayLog.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private Main() {
        initComponents();
        setLocation(SETTINGS.getProperty(MASTER_LOCATION_X, 0), SETTINGS.getProperty(MASTER_LOCATION_Y, 0));
        setExtendedState(SETTINGS.getProperty(MASTER_SIZE_STATE, 0));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spDisplayLog = new javax.swing.JScrollPane();
        taDisplayLog = new javax.swing.JTextArea();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Starting program");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });

        taDisplayLog.setEditable(false);
        taDisplayLog.setBackground(GlobalUtils.getBackgroundTransparent());
        taDisplayLog.setColumns(20);
        taDisplayLog.setRows(5);
        spDisplayLog.setViewportView(taDisplayLog);

        jProgressBar1.setIndeterminate(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spDisplayLog, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(spDisplayLog, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        if (getExtendedState() == NORMAL) {
            SETTINGS.setProperty(MASTER_LOCATION_X, getLocation().x);
            SETTINGS.setProperty(MASTER_LOCATION_Y, getLocation().y);
        }
    }//GEN-LAST:event_formComponentMoved

    public static int getPort() {
        return port;
    }

    public static String getIp() {
        return ip;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane spDisplayLog;
    private javax.swing.JTextArea taDisplayLog;
    // End of variables declaration//GEN-END:variables
}
