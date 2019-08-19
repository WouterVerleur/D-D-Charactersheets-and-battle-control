/*
 * Copyright (C) 2019 wverl
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
package org.dndbattle.utils;

import static org.dndbattle.utils.GlobalUtils.ATTACK;
import static org.dndbattle.utils.GlobalUtils.DAMAGE;
import static org.dndbattle.utils.GlobalUtils.NAME;
import static org.dndbattle.utils.GlobalUtils.NOTES;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.dndbattle.objects.ICharacter;
import org.dndbattle.objects.IEquipment;
import org.dndbattle.objects.IExtendedCharacter;
import org.dndbattle.objects.IInventoryItem;
import org.dndbattle.objects.ISpell;
import org.dndbattle.objects.IWeapon;
import org.dndbattle.objects.enums.WeaponSelection;
import org.dndbattle.objects.enums.WeaponType;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wverl
 */
public class FileExporter {

    private static final String PARAM_SPLITTER = "\\.";

    private static final Logger log = LoggerFactory.getLogger(FileExporter.class);

    private static final String TEMPLATE_REPLACEMENT_STRING = "#\\{\\w+(\\.\\w+)*\\}";
    private static final String WEAPONS_PLACEHOLDER = "#{weapons}";
    private static final String SPELLS_PLACEHOLDER = "#{spells}";
    private static final String EQUIPMENT_PLACEHOLDER = "#{equipment}";
    private static final String WEAPON_ROW_FORMAT = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
    private static final String SPELL_BLOCK_FORMAT = "<table class=\"fullwidth top nopagebreak spell\"><tr><th colspan=\"2\">%s</th><td rowspan=\"7\"><p>%s</p></td></tr><tr><td colspan=\"2\">%s</td></tr><tr><td>Casting Time:</td><td>%s</td></tr><tr><td>Range:</td><td>%s</td></tr><tr><td>Components:</td><td>%s</td></tr><tr><td>Duration:</td><td>%s</td></tr><tr><td colspan=\"2\">Notes:<p>%s</p></td></tr></table>";
    private static final String EQUIPMENT_ROW_FORMAT = "<tr><td>%s<br/>%s</td><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>";
    private static final DecimalFormat FORMATTER = new DecimalFormat("#.##");

    public static void createPDF(ICharacter character, WeaponSelection weaponSelection, File pdf) throws Exception {
        String contents = createHtmlContent(character, weaponSelection);

        File tempFile = File.createTempFile("export_" + character.getSaveFileName(), ".xhtml");
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println(contents);
        }

        try (OutputStream os = new FileOutputStream(pdf)) {
            log.debug("Creating pdf from tempfile [{}]", tempFile);

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withW3cDocument(new W3CDom().fromJsoup(Jsoup.parse(tempFile, "UTF-8")), tempFile.toURI().toString());
            builder.toStream(os);
            builder.run();

            log.debug("PDF [{}] was created", pdf);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdf);
            }
        }

        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    public static void createHTML(ICharacter character, WeaponSelection weaponSelection, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(createHtmlContent(character, weaponSelection));

            log.debug("HTML [{}] was created", file);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        }
    }

    private static String createHtmlContent(ICharacter character, WeaponSelection weaponSelection) throws SecurityException {
        String contents;
        if (character instanceof IExtendedCharacter) {
            contents = GlobalUtils.getResourceFileAsString("templates/extended-character.xhtml");
        } else {
            contents = GlobalUtils.getResourceFileAsString("templates/character.xhtml");
        }
        Class<? extends ICharacter> aClass = character.getClass();
        Map<String, List<Method>> methodMap = new HashMap<>();
        for (Method method : aClass.getMethods()) {
            log.debug("Checking method [{}]", method.getName());
            if (method.getName().startsWith("get")) {
                String name = method.getName().substring(3).toLowerCase();
                log.debug("Found mathod [{}] with name [{}], adding to map", method, name);
                if (!methodMap.containsKey(name)) {
                    methodMap.put(name, new ArrayList<>(1));
                }
                methodMap.get(name).add(method);
            }
        }

        contents = replaceInTemplateNoEscape(contents, WEAPONS_PLACEHOLDER, createWeaponRows(character, weaponSelection));

        contents = replaceInTemplateNoEscape(contents, SPELLS_PLACEHOLDER, createSpellBlocks(character));

        contents = replaceInTemplateNoEscape(contents, EQUIPMENT_PLACEHOLDER, createEquipmentRows(character));

        Pattern pattern = Pattern.compile(TEMPLATE_REPLACEMENT_STRING);
        Matcher matcher = pattern.matcher(contents);
        while (matcher.find()) {
            String match = matcher.group();
            String cleanMatch = match.substring(2, match.length() - 1).toLowerCase();
            String name;
            String[] params = null;
            int paramCount = 0;
            if (cleanMatch.contains(".")) {
                String[] splitMatch = cleanMatch.split(PARAM_SPLITTER);
                name = splitMatch[0];
                params = Arrays.copyOfRange(splitMatch, 1, splitMatch.length);
                paramCount = params.length;
            } else {
                name = cleanMatch;
            }
            List<Method> methods = methodMap.get(name);
            if (methods == null || methods.isEmpty()) {
                log.debug("Unable to find a method for name [{}]", name);
            } else {
                log.debug("Found [{}] method(s) for name [{}]", methods.size(), name);
                Object value = null;
                boolean invoked = false;
                for (Method method : methods) {
                    log.debug("Checking method [{}] if it can be used", method);
                    if (method.getParameterCount() == paramCount) {
                        try {
                            if (paramCount == 0) {
                                value = method.invoke(character);
                                invoked = true;
                            } else {
                                boolean tryNext = false;
                                for (Class<?> parameterType : method.getParameterTypes()) {
                                    log.debug("Checking if parameter [{}] is a String", parameterType);
                                    if (!parameterType.isAssignableFrom(String.class)) {
                                        log.debug("Current method cannot be used.");
                                        tryNext = true;
                                        break;
                                    }
                                }
                                if (tryNext) {
                                    continue;
                                }
                                value = method.invoke(character, (Object[]) params);
                                invoked = true;
                            }
                            if (value != null) {
                                break;
                            }
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            log.error("Unable to invoke method [{}] with parameters [{}] on object [{}]", method, params, character, e);
                        }
                    } else {
                        log.debug("The method [{}] does not have the required amount of paramenters [{}]", method, paramCount);
                    }
                }
                if (value != null || invoked) {
                    log.debug("The value for [{}] was determined to be [{}]", name, value);
                    contents = replaceInTemplate(contents, match, value == null ? "" : value.toString());
                }
            }
        }
        return contents;
    }

    private static String createEquipmentRows(ICharacter character) {
        StringBuilder equipmentRows = new StringBuilder();
        for (IEquipment equipment : character.getInventoryItems()) {
            final IInventoryItem inventoryItem = equipment.getInventoryItem();
            equipmentRows.append(String.format(EQUIPMENT_ROW_FORMAT, inventoryItem, inventoryItem.getDescription(), equipment.getAmount(), FORMATTER.format(inventoryItem.getWeight()), inventoryItem.getValue(), inventoryItem.getNotes()));
        }
        return equipmentRows.toString();
    }

    private static String createSpellBlocks(ICharacter character) {
        StringBuilder spellBlocks = new StringBuilder();
        for (ISpell spell : character.getSpells()) {
            String type;
            switch (spell.getLevel()) {
                case CANTRIP:
                case FEATURE:
                    type = spell.getType() + ' ' + spell.getLevel().toString();
                    break;
                default:
                    type = "Level " + spell.getLevel() + ' ' + spell.getType();
                    break;
            }
            spellBlocks.append(String.format(SPELL_BLOCK_FORMAT, spell.getName(), spell.getDescription(), type, spell.getCastingTime(), spell.getRange(), spell.getComponents(), spell.getDuration(), spell.getNotes()));
        }
        return spellBlocks.toString();
    }

    private static String createWeaponRows(ICharacter character, WeaponSelection weaponSelection) {
        List<IWeapon> weapons = new ArrayList<>(character.getPrivateWeapons());
        switch (weaponSelection) {
            case ALL:
            case PROFICIENT:
                weapons.addAll(Weapons.getInstance().getAll());
                break;
            case EQUIPMENT:
                for (IEquipment equipment : character.getInventoryItems()) {
                    if (equipment.getInventoryItem() instanceof IWeapon) {
                        weapons.add((IWeapon) equipment.getInventoryItem());
                    }
                }
                break;
            case PERSONAL:
                break;
            default:
                log.error("Unknown option [{}]", weaponSelection);
                break;
        }
        Collections.sort(weapons);
        StringBuilder weaponRows = new StringBuilder();
        for (IWeapon weapon : weapons) {
            if (weaponSelection == WeaponSelection.ALL || character.isProficient(weapon) || weapon.getType() == WeaponType.PERSONAL) {
                Object[] weaponRow = GlobalUtils.getWeaponRow(character, weapon);
                weaponRows.append(String.format(WEAPON_ROW_FORMAT, weaponRow[NAME], weaponRow[ATTACK], weaponRow[DAMAGE], weaponRow[NOTES]));
            }
        }
        return weaponRows.toString();
    }

    private static String replaceInTemplate(String template, String search, String replacement) {
        return replaceInTemplateNoEscape(template, search, escapeHtml(replacement));
    }

    private static String replaceInTemplateNoEscape(String template, String search, String replacement) {
        return template.replace(search, replacement);
    }
}
