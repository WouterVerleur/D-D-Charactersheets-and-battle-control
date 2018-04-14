/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.view.master;

import static java.nio.file.StandardCopyOption.*;

import static com.wouter.dndbattle.utils.GlobalUtils.getFileName;
import static com.wouter.dndbattle.utils.GlobalUtils.getFileNameWithoutExtention;
import static com.wouter.dndbattle.utils.Settings.INPUT_FILESELECTION;

import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.wouter.dndbattle.utils.AudioPlayer;
import com.wouter.dndbattle.utils.FileManager;
import com.wouter.dndbattle.utils.Settings;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class AudioPanel extends javax.swing.JPanel {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final Settings SETTINGS = Settings.getInstance();

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AudioPanel.class);
    private static final File baseFolder = FileManager.getFolder("audio");

    /**
     * Creates new form AudioPanel
     */
    public AudioPanel() {
        initComponents();
        for (File audioFile : baseFolder.listFiles()) {
            addAudioButton(audioFile);
        }
    }

    private void addAudioButton(File audioFile) {
        log.debug("Adding button for file [{}]", audioFile);
        JButton button = new JButton(getFileNameWithoutExtention(audioFile).replace('_', ' '));
        button.addActionListener(new AudioActionListener(audioFile, this));
        pAudio.add(button);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bAdd = new javax.swing.JButton();
        spOverview = new javax.swing.JScrollPane();
        pAudio = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        bAdd.setText("Add more audio");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });

        spOverview.setBorder(null);

        pAudio.setLayout(new java.awt.GridLayout(0, 3));
        spOverview.setViewportView(pAudio);

        jButton1.setText("Stop all audio");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spOverview)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(spOverview)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAdd)
                    .addComponent(jButton1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        JFileChooser chooser = new JFileChooser(SETTINGS.getProperty(INPUT_FILESELECTION, ""));
        chooser.setMultiSelectionEnabled(true);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new AudioPlayer.AudioFormatFilter());
        int selection = chooser.showDialog(this, "Import");
        if (selection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        List<String> failedFiles = new ArrayList<>();
        File[] selectedFiles = chooser.getSelectedFiles();
        if (selectedFiles.length > 0){
            SETTINGS.setProperty(INPUT_FILESELECTION, selectedFiles[0].getParent());
        }
        for (File selectedFile : selectedFiles) {
            String fileName = getFileName(selectedFile);
            File targetFile = new File(baseFolder, fileName);
            try {
                Files.copy(selectedFile.toPath(), targetFile.toPath(), COPY_ATTRIBUTES, REPLACE_EXISTING);
                addAudioButton(targetFile);
            } catch (IOException e) {
                log.error("Unable to copy file [{}] to [{}]", selectedFile, targetFile, e);
                failedFiles.add(fileName);
            }
        }
        if (failedFiles.size() > 0) {
            StringBuilder builder = new StringBuilder("Unable to import the following file" + (failedFiles.size() == 1 ? "" : "s") + ": ");
            for (Iterator<String> failedFileIterator = failedFiles.iterator(); failedFileIterator.hasNext();) {
                String next = failedFileIterator.next();
                builder.append(next);
                if (failedFileIterator.hasNext()) {
                    builder.append(", ");
                }
            }
            JOptionPane.showMessageDialog(this, builder.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bAddActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        executorService.shutdownNow();
        executorService = Executors.newSingleThreadExecutor();
    }//GEN-LAST:event_jButton1ActionPerformed

    public ExecutorService getExecutorService() {
        return executorService;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel pAudio;
    private javax.swing.JScrollPane spOverview;
    // End of variables declaration//GEN-END:variables

    private static class AudioActionListener implements ActionListener {

        private final File audioFile;
        private final AudioPanel panel;

        public AudioActionListener(File audioFile, AudioPanel panel) {
            this.audioFile = audioFile;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            log.debug("Playing sound from file [{}]", audioFile);
            AudioPlayer player = new AudioPlayer(audioFile);
            panel.getExecutorService().submit(player);
        }
    }
}
