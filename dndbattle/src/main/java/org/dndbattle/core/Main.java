/*
 * Copyright (C) 2018 Wouter
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
package org.dndbattle.core;

import static java.awt.Frame.NORMAL;

import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

import static org.dndbattle.utils.Settings.CONNECTION_HOST;
import static org.dndbattle.utils.Settings.CONNECTION_NAME;
import static org.dndbattle.utils.Settings.CONNECTION_PORT;
import static org.dndbattle.utils.Settings.HOST_LOCATION_X;
import static org.dndbattle.utils.Settings.HOST_LOCATION_Y;
import static org.dndbattle.utils.Settings.LOOKANDFEEL;

import java.awt.Color;
import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dndbattle.core.impl.Host;
import org.dndbattle.utils.Armors;
import org.dndbattle.utils.Characters;
import org.dndbattle.utils.Initializable;
import org.dndbattle.utils.Settings;
import org.dndbattle.utils.Spells;
import org.dndbattle.utils.Utilities;
import org.dndbattle.utils.Weapons;
import org.dndbattle.view.client.ClientFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Main extends javax.swing.JFrame implements Initializable.IProgressKeeper {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  private static final Settings SETTINGS = Settings.getInstance();

  private static final String LOCALHOST = "localhost";
  private static final int DEFAULT_PORT = 4144; // d = 4, n = 14, dnd=4144
  private static final Main MAIN = new Main();

  private final ImageIcon image;

  private static int port = DEFAULT_PORT;
  private static String ip = null;

  public static void main(String[] args) {
    String hostOverride = null;
    for (String arg : args) {
      switch (arg.toLowerCase()) {
        case "--alpha":
          Settings.setAlpha(true);
          break;
        case "--localhost":
          hostOverride = LOCALHOST;
          break;
        default:
          log.error("Unknown option [{}]", arg);
          break;
      }
    }
    ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
    java.awt.EventQueue.invokeLater(() -> {
      MAIN.setVisible(true);
    });
    final String runHost = hostOverride;
    Thread thread = new Thread(() -> {
      startup(runHost);
    });
    thread.start();
  }

  private static void startup(final String hostOverride) {
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
    start(false, hostOverride);
  }

  private static void start(boolean requestPort, String hostOverride) throws HeadlessException {
    String host = hostOverride;
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
    if (host == null || host.isEmpty()) {
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
    }
    try {
      connectClient(host);
    } catch (RemoteException | NotBoundException e) {
      logToScreen("Unable to connect to host: " + host);
      log.debug("Host doesn't seem present [" + e + "] creating now.", e);
      boolean createHost = host.equalsIgnoreCase(LOCALHOST);
      if (!createHost) {
        switch (JOptionPane.showConfirmDialog(MAIN, "No connection could be made to host " + host + ".\nWould you like to create a host on this machine?", "No connection", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
          case YES_OPTION:
            createHost = true;
            break;
          case CANCEL_OPTION:
            start(true, host);
            break;
          default:
            System.exit(1);
            break;
        }
      }
      if (createHost) {
        createHost();
      }
    }
  }

  private static void createHost() throws HeadlessException {
    logToScreen("Creating host.");
    Registry registry;
    try {
      registry = LocateRegistry.createRegistry(port);
      final Host host = new Host(ip);
      logToScreen(String.format("Attempting to create a host on port %d", port));
      IHost stub = (IHost) UnicastRemoteObject.exportObject(host, port);
      registry.bind("dnd", stub);
      logToScreen("Done.");
      MAIN.pbSub.setVisible(true);
      MAIN.pbSub.setStringPainted(true);
      logToScreen("Loading armors.");
      loadObjects(Armors.getInstance());
      logToScreen("Loading spells.");
      loadObjects(Spells.getInstance());
      logToScreen("Loading utilities.");
      loadObjects(Utilities.getInstance());
      logToScreen("Loading weapons.");
      loadObjects(Weapons.getInstance());
      logToScreen("Loading characters.");
      loadObjects(Characters.getInstance());
      logToScreen("Starting interface.");
      host.getFrame().setIconImage(MAIN.getIconImage());
      startFrame(host.getFrame());
    } catch (RemoteException | AlreadyBoundException e) {
      log.error("Host can't be started [" + e + "]", e);
      JOptionPane.showMessageDialog(MAIN, "Unable to start.", "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }

  private static void loadObjects(Initializable initializable) {
    initializable.registerProgressKeeper(MAIN);
    initializable.initialize();
    initializable.unregisterProgressKeeper(MAIN);
  }

  @Override
  public void notifyProgress(int progress) {
    pbSub.setValue(progress);
  }

  private static void connectClient(String hostname) throws RemoteException, NotBoundException {
    logToScreen(String.format("Attempting to connect with the host %s on port %d", hostname, port));
    Registry registry;
    registry = LocateRegistry.getRegistry(hostname, port);
    IHost host = (IHost) registry.lookup("dnd");
    final ClientFrame clientFrame = new ClientFrame(host, ip);
    clientFrame.setIconImage(MAIN.getIconImage());
    IClient remoteClient = (IClient) UnicastRemoteObject.exportObject(clientFrame.getClient(), 0);
    String playerName = JOptionPane.showInputDialog(MAIN, "What is your name?", SETTINGS.getProperty(CONNECTION_NAME));
    if (playerName != null && !playerName.isEmpty()) {
      SETTINGS.setProperty(CONNECTION_NAME, playerName);
    }
    host.connect(remoteClient, playerName);
    log.debug("Connected client to host [{}]", host);
    startFrame(clientFrame);
  }

  private static void startFrame(JFrame frame) {
    logToScreen("Startup procedure complete. Opening interface.");
    java.awt.EventQueue.invokeLater(() -> {
      MAIN.dispose();
      frame.setVisible(true);
    });
  }

  private static void logToScreen(String logLine) {
    MAIN.pbMain.setString(logLine);
  }

  private Main() {
    initComponents();
    final ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("/images/font_awesome_5_solid_dice-d20.png");
    if (resource == null) {
      resource = classLoader.getResource("images/font_awesome_5_solid_dice-d20.png");
    }
    log.debug("Resource = [{}]", resource);
    if (resource != null) {
      image = new ImageIcon(resource);
      log.debug("Resource is an image of [{}] by [{}] pixels", image.getIconHeight(), image.getIconWidth());
      setIconImage(image.getImage());
    } else {
      image = null;
    }
    //setLocation(SETTINGS.getProperty(HOST_LOCATION_X, 0), SETTINGS.getProperty(HOST_LOCATION_Y, 0));
    setLocationRelativeTo(null);
    //setExtendedState(SETTINGS.getProperty(HOST_SIZE_STATE, 0));
  }

  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pbMain = new javax.swing.JProgressBar();
        pbSub = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Starting program");
        setName("Starting"); // NOI18N
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pbMain.setIndeterminate(true);
        pbMain.setString("");
        pbMain.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(pbMain, gridBagConstraints);

        pbSub.setVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        getContentPane().add(pbSub, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/font_awesome_5_solid_dice-d20.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("D&D Charactersheets and Battle control");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
    if (getExtendedState() == NORMAL) {
      SETTINGS.setProperty(HOST_LOCATION_X, getLocation().x);
      SETTINGS.setProperty(HOST_LOCATION_Y, getLocation().y);
    }
    }//GEN-LAST:event_formComponentMoved

  public static int getPort() {
    return port;
  }

  public static String getIp() {
    return ip;
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar pbMain;
    private javax.swing.JProgressBar pbSub;
    // End of variables declaration//GEN-END:variables
}
