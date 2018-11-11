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
package com.wouter.dndbattle.objects.enums;

/**
 *
 * @author Wouter
 */
public enum Size {

    TINY("Tiny", "0.5"),
    SMALL("Small", "1"),
    MEDIUM("Medium", "1"),
    LARGE("Large", "2"),
    HUGE("Huge", "3"),
    GARGANTUAN("Gargantuan", "4");

    private static final String SIZE_FORMAT = "%1$s x %1$s";
    private static final String STRING_FORMAT = "%s (%s)";

    private final String displayName;
    private final String size;

    private Size(final String displayName, final String size) {
        this.displayName = displayName;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    private String getSizeFormat() {
        return String.format(SIZE_FORMAT, getSize());
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, displayName, getSizeFormat());
    }
}
