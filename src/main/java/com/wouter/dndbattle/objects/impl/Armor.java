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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wouter.dndbattle.objects.IArmor;
import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.ISaveableClass;
import com.wouter.dndbattle.objects.enums.AbilityType;
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
    private List<AbilityType> additionalAbilityTypes = new ArrayList<>();

    public Armor() {
    }

    public Armor(String name, ArmorType armorType, int baseArmorRating) {
        this.name = name;
        this.armorType = armorType;
        this.baseArmorRating = baseArmorRating;
    }

    public Armor(IArmor armor) {
        this.name = armor.getName();
        this.armorType = armor.getArmorType();
        this.baseArmorRating = armor.getBaseArmorRating();
        this.additionalAbilityTypes = armor.getAdditionalAbilityTypes();
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
    public List<AbilityType> getAdditionalAbilityTypes() {
        return additionalAbilityTypes;
    }

    public void setAdditionalAbilityTypes(List<AbilityType> additionalAbilityTypes) {
        this.additionalAbilityTypes = additionalAbilityTypes;
    }

    public void addAdditionalAbilityType(AbilityType abilityType) {
        additionalAbilityTypes.add(abilityType);
    }

    public void removeAdditionalAbilityType(AbilityType abilityType) {
        additionalAbilityTypes.remove(abilityType);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int getArmorClass(ICharacter character) {
        int dex = character.getAbilityModifier(AbilityType.DEX);
        int ac = baseArmorRating;
        switch (armorType) {
            case LIGHT:
            case UNARMORED:
                ac += dex;
                break;
            case MEDIUM:
                ac += (dex > 2 ? 2 : dex);
                break;
            default:
                break;
        }
        for (AbilityType additionalAbilityType : additionalAbilityTypes) {
            ac += character.getAbilityModifier(additionalAbilityType);
        }
        return ac;
    }

    @Override
    public int compareTo(ISaveableClass other) {
        if (other instanceof IArmor) {
            IArmor armor = (IArmor) other;
            if (armorType == armor.getArmorType()) {
                if (baseArmorRating == armor.getBaseArmorRating()) {
                    return name.compareToIgnoreCase(armor.getName());
                }
                return baseArmorRating - armor.getBaseArmorRating();
            }
            return armorType.compareTo(armor.getArmorType());
        }
        return IArmor.super.compareTo(other);
    }

}
