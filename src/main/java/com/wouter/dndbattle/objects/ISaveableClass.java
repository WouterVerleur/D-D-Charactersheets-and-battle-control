/*
 * Copyright (C) 2018 wverl
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
package com.wouter.dndbattle.objects;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author wverl
 */
public interface ISaveableClass extends Serializable, Comparable<ISaveableClass> {

    public static final String SPECIAL_CHARACTER_REPLACEMENT = "_";
    public static final String SPECIAL_CHARACTER_REGEX = "[^a-zA-Z0-9]+";

    /**
     * Funtion to return a name based string that is save for usage in
     * filenames.
     *
     * @return a filename save representation of the name of this character.
     */
    @JsonIgnore
    public default String getSaveFileName() {
        String fileName = toString().replaceAll(SPECIAL_CHARACTER_REGEX, SPECIAL_CHARACTER_REPLACEMENT);
        if (fileName.startsWith(SPECIAL_CHARACTER_REPLACEMENT)) {
            fileName = fileName.substring(1);
        }
        if (fileName.endsWith(SPECIAL_CHARACTER_REPLACEMENT)) {
            fileName = fileName.substring(0, fileName.length() - 1);
        }
        return fileName;
    }

    @Override
    public default int compareTo(ISaveableClass other) {
        return toString().compareTo(other.toString());
    }

    @Override
    public String toString();

}
