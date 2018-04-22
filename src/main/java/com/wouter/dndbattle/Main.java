/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle;

import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

import static com.wouter.dndbattle.utils.Settings.CONNECTION_HOST;
import static com.wouter.dndbattle.utils.Settings.CONNECTION_NAME;
import static com.wouter.dndbattle.utils.Settings.CONNECTION_PORT;
import static com.wouter.dndbattle.utils.Settings.LOOKANDFEEL;

import java.awt.Color;
import java.awt.HeadlessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.wouter.dndbattle.utils.Settings;
import com.wouter.dndbattle.view.master.MasterFrame;
import com.wouter.dndbattle.view.slave.SlaveFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final Settings SETTINGS = Settings.getInstance();

    private static final String LOCALHOST = "localhost";
    private static final int DEFAULT_PORT = 12345;

    private static int port = DEFAULT_PORT;

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("--alpha")) {
            Settings.setAlpha(true);
        }

        String lookAndFeel = SETTINGS.getProperty(LOOKANDFEEL, "Nimbus");
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

        start(false);
    }

    private static void start(boolean requestPort) throws HeadlessException {
        String host;
        if (requestPort) {
            port = 0;
            while (port == 0) {
                String portInput = JOptionPane.showInputDialog(null, "What port?", SETTINGS.getProperty(CONNECTION_PORT, DEFAULT_PORT));
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
            host = JOptionPane.showInputDialog(null, "What host?", SETTINGS.getProperty(CONNECTION_HOST, LOCALHOST));
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
                switch (JOptionPane.showConfirmDialog(null, "No connection could be made to host " + host + ".\nWould you like to create a master on this machine?", "No connection", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    case YES_OPTION:
                        createMaster = true;
                        break;
                    case CANCEL_OPTION:
                        start(true);
                        break;
                    case NO_OPTION:
                        System.exit(1);
                        break;
                }
            }
            if (createMaster) {
                createMaster();
            }
        }
    }

    public static int getPort() {
        return port;
    }

    private static void createMaster() throws HeadlessException {
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(port);
            final MasterFrame masterFrame = new MasterFrame();
            IMaster stub = (IMaster) UnicastRemoteObject.exportObject(masterFrame.getMaster(), 0);
            registry.bind("dnd", stub);
            java.awt.EventQueue.invokeLater(() -> {
                masterFrame.setVisible(true);
            });
        } catch (RemoteException | AlreadyBoundException ex) {
            log.error("Master can't be started [" + ex + "]");
            JOptionPane.showMessageDialog(null, "Unable to start.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void connectSlave(String host) throws RemoteException, NotBoundException {
        Registry registry;
        registry = LocateRegistry.getRegistry(host, port);
        IMaster master = (IMaster) registry.lookup("dnd");
        final SlaveFrame slaveFrame = new SlaveFrame(master);
        ISlave remoteSlave = (ISlave) UnicastRemoteObject.exportObject(slaveFrame.getSlave(), 0);
        if (host.equalsIgnoreCase("localhost")) {
            String playerName = JOptionPane.showInputDialog(slaveFrame, "What is your name?", SETTINGS.getProperty(CONNECTION_NAME));
            if (playerName != null && !playerName.isEmpty()) {
                SETTINGS.setProperty(CONNECTION_NAME, playerName);
            }
            master.connect(remoteSlave, playerName);
        }
        java.awt.EventQueue.invokeLater(() -> {
            slaveFrame.setVisible(true);
        });
    }

    public Main() {
    }

}
