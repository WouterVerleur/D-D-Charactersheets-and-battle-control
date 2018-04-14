/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.awt.Color;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class GlobalUtils {

    private static final Logger log = LoggerFactory.getLogger(GlobalUtils.class);

    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
    }

    public static String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        } catch (Exception e) {
            log.error("Error while getting file extention from [{}]", fileName, e);
            return fileName;
        }
    }

    public static String getFileName(File file) {
        return file.getName();
    }

    public static String getFileName(String fileName) {
        try {
            return fileName.substring(fileName.replace('\\', '/').lastIndexOf('/') + 1);
        } catch (Exception e) {
            log.error("Error while getting filename from [{}]", fileName, e);
            return fileName;
        }
    }

    public static String getFileNameWithoutExtention(File file) {
        return getFileNameWithoutExtention(file.getName());
    }

    public static String getFileNameWithoutExtention(String fileName) {
        try {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        } catch (Exception e) {
            log.error("Error while getting filename without extention from [{}]", fileName, e);
            return fileName;
        }
    }

    public static String modifierToString(int modifier) {
        return modifier > 0 ? "+" + modifier : Integer.toString(modifier);
    }

    public static Color getTransparentColor() {
        return new Color(0, 0, 0, 0);
    }
}
