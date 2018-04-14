/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle;

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

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("--alpha")) {
            Settings.setAlpha(true);
        }

        //<editor-fold defaultstate="collapsed" desc=" Setting the look and feel ">
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
        //</editor-fold>

        int port = SETTINGS.getProperty("connection.port", 12345);
        String host;
        if (Settings.isAlpha()) {
            host = "localhost";
        } else {
            JOptionPane.showMessageDialog(null, "Warning!\nThis is a development build.\nIt may not be compatible with future releases.", "Warning", JOptionPane.WARNING_MESSAGE);
            host = JOptionPane.showInputDialog(null, "What host?", "localhost");
            if (host == null) {
                log.debug("User cancelled");
                System.exit(0);
            }
            log.debug("User input on host request: [{}]", host);
        }

        try {
            connectSlave(host, port);
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Master doesn't seem present [" + e + "] creating now.");
            createMaster(port);
        }
    }

    private static void createMaster(int port) throws HeadlessException {
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
            System.out.println("Master can't be started [" + ex + "]");
            JOptionPane.showMessageDialog(null, "Unable to start.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void connectSlave(String host, int port) throws RemoteException, NotBoundException {
        Registry registry;
        registry = LocateRegistry.getRegistry(host, port);
        IMaster master = (IMaster) registry.lookup("dnd");
        final SlaveFrame slaveFrame = new SlaveFrame(master);
        IMasterConnectionInfo connectionInfo = master.connect((ISlave) UnicastRemoteObject.exportObject(slaveFrame.getSlave(), 0));
        slaveFrame.setConnectionInfo(connectionInfo);
        java.awt.EventQueue.invokeLater(() -> {
            slaveFrame.setVisible(true);
        });
    }

    public Main() {
    }

    public void start(String host) {
    }

}
