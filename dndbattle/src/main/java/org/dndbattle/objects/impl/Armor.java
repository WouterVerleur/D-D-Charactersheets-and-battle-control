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
package org.dndbattle.objects.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.dndbattle.objects.IArmor;
import org.dndbattle.objects.ICharacter;
import org.dndbattle.objects.ISaveableClass;
import org.dndbattle.objects.enums.AbilityType;
import org.dndbattle.objects.enums.ArmorType;

/**
 *
 * @author wverl
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Armor extends AbstractInventoryItem implements IArmor {

    private static final String DESCRIPTION_FORMAT = "%s armor";
    private static final String NOTES_FORMAT = "Base AC %d";

    private ArmorType armorType = ArmorType.LIGHT;
    private int baseArmorRating = 10;
    private List<AbilityType> additionalAbilityTypes = new ArrayList<>();

    public Armor() {
    }

    public Armor(String name, ArmorType armorType, int baseArmorRating) {
        super(name);
        this.armorType = armorType;
        this.baseArmorRating = baseArmorRating;
    }

    public Armor(IArmor armor) {
        super(armor);
        this.armorType = armor.getArmorType();
        this.baseArmorRating = armor.getBaseArmorRating();
        this.additionalAbilityTypes = armor.getAdditionalAbilityTypes();
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

    @JsonIgnore
    @Override
    public String getDescription() {
        return String.format(DESCRIPTION_FORMAT, armorType);
    }
    
    @JsonIgnore
    @Override
    public String getNotes() {
        return String.format(NOTES_FORMAT, baseArmorRating);
    }

    @Override
    public int compareTo(ISaveableClass other) {
        if (other instanceof IArmor) {
            IArmor armor = (IArmor) other;
            if (armorType == armor.getArmorType()) {
                if (baseArmorRating == armor.getBaseArmorRating()) {
                    return getName().compareToIgnoreCase(armor.getName());
                }
                return baseArmorRating - armor.getBaseArmorRating();
            }
            return armorType.compareTo(armor.getArmorType());
        }
        return IArmor.super.compareTo(other);
    }

}
