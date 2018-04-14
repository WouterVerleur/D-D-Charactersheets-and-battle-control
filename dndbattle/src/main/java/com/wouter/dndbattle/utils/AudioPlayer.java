/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioPlayer extends Thread {

    private static final Logger log = LoggerFactory.getLogger(AudioPlayer.class);
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb

    private final File soundFile;

    public AudioPlayer(File soundFile) {
        this.soundFile = soundFile;
    }

    public AudioPlayer(String audioFile) {
        this(new File(audioFile));
    }

    @Override
    public void run() {

        if (!soundFile.exists()) {
            log.error("File [{}] does not exist", soundFile);
            return;
        }

        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            log.error("Error opening file [{}]", soundFile, e);
            return;
        }
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            log.error("Error opening audio from file [{}]", soundFile, e);
            return;
        }

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1 && !isInterrupted()) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }
            }
        } catch (IOException e) {
            log.error("Error playing audio from file [{}]", soundFile, e);
        } finally {
            auline.drain();
            auline.close();
        }
    }

    public static class AudioFormatFilter extends FileFilter {

        private static final String DESCRIPTION = "Supported audiofiles (.wav, .aaif, .au)";

        public AudioFormatFilter() {
        }

        @Override
        public boolean accept(File file) {
            String fileExtension = GlobalUtils.getFileExtension(file);
            if (fileExtension.equalsIgnoreCase("wav") || fileExtension.equalsIgnoreCase("aaif") || fileExtension.equalsIgnoreCase("au")) {
                try {
                    AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(file);
                    return AudioSystem.isFileTypeSupported(audioFileFormat.getType());
                } catch (UnsupportedAudioFileException | IOException e) {
                    log.error("Error while checking if file [{}] is supported", file, e);
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

    }
}
