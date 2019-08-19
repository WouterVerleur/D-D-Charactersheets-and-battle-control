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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.dndbattle.objects.IAbility;
import org.dndbattle.objects.enums.AbilityType;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Ability implements IAbility {

    private AbilityType abilityType;
    private int score;

    private Ability() {
        // For JSON!!!
    }

    public Ability(AbilityType abilityType) {
        this(abilityType, 10);
    }

    public Ability(AbilityType abilityType, int score) {
        this.abilityType = abilityType;
        this.score = score;
    }

    @Override
    public int compareTo(IAbility o) {
        return abilityType.compareTo(o.getAbilityType());
    }

    @Override
    public AbilityType getAbilityType() {
        return abilityType;
    }

    @JsonIgnore
    @Override
    public int getModifier() {
        return Math.floorDiv(score - 10, 2);
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return abilityType.getFullName();
    }

}
