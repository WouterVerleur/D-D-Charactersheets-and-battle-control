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
package com.wouter.dndbattle.objects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wouter.dndbattle.objects.enums.Dice;
import com.wouter.dndbattle.objects.enums.Proficiency;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface IWeapon extends ISaveableClass {

    String getName();

    boolean isFinesse();

    Dice getAttackDice();

    int getAmountOfAttackDice();

    String getDamageType();

    boolean isMagicallyImbued();

    boolean isRanged();

    boolean isThrown();

    int getRange();

    int getMaxRange();

    boolean isReach();

    boolean isLight();

    String getNotes();

    Proficiency getProficiency();

    String getAttackOverride();

    String getDamageOverride();
}
