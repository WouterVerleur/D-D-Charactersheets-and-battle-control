/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import static com.wouter.dndbattle.utils.Settings.WEBSITE;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.ICharacterClass;
import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.enums.AbilityType;
import com.wouter.dndbattle.objects.enums.Dice;
import com.wouter.dndbattle.objects.enums.WeaponRange;
import com.wouter.dndbattle.objects.enums.Website;
import com.wouter.dndbattle.objects.impl.AbstractExtendedCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class GlobalUtils {

    private static final Logger log = LoggerFactory.getLogger(GlobalUtils.class);

    private static final Settings SETTINGS = Settings.getInstance();

    public static final String DAMAGE_FORMAT_SHORT = "%s %s";
    public static final String DAMAGE_FORMAT = "%d%s %s %s";

    public static final int NAME = 0;
    public static final int ATTACK = 2;
    public static final int DAMAGE = 3;

    //******************************
    // File Functions
    //******************************
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

    public static String getResourceFileAsString(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream is = classLoader.getResourceAsStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        return null;
    }

    //******************************
    // Character Functions
    //******************************
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

    //******************************
    // Browser Functions
    //******************************
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
                Website website = Website.valueOf(SETTINGS.getProperty(WEBSITE, Website.ROLL20.name()));
                Desktop.getDesktop().browse(new URI(website.getBasePath() + searchQuery.replace(" ", "+")));
            } catch (IOException | URISyntaxException e) {
                log.error("Error while opening search in browser", e);
            }
        }
    }

    //******************************
    // Background Functions
    //******************************
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
