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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import org.dndbattle.objects.enums.AbilityType;
import org.dndbattle.objects.enums.ChallengeRating;
import org.dndbattle.objects.enums.Proficiency;
import org.dndbattle.objects.enums.Size;
import org.dndbattle.objects.enums.SkillType;
import org.dndbattle.objects.enums.SpellLevel;
import org.dndbattle.utils.GlobalUtils;
import static org.dndbattle.view.host.character.spells.SpellOverviewPanel.ABILITY_FORMAT;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface ICharacter extends ISaveableClass, Cloneable {

    int getAbilityScore(AbilityType abilityType);

    int getAbilityModifier(AbilityType abilityType);

    IArmor getArmor();

    int getConditionalArmorBonus();

    int getShieldBonus();

    String getName();

    String getDescription();

    int getInitiative();

    int getInitiativeBonus();

    int getMaxHealth();

    int getPassiveWisdom();

    int getSavingThrowModifier(AbilityType abilityType);

    int getSkillModifier(SkillType skillType);

    boolean isJackOfAllTrades();

    int getSpeed();

    List<ISpell> getSpells();

    AbilityType getSpellCastingAbility();

    boolean isPowerfulBuild();

    List<IEquipment> getInventoryItems();

    @JsonIgnore
    default String getSpellCastingAbilityString() {
        AbilityType spellAbility = getSpellCastingAbility();
        if (spellAbility != null) {
            return String.format(ABILITY_FORMAT, getAbilityScore(spellAbility), GlobalUtils.modifierToString(getAbilityModifier(spellAbility)));
        }
        return " ";
    }

    @JsonIgnore
    default String getSpellAttackBonus() {
        AbilityType spellAbility = getSpellCastingAbility();
        int modifier = getProficiencyScore();
        if (spellAbility != null) {
            modifier += getAbilityModifier(spellAbility);
        }
        return GlobalUtils.modifierToString(modifier);
    }

    @JsonIgnore
    default String getSpellSaveDC() {
        AbilityType spellAbility = getSpellCastingAbility();
        int modifier = 8 + getProficiencyScore();
        if (spellAbility != null) {
            modifier += getAbilityModifier(spellAbility);
        }
        return Integer.toString(modifier);
    }

    @JsonIgnore
    int getSpellSlotsByLevel(SpellLevel spellLevel);

    List<IWeapon> getPrivateWeapons();

    boolean isProficient(IWeapon weapon);

    int getConditionalAttackBonus();

    int getConditionalDamageBonus();

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

    Size getSize();
}
