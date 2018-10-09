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
public enum WeaponRange {

    MELEE("Melee", false),
    REACH("Reach", false),
    THROWN("Thrown", true),
    RANGED("Range", true);
    private final String name;
    private final boolean ranged;

    private WeaponRange(String name, boolean ranged) {
        this.name = name;
        this.ranged = ranged;
    }

    public boolean isRanged() {
        return ranged;
    }

    @Override
    public String toString() {
        return name;
    }

}
