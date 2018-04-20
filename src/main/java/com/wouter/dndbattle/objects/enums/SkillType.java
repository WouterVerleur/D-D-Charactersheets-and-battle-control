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
package com.wouter.dndbattle.objects.enums;

import static com.wouter.dndbattle.objects.enums.AbilityType.CHA;
import static com.wouter.dndbattle.objects.enums.AbilityType.DEX;
import static com.wouter.dndbattle.objects.enums.AbilityType.INT;
import static com.wouter.dndbattle.objects.enums.AbilityType.STR;
import static com.wouter.dndbattle.objects.enums.AbilityType.WIS;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author Wouter
 */
public enum SkillType {

    ACROBATICS(DEX),
    ANIMAL_HANDLING(WIS),
    ARCANA(INT),
    ATHLETHICS(STR),
    DECEPTION(CHA),
    HISTORY(INT),
    INSIGHT(WIS),
    INTIMIDATION(CHA),
    INVESTIGATION(INT),
    MEDICINE(WIS),
    NATURE(INT),
    PERCEPTION(WIS),
    PERFORMANCE(CHA),
    PERSUASION(CHA),
    RELIGION(INT),
    SLEIGHT_OF_HAND(DEX),
    STEALTH(DEX),
    SURVIVAL(WIS);

    private static final String STRING_FORMAT = "%s (%s)";
    private static final char SPACE = ' ';

    private final AbilityType abilityType;
    private final String casedName;

    private SkillType(final AbilityType abilityType) {
        this.abilityType = abilityType;
        StringBuilder builder = new StringBuilder(name().length());
        for (String part : name().split("_")) {
            builder.append(part.charAt(0)).append(part.toLowerCase().substring(1)).append(SPACE);
        }
        casedName = builder.toString().trim();
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public String getCasedName() {
        return casedName;
    }
    
    @JsonValue
    public String toValue() {
        return name();
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, casedName, abilityType);
    }
}
