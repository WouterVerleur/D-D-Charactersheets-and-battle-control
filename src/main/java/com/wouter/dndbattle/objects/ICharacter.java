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

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wouter.dndbattle.objects.enums.AbilityType;
import com.wouter.dndbattle.objects.enums.ChallengeRating;
import com.wouter.dndbattle.objects.enums.Proficiency;
import com.wouter.dndbattle.objects.enums.SkillType;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface ICharacter extends Comparable<ICharacter>, Serializable {

    int getAbilityScore(AbilityType abilityType);

    int getAbilityModifier(AbilityType abilityType);

    IArmor getArmor();

    int getArmorClass();

    int getConditionalArmorBonus();

    String getName();

    @JsonIgnore
    String getSaveFileName();

    String getDescription();

    int getInitiative();

    int getMaxHealth();

    int getPassiveWisdom();

    int getSavingThrowModifier(AbilityType abilityType);

    int getSkillModifier(SkillType skillType);

    int getSpeed();

    List<ISpell> getSpells();

    public AbilityType getSpellCastingAbility();

    List<IWeapon> getWeapons();

    boolean isShieldWearer();

    @JsonIgnore
    boolean rollForDeath();

    @JsonIgnore
    int getProficiencyScore();

    boolean isCanTransform();

    Class<? extends ICharacter> getTransformType();

    ChallengeRating getTransformChallengeRating();

    ChallengeRating getChallengeRating();

    boolean isFriendly();

    @JsonIgnore
    String getArmorClassString();

    @JsonIgnore
    Proficiency getSavingThrowProficiency(AbilityType abilityType);

    @JsonIgnore
    Proficiency getSkillProficiency(SkillType skillType);

    String getNotes();
}
