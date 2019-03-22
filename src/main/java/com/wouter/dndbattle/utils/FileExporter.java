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

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lowagie.text.DocumentException;
import com.wouter.dndbattle.objects.IExtendedCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.resource.XMLResource;
import org.xml.sax.InputSource;

/**
 *
 * @author wverl
 */
public class FileExporter {

    private static final String PARAM_SPLITTER = "\\.";

    private static final Logger log = LoggerFactory.getLogger(FileExporter.class);

    private static final String TEMPLATE_REPLACEMENT_STRING = "#\\{\\w+(\\.\\w+)*\\}";

    public static void createPDF(IExtendedCharacter character, File pdf) throws IOException, DocumentException {
        String contents = createHtmlContent(character);

        File tempFile = File.createTempFile("export_" + character.getSaveFileName(), ".xhtml");
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println(contents);
        }

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
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdf);
            }
        }

        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    public static void createHTML(IExtendedCharacter character, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(createHtmlContent(character));

            log.debug("HTML [{}] was created", file);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        }
    }

    private static String createHtmlContent(IExtendedCharacter character) throws SecurityException {
        String contents = GlobalUtils.getResourceFileAsString("templates/character.xhtml");
        Class<? extends IExtendedCharacter> aClass = character.getClass();
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
                for (Method method : methods) {
                    log.debug("Checking method [{}] if it can be used", method);
                    if (method.getParameterCount() == paramCount) {
                        try {
                            if (paramCount == 0) {
                                value = method.invoke(character);
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
                if (value != null) {
                    log.debug("The value for [{}] was detemined to be [{}]", name, value);
                    contents = replaceInTemplate(contents, match, value.toString());
                }
            }
        }
        return contents;
    }

    private static String replaceInTemplate(String template, String search, String replacement) {
        return template.replace(search, escapeHtml(replacement).replace("\n", "<br/>"));
    }
}
