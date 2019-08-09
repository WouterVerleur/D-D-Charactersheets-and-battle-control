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
package com.wouter.dndbattle.objects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *
 * @author wverl
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface IInventoryItem extends ISaveableClass {

    String getName();

    float getWeight();

    String getNotes();

    String getValue();

    String getDescription();

    @Override
    public default int compareTo(ISaveableClass other) {
        if (other instanceof IInventoryItem) {
            int compare = getClass().getName().compareTo(other.getClass().getName());
            if (compare == 0) {
                compare = getName().compareTo(((IInventoryItem) other).getName());
            }
            return compare;
        }
        return ISaveableClass.super.compareTo(other);
    }
}
