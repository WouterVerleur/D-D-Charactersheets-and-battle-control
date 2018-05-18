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
package com.wouter.dndbattle.objects.impl;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.enums.Dice;
import com.wouter.dndbattle.objects.enums.Proficiency;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Weapon implements IWeapon {

    private int amountOfAttackDice;
    private Dice attackDice;
    private String damageType;
    private boolean finesse;
    private int maxRange;
    private String name;
    private String actualNotes;
    private Proficiency proficiency = Proficiency.NONE;
    private int range;
    private boolean ranged;
    private boolean thrown;
    private boolean reach;
    private boolean light;
    private String attackOverride;
    private String damageOverride;

    @Override
    public String getAttackOverride() {
        return attackOverride;
    }

    public void setAttackOverride(String attackOverride) {
        this.attackOverride = attackOverride;
    }

    @Override
    public String getDamageOverride() {
        return damageOverride;
    }

    public void setDamageOverride(String damageOverride) {
        this.damageOverride = damageOverride;
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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @Override
    public String getNotes() {
        StringBuilder builder = new StringBuilder();
        if (finesse) {
            builder.append("Finesse");
        }
        if (light) {
            checkBuilder(builder);
            builder.append("Light");
        }
        if (reach) {
            checkBuilder(builder);
            builder.append("Reach");
        }
        if (ranged) {
            checkBuilder(builder);
            builder.append(thrown ? "Thrown (" : "Ranged (").append(range).append('/').append(maxRange).append(')');
        }
        if (proficiency.isProficient()) {
            checkBuilder(builder);
            builder.append("Proficient");
        }
        if (actualNotes != null && !actualNotes.isEmpty()) {
            checkBuilder(builder);
            builder.append(actualNotes);
        }
        return builder.toString();
    }

    private void checkBuilder(StringBuilder builder) {
        if (builder.length() > 0) {
            builder.append(", ");
        }
    }

    public String getActualNotes() {
        return actualNotes;
    }

    public void setActualNotes(String actualNotes) {
        this.actualNotes = actualNotes;
    }

    @Override
    public Proficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(Proficiency proficiency) {
        this.proficiency = proficiency;
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
    public boolean isRanged() {
        return ranged;
    }

    public void setRanged(boolean ranged) {
        this.ranged = ranged;
    }

    @Override
    public boolean isReach() {
        return reach;
    }

    public void setReach(boolean reach) {
        this.reach = reach;
    }

    @Override
    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    @Override
    public boolean isThrown() {
        return thrown;
    }

    public void setThrown(boolean thrown) {
        this.thrown = thrown;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Weapon) {
            Weapon other = (Weapon) obj;
            return name.equalsIgnoreCase(other.name) && attackDice.equals(other.attackDice) && amountOfAttackDice == other.amountOfAttackDice;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.amountOfAttackDice;
        hash = 37 * hash + Objects.hashCode(this.attackDice);
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public int compareTo(IWeapon other) {
        int returnValue = name.compareToIgnoreCase(other.getName());
        if (returnValue == 0) {
            returnValue = attackDice.compareTo(other.getAttackDice());
        }
        if (returnValue == 0) {
            returnValue = amountOfAttackDice - other.getAmountOfAttackDice();
        }
        return returnValue;
    }
}
