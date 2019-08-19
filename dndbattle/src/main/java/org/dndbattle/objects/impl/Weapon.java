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
package org.dndbattle.objects.impl;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.dndbattle.objects.ISaveableClass;
import org.dndbattle.objects.IWeapon;
import org.dndbattle.objects.enums.Dice;
import org.dndbattle.objects.enums.WeaponRange;
import org.dndbattle.objects.enums.WeaponType;
import org.dndbattle.objects.enums.WeaponWeight;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Weapon extends AbstractInventoryItem implements IWeapon {

    private static final String DESCRIPTION_FORMAT = "%s weapon";

    private int amountOfAttackDice = 1;
    private Dice attackDice;
    private int attackModifier;
    private boolean canUseMagicStats;
    private int damageModifier;
    private String damageType;
    private boolean finesse;
    private boolean loading;
    private int range;
    private int maxRange;
    private boolean twoHanded;
    private WeaponType type;
    private WeaponRange weaponRange = WeaponRange.MELEE;
    private WeaponWeight weightClass = WeaponWeight.NORMAL;
    private boolean proficient = false;

    public Weapon() {
    }

    public Weapon(WeaponType type) {
        this.type = type;
    }

    @Override
    public int getAmountOfAttackDice() {
        return amountOfAttackDice;
    }

    public void setAmountOfAttackDice(int amountOfAttackDice) {
        this.amountOfAttackDice = amountOfAttackDice;
    }

    @Override
    public Dice getAttackDice() {
        return attackDice;
    }

    public void setAttackDice(Dice attackDice) {
        this.attackDice = attackDice;
    }

    @Override
    public int getAttackModifier() {
        return attackModifier;
    }

    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }

    @Override
    public int getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(int damageModifier) {
        this.damageModifier = damageModifier;
    }

    @Override
    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    @Override
    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    @JsonIgnore
    @Override
    public String getNotes() {
        StringBuilder builder = new StringBuilder();
        if (finesse) {
            builder.append("Finesse");
        }
        if (loading) {
            checkBuilder(builder);
            builder.append("Loading");
        }
        if (twoHanded) {
            checkBuilder(builder);
            builder.append("Two-handed");
        }
        if (canUseMagicStats) {
            checkBuilder(builder);
            builder.append("Magical");
        }
        if (weightClass != WeaponWeight.NORMAL) {
            checkBuilder(builder);
            builder.append(weightClass);
        }
        switch (weaponRange) {
            case REACH:
                checkBuilder(builder);
                builder.append("Reach ").append(range).append("ft.");
                break;
            case THROWN:
                checkBuilder(builder);
                builder.append("Thrown (").append(range).append('/').append(maxRange).append(')');
                break;
            case RANGED:
                checkBuilder(builder);
                builder.append("Ranged (").append(range).append('/').append(maxRange).append(')');
                break;
            default:
                break;
        }
        if (getActualNotes() != null && !getActualNotes().isEmpty()) {
            checkBuilder(builder);
            builder.append(getActualNotes());
        }
        return builder.toString();
    }

    @Override
    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    @Override
    public WeaponRange getWeaponRange() {
        return weaponRange;
    }

    public void setWeaponRange(WeaponRange weaponRange) {
        this.weaponRange = weaponRange;
    }

    @Override
    public WeaponWeight getWeightClass() {
        return weightClass;
    }

    public void setWeightClass(WeaponWeight weightClass) {
        this.weightClass = weightClass;
    }

    @Override
    public boolean isCanUseMagicStats() {
        return canUseMagicStats;
    }

    public void setCanUseMagicStats(boolean canUseMagicStats) {
        this.canUseMagicStats = canUseMagicStats;
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    private void checkBuilder(StringBuilder builder) {
        if (builder.length() > 0) {
            builder.append(", ");
        }
    }

    public String getActualNotes() {
        return super.getNotes();
    }

    public void setActualNotes(String actualNotes) {
        super.setNotes(actualNotes);
    }

    @Override
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public boolean isFinesse() {
        return finesse;
    }

    public void setFinesse(boolean finesse) {
        this.finesse = finesse;
    }

    @Override
    public boolean isTwoHanded() {
        return twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        this.twoHanded = twoHanded;
    }

    public void setProficient(boolean proficient) {
        this.proficient = proficient;
    }

    @Override
    public boolean isProficient() {
        return type == WeaponType.PERSONAL && proficient;
    }

    @JsonIgnore
    @Override
    public String getDescription() {
        return String.format(DESCRIPTION_FORMAT, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Weapon) {
            Weapon other = (Weapon) obj;
            return getName().equalsIgnoreCase(other.getName()) && attackDice.equals(other.attackDice) && amountOfAttackDice == other.amountOfAttackDice;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.amountOfAttackDice;
        hash = 37 * hash + Objects.hashCode(this.attackDice);
        hash = 37 * hash + Objects.hashCode(this.getName());
        return hash;
    }

    @Override
    public int compareTo(ISaveableClass other) {
        if (other instanceof IWeapon) {
            IWeapon weapon = (IWeapon) other;
            int returnValue = getName().compareToIgnoreCase(weapon.getName());
            if (returnValue == 0) {
                returnValue = attackDice.compareTo(weapon.getAttackDice());
            }
            if (returnValue == 0) {
                returnValue = amountOfAttackDice - weapon.getAmountOfAttackDice();
            }
            return returnValue;
        }
        return IWeapon.super.compareTo(other);
    }

    @Override
    public String toString() {
        return getName();
    }

}
