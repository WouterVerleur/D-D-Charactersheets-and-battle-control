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
package com.wouter.dndbattle.objects.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wouter.dndbattle.objects.IArmor;
import com.wouter.dndbattle.objects.enums.ArmorType;

/**
 *
 * @author wverl
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Armor implements IArmor {

    private String name;
    private ArmorType armorType = ArmorType.LIGHT;
    private int baseArmorRating = 10;

    public Armor() {
    }

    public Armor(String name, ArmorType armorType, int baseArmorRating) {
        this.name = name;
        this.armorType = armorType;
        this.baseArmorRating = baseArmorRating;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArmorType getArmorType() {
        return armorType;
    }

    public void setArmorType(ArmorType armorType) {
        if (armorType != null) {
            this.armorType = armorType;
        }
    }

    @Override
    public int getBaseArmorRating() {
        return baseArmorRating;
    }

    public void setBaseArmorRating(int baseArmorRating) {
        this.baseArmorRating = baseArmorRating;
    }

    @Override
    public int compareTo(IArmor other) {
        if (armorType.equals(other.getArmorType())) {
            return baseArmorRating - other.getBaseArmorRating();
        }
        return armorType.compareTo(armorType);
    }

}
