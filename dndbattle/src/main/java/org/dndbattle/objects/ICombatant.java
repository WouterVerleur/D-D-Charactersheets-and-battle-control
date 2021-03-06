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
package org.dndbattle.objects;

import java.io.Serializable;

import org.dndbattle.objects.enums.SpellLevel;
import org.dndbattle.objects.impl.CombatantCharacter;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static org.dndbattle.objects.enums.AbilityType.DEX;

/**
 *
 * @author wverl
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface ICombatant extends Comparable<ICombatant>, Serializable {

    String getName();

    String getDescription();

    ICharacter getCharacter();

    CombatantCharacter getCombatantCharacter();

    int getHealth();

    boolean isDead();

    int getDeathRolls();

    int getLifeRolls();

    int getHealthBuff();

    String getHealthString();

    int getInitiative();

    boolean isTransformed();

    ICombatant getTransformation();

    int getTotalDamageRecieved();

    boolean ownedbyPlayer(String playerName);

    boolean rollingForDeath();

    boolean isFriendly();

    boolean isHidden();

    int getUsedSpellSlots(SpellLevel level);

    @Override
    public default int compareTo(ICombatant t) {
        int returnValue = t.getInitiative() - getInitiative();
        if (returnValue == 0) {
            returnValue = t.getCharacter().getAbilityScore(DEX) - getCharacter().getAbilityScore(DEX);
        }
        return returnValue;
    }
}
