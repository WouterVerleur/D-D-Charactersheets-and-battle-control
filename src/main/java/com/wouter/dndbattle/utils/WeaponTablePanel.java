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
package com.wouter.dndbattle.utils;

import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.enums.AbilityType;
import com.wouter.dndbattle.objects.enums.Dice;
import static com.wouter.dndbattle.view.master.character.weapon.WeaponsPanel.DAMAGE_FORMAT;
import static com.wouter.dndbattle.view.master.character.weapon.WeaponsPanel.DAMAGE_FORMAT_SHORT;
import javax.swing.JPanel;

/**
 *
 * @author wverl
 */
public abstract class WeaponTablePanel extends JPanel{
    
    protected String[] getWeaponRow(ICharacter character, IWeapon weapon) {
        return new String[]{
            weapon.getName(),
            getModifierString(character, weapon, true),
            getWeaponDamage(weapon, getModifierString(character, weapon, false)),
            weapon.getNotes()
        };
    }

    private String getModifierString(ICharacter character, IWeapon weapon, boolean addProficiency) {
        int modifier = character.getAbilityModifier(AbilityType.STR);
        if (weapon.isFinesse() && character.getAbilityModifier(AbilityType.DEX) > modifier) {
            modifier = character.getAbilityModifier(AbilityType.DEX);
        }

        if (addProficiency) {
            String attackOverride = weapon.getAttackOverride();
            if (attackOverride != null && !attackOverride.isEmpty()) {
                return attackOverride;
            }
            modifier += (character.getProficiencyScore() * weapon.getProficiency().getMultiplier());
        } else {
            String damageOverride = weapon.getDamageOverride();
            if (damageOverride != null && !damageOverride.isEmpty()) {
                return damageOverride;
            }
            if (modifier == 0) {
                return "";
            }
        }
        return GlobalUtils.modifierToString(modifier);
    }

    private String getWeaponDamage(IWeapon weapon, String modifierString) {
        if (weapon.getAttackDice() == Dice.NONE) {
            return String.format(DAMAGE_FORMAT_SHORT, modifierString.trim(), weapon.getDamageType()).trim();
        }
        return String.format(DAMAGE_FORMAT, weapon.getAmountOfAttackDice(), weapon.getAttackDice(), modifierString.trim(), weapon.getDamageType()).trim();
    }
}
