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
package com.wouter.dndbattle.utils;

import static com.wouter.dndbattle.utils.GlobalUtils.ATTACK;
import static com.wouter.dndbattle.utils.GlobalUtils.DAMAGE;
import static com.wouter.dndbattle.utils.GlobalUtils.NAME;
import static com.wouter.dndbattle.utils.GlobalUtils.NOTES;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.IExtendedCharacter;
import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.enums.WeaponSelection;
import com.wouter.dndbattle.objects.enums.WeaponType;
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
    private static final String WEAPON_ROW_FORMAT = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
    private static final String SPELL_BLOCK_FORMAT = "<table class=\"fullwidth top nopagebreak spell\"><tr><th colspan=\"2\">%s</th><td rowspan=\"7\"><p>%s</p></td></tr><tr><td colspan=\"2\">%s</td></tr><tr><td>Casting Time:</td><td>%s</td></tr><tr><td>Range:</td><td>%s</td></tr><tr><td>Components:</td><td>%s</td></tr><tr><td>Duration:</td><td>%s</td></tr><tr><td colspan=\"2\">Notes:<p>%s</p></td></tr></table>";

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

        List<IWeapon> weapons = new ArrayList<>(character.getPrivateWeapons());
        if (weaponSelection != WeaponSelection.PERSONAL) {
            weapons.addAll(Weapons.getInstance().getAll());
        }
        Collections.sort(weapons);

        StringBuilder weaponRows = new StringBuilder();
        weapons.stream().filter((weapon) -> (weaponSelection == WeaponSelection.ALL || character.isProficient(weapon) || weapon.getType() == WeaponType.PERSONAL)).map((weapon) -> GlobalUtils.getWeaponRow(character, weapon)).forEachOrdered((weaponRow) -> {
            weaponRows.append(String.format(WEAPON_ROW_FORMAT, weaponRow[NAME], weaponRow[ATTACK], weaponRow[DAMAGE], weaponRow[NOTES]));
        });
        contents = replaceInTemplateNoEscape(contents, WEAPONS_PLACEHOLDER, weaponRows.toString());

        StringBuilder spellBlocks = new StringBuilder();
        for (ISpell spell : character.getSpells()) {
            String type;
            switch (spell.getLevel()) {
                case CANTRIP:
                case FEATURE:
                    type = spell.getType() + ' ' + spell.getLevel().toString();
                    break;
                default:
                    type = "Level " + spell.getLevel() + " " + spell.getType();
                    break;
            }
            spellBlocks.append(String.format(SPELL_BLOCK_FORMAT, spell.getName(), spell.getDescription(), type, spell.getCastingTime(), spell.getRange(), spell.getComponents(), spell.getDuration(), spell.getNotes()));
        }
        contents = replaceInTemplateNoEscape(contents, SPELLS_PLACEHOLDER, spellBlocks.toString());

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

    private static String replaceInTemplate(String template, String search, String replacement) {
        return replaceInTemplateNoEscape(template, search, escapeHtml(replacement));
    }

    private static String replaceInTemplateNoEscape(String template, String search, String replacement) {
        return template.replace(search, replacement);
    }
}
