/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.lowagie.text.DocumentException;
import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.ICharacterClass;
import com.wouter.dndbattle.objects.IExtendedCharacter;
import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.enums.AbilityType;
import com.wouter.dndbattle.objects.enums.Dice;
import com.wouter.dndbattle.objects.enums.WeaponRange;
import com.wouter.dndbattle.objects.impl.AbstractExtendedCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.resource.XMLResource;
import org.xml.sax.InputSource;

/**
 *
 * @author Wouter
 */
public class GlobalUtils {

    private static final Logger log = LoggerFactory.getLogger(GlobalUtils.class);

    public static final String DAMAGE_FORMAT_SHORT = "%s %s";
    public static final String DAMAGE_FORMAT = "%d%s %s %s";

    public static final int NAME = 0;
    public static final int ATTACK = 2;
    public static final int DAMAGE = 3;

    /*
     * File Functions
     */
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
            String name = getFileName(fileName);
            return name.substring(0, name.lastIndexOf('.'));
        } catch (Exception e) {
            log.error("Error while getting filename without extention from [{}]", fileName, e);
            return fileName;
        }
    }

    public static void createPDF(IExtendedCharacter character, File pdf) throws IOException, DocumentException {
        String contents = GlobalUtils.getResourceFileAsString("templates/character.xhtml");

        Class<? extends IExtendedCharacter> aClass = character.getClass();
        Map<String, Object> valuesMap = new HashMap<>();
        for (Method method : aClass.getMethods()) {
            if (method.getParameterCount() != 0) {
                continue;
            }
            try {
                if (method.getName().startsWith("get")) {
                    String name = method.getName().substring(3).toLowerCase();
                    Object value = method.invoke(character);
                    if (value == null && method.getReturnType() == String.class) {
                        value = "";
                    }
                    log.debug("Found value [{}] for name [{}], adding to map", value, name);
                    valuesMap.put(name, value);
                }
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                log.error("Error while executing method [{}] on character [{}]", method, character, e);
            }
        }

        Pattern pattern = Pattern.compile(TEMPLATE_REPLACEMENT_STRING);
        Matcher matcher = pattern.matcher(contents);
        while (matcher.find()) {
            String match = matcher.group();
            String matchName = match.substring(2, match.length() - 1).toLowerCase();
            Object value = valuesMap.get(matchName);
            log.debug("Found a match with [{}] that resulted in the name [{}] and value [{}]", match, matchName, value);
            if (value != null) {
                contents = replaceInTemplate(contents, match, value.toString());
            }
        }

        File tempFile = File.createTempFile("export_" + character.getSaveFileName(), ".xhtml");
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println(contents);
        }

        createPDF(tempFile, pdf);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    public static void createPDF(File tempFile, File pdf) throws IOException, DocumentException {
        try (OutputStream os = new FileOutputStream(pdf)) {
            log.debug("Creating pdf from tempfile [{}]", tempFile);
            ITextRenderer renderer = new ITextRenderer();
            ITextUserAgent callback = new ITextUserAgent(renderer.getOutputDevice());
            callback.setSharedContext(renderer.getSharedContext());
            renderer.getSharedContext().setUserAgentCallback(callback);

            Document doc = XMLResource.load(new InputSource(new FileInputStream(tempFile))).getDocument();

            renderer.setDocument(doc, tempFile.getName());
            renderer.layout();
            renderer.createPDF(os);

            log.debug("PDF [{}] was created", pdf);
        }
    }

    private static final String TEMPLATE_REPLACEMENT_STRING = "#\\{\\w+\\}";

    private static String replaceInTemplate(String template, String search, String replacement) {
        return template.replace(search, replacement);
    }

    public static String getResourceFileAsString(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream is = classLoader.getResourceAsStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        return null;
    }

    /*
     *
     * Character Functions
     */
    public static String modifierToString(int modifier) {
        return modifier > 0 ? "+" + modifier : Integer.toString(modifier);
    }

    public static Object[] getWeaponRow(ICharacter character, IWeapon weapon) {
        // If changed also change the static integers that point the to variables.
        return new Object[]{
            weapon.getName(),
            character.isProficient(weapon),
            getModifierString(character, weapon, true),
            getWeaponDamage(weapon, getModifierString(character, weapon, false)).replaceAll("\\s(\\s)+", " "),
            weapon.getNotes()
        };
    }

    public static String getAttackModifier(ICharacter character, IWeapon weapon) {
        return getModifierString(character, weapon, true);
    }

    public static String getDamageModifier(ICharacter character, IWeapon weapon) {
        return getModifierString(character, weapon, false);
    }

    private static String getModifierString(ICharacter character, IWeapon weapon, boolean addProficiency) {
        final int str = character.getAbilityModifier(AbilityType.STR);
        final int dex = character.getAbilityModifier(AbilityType.DEX);
        final int magic = character.getSpellCastingAbility() != null ? character.getAbilityModifier(character.getSpellCastingAbility()) : -100;

        int modifier;

        // First calculate the base
        if (weapon.getWeaponRange() == WeaponRange.RANGED || (weapon.isFinesse() && dex > str)) {
            modifier = dex;
        } else {
            modifier = str;
        }

        // Check if we should use magic stats
        if (weapon.isCanUseMagicStats() && magic > modifier) {
            modifier = magic;
        }

        // Add bonus
        modifier += addProficiency ? weapon.getAttackModifier() : weapon.getDamageModifier();

        // Add proficiency
        if (addProficiency && character.isProficient(weapon)) {
            modifier += character.getProficiencyScore();
        }

        if (modifier == 0) {
            return "";
        }
        return modifierToString(modifier);
    }

    public static String getWeaponDamage(IWeapon weapon, String modifierString) {
        if (weapon.getAttackDice() == Dice.NONE) {
            log.debug("Formatting [{}] with [{}], [{}]", DAMAGE_FORMAT_SHORT, modifierString.trim(), weapon.getDamageType());
            return String.format(DAMAGE_FORMAT_SHORT, modifierString.trim(), weapon.getDamageType()).trim();
        }
        log.debug("Formatting [{}] with [{}], [{}], [{}], [{}]", DAMAGE_FORMAT, weapon.getAmountOfAttackDice(), weapon.getAttackDice(), modifierString.trim(), weapon.getDamageType());
        return String.format(DAMAGE_FORMAT, weapon.getAmountOfAttackDice(), weapon.getAttackDice(), modifierString.trim(), weapon.getDamageType()).trim();
    }

    /*
     ************************************
     * Browser Functions ***********************************
     */
    public static void browseCharacter(ICharacter character) {
        String searchQuery = null;
        if (character instanceof AbstractExtendedCharacter) {
            List<ICharacterClass> classes = ((AbstractExtendedCharacter) character).getCharacterClasses();
            if (classes != null && !classes.isEmpty()) {
                searchQuery = classes.get(0).getName();
            }
        }
        if (searchQuery == null || searchQuery.isEmpty()) {
            searchQuery = character.getName();
        }
        browseSearch(searchQuery);
    }

    public static void browseSearch(String searchQuery) {
        if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://roll20.net/compendium/dnd5e/searchbook/?terms=" + searchQuery.replace(" ", "%20")));
            } catch (IOException | URISyntaxException e) {
                log.error("Error while opening search in browser", e);
            }
        }
    }

    /*
     ************************************
     * Background Functions ***********************************
     */
    public static Color getBackgroundTransparent() {
        return new Color(0, 0, 0, 0);
    }

    public static Color getBackgroundError() {
        return Color.RED;
    }

    public static Color getBackgroundDown() {
        return Color.GRAY;
    }

    public static Color getBackgroundDead() {
        return Color.DARK_GRAY;
    }

}
