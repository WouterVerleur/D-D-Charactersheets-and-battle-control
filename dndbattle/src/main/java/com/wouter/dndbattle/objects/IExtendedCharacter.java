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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface IExtendedCharacter extends ICharacter {

    List<ICharacterClass> getCharacterClasses();

    @JsonIgnore
    default int getTotalLevel() {
        int level = 0;
        level = getCharacterClasses().stream().map((playerClass) -> playerClass.getLevel()).reduce(level, Integer::sum);
        return level;
    }

    @JsonIgnore
    default String getHitDice() {
        StringBuilder builder = new StringBuilder();
        getCharacterClasses().forEach((characterClass) -> {
            builder.append(characterClass.getLevel()).append(characterClass.getHitDice()).append(' ');
        });
        if (builder.length() > 0) {
            return builder.toString().trim();
        }
        return " ";
    }

    String getBackground();

    String getPlayerName();

    String getRace();

    String getAlignment();

    int getExperiencePoints();

    String getPersonalityTraits();

    String getIdeals();

    String getBonds();

    String getFlaws();

    String getProficiencies();

    String getLanguages();

    String getEquipment();

    String getFeaturesAndTraits();

    int getAge();

    String getHeight();

    String getWeight();

    String getEyes();

    String getSkin();

    String getHair();

    String getBackstory();

    String getAliesAndOrganizations();

    String getTreasure();
}
