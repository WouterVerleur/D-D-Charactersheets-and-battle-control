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
package com.wouter.dndbattle.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Wouter
 */
public class GlobalUtilsTest {

    private static final String EXAMPLE_FILE = "C:/some/path/example.test";

    public GlobalUtilsTest() {
    }

    /**
     * Test of getFileExtension method, of class GlobalUtils.
     */
    @Test
    public void testGetFileExtension() {
        System.out.println("getFileExtension");
        String expResult = "test";
        String result = GlobalUtils.getFileExtension(EXAMPLE_FILE);
        assertEquals("Code was not able to find the file extension.", expResult, result);
    }

    /**
     * Test of getFileName method, of class GlobalUtils.
     */
    @Test
    public void testGetFileName() {
        System.out.println("getFileName");
        String expResult = "example.test";
        String result = GlobalUtils.getFileName(EXAMPLE_FILE);
        assertEquals("Code was not able to find the file name.", expResult, result);
    }

    /**
     * Test of getFileNameWithoutExtention method, of class GlobalUtils.
     */
    @Test
    public void testGetFileNameWithoutExtention() {
        System.out.println("getFileNameWithoutExtention");
        String expResult = "example";
        String result = GlobalUtils.getFileNameWithoutExtention(EXAMPLE_FILE);
        assertEquals("Code was not able to find the file name without extension.", expResult, result);
    }

    /**
     * Test of modifierToString method, of class GlobalUtils.
     */
    @Test
    public void testModifierToString() {
        System.out.println("modifierToString");
        Map<Integer, String> testValues = new HashMap<>(3);
        testValues.put(-2, "-2");
        testValues.put(0, "0");
        testValues.put(2, "+2");

        testValues.entrySet().forEach((entrySet) -> {
            int modifier = entrySet.getKey();
            String result = GlobalUtils.modifierToString(modifier);
            assertEquals("Get modifier string was not able to convert " + modifier + " to the correct string.", entrySet.getValue(), result);
        });

    }

}
