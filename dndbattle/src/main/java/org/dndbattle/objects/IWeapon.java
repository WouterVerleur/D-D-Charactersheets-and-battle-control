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
package org.dndbattle.objects;

import org.dndbattle.objects.enums.Dice;
import org.dndbattle.objects.enums.WeaponRange;
import org.dndbattle.objects.enums.WeaponType;
import org.dndbattle.objects.enums.WeaponWeight;

/**
 *
 * @author Wouter
 */
public interface IWeapon extends IInventoryItem {

    WeaponType getType();

    Dice getAttackDice();

    int getAmountOfAttackDice();

    String getDamageType();

    boolean isFinesse();

    boolean isLoading();

    boolean isTwoHanded();

    boolean isCanUseMagicStats();

    WeaponRange getWeaponRange();

    int getRange();

    int getMaxRange();

    WeaponWeight getWeightClass();

    int getAttackModifier();

    int getDamageModifier();

    boolean isProficient();
}
