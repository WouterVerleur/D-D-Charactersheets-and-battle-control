/*
 * Copyright (C) 2019 wverl
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

import java.util.List;
import org.dndbattle.objects.IArmor;
import org.dndbattle.objects.ICharacter;
import org.dndbattle.objects.IEquipment;
import org.dndbattle.objects.ISpell;
import org.dndbattle.objects.IWeapon;
import org.dndbattle.objects.enums.AbilityType;
import org.dndbattle.objects.enums.ChallengeRating;
import org.dndbattle.objects.enums.Proficiency;
import org.dndbattle.objects.enums.Size;
import org.dndbattle.objects.enums.SkillType;
import org.dndbattle.objects.enums.SpellLevel;

/**
 *
 * @author wverl
 */
public class CombatantCharacter implements ICharacter {

    private static final String NAME_FORMAT = "%s (%s)";
    private static final String TEXT_FORMAT = "%s\n\n______________________________\n\n%s";

    private final ICharacter character;
    private CombatantCharacter transformation;
    private boolean completeTransformation;

    public CombatantCharacter(ICharacter combatant) {
        this.character = combatant;
    }

    @Override
    public int getAbilityScore(AbilityType abilityType) {
        if (isDeferToTransformation(abilityType)) {
            return transformation.getAbilityScore(abilityType);
        }
        return character.getAbilityScore(abilityType);
    }

    @Override
    public int getAbilityModifier(AbilityType abilityType) {
        if (isDeferToTransformation(abilityType)) {
            return transformation.getAbilityModifier(abilityType);
        }
        return character.getAbilityModifier(abilityType);
    }

    @Override
    public IArmor getArmor() {
        if (isTransformed()) {
            return transformation.getArmor();
        }
        return character.getArmor();
    }

    @Override
    public int getConditionalArmorBonus() {
        if (isTransformed()) {
            return transformation.getConditionalArmorBonus();
        }
        return character.getConditionalArmorBonus();
    }

    @Override
    public int getShieldBonus() {
        if (isTransformed()) {
            return transformation.getShieldBonus();
        }
        return character.getShieldBonus();
    }

    @Override
    public String getName() {
        if (isTransformed()) {
            return String.format(NAME_FORMAT, transformation.getName(), character.getName());
        }
        return character.getName();
    }

    @Override
    public String getDescription() {
        if (isTransformed()) {
            return transformation.getDescription();
        }
        return character.getDescription();
    }

    @Override
    public int getInitiative() {
        if (isTransformed()) {
            return transformation.getInitiative();
        }
        return character.getInitiative();
    }

    @Override
    public int getInitiativeBonus() {
        if (isTransformed()) {
            return transformation.getInitiativeBonus();
        }
        return character.getInitiativeBonus();
    }

    @Override
    public int getMaxHealth() {
        if (isTransformed()) {
            return transformation.getMaxHealth();
        }
        return character.getMaxHealth();
    }

    @Override
    public int getPassiveWisdom() {
        if (completeTransformation) {
            return transformation.getPassiveWisdom();
        }
        return getSkillModifier(SkillType.PERCEPTION);
    }

    @Override
    public int getSavingThrowModifier(AbilityType abilityType) {
        if (completeTransformation) {
            return transformation.getSavingThrowModifier(abilityType);
        }
        return getAbilityModifier(abilityType) + (getSavingThrowProficiency(abilityType).getMultiplier() * getProficiencyScore());
    }

    @Override
    public int getSkillModifier(SkillType skillType) {
        if (completeTransformation) {
            return transformation.getSkillModifier(skillType);
        }
        return getAbilityModifier(skillType.getAbilityType()) + (getSkillProficiency(skillType).getMultiplier() * getProficiencyScore());
    }

    @Override
    public int getSpeed() {
        if (isTransformed()) {
            return transformation.getSpeed();
        }
        return character.getSpeed();
    }

    @Override
    public List<ISpell> getSpells() {
        if (isTransformed()) {
            return transformation.getSpells();
        }
        return character.getSpells();
    }

    @Override
    public AbilityType getSpellCastingAbility() {
        if (isTransformed()) {
            return transformation.getSpellCastingAbility();
        }
        return character.getSpellCastingAbility();
    }

    @Override
    public boolean isPowerfulBuild() {
        if (isTransformed()) {
            return transformation.isPowerfulBuild();
        }
        return character.isPowerfulBuild();
    }

    @Override
    public List<IEquipment> getInventoryItems() {
        if (isTransformed()) {
            return transformation.getInventoryItems();
        }
        return character.getInventoryItems();
    }

    @Override
    public int getSpellSlotsByLevel(SpellLevel spellLevel) {
        if (isTransformed()) {
            return transformation.getSpellSlotsByLevel(spellLevel);
        }
        return character.getSpellSlotsByLevel(spellLevel);
    }

    @Override
    public List<IWeapon> getPrivateWeapons() {
        if (isTransformed()) {
            return transformation.getPrivateWeapons();
        }
        return character.getPrivateWeapons();
    }

    @Override
    public boolean isProficient(IWeapon weapon) {
        if (completeTransformation) {
            return transformation.isProficient(weapon);
        }
        if (isTransformed()) {
            return character.isProficient(weapon) || transformation.isProficient(weapon);
        }
        return character.isProficient(weapon);
    }

    @Override
    public boolean isShieldWearer() {
        if (isTransformed()) {
            return transformation.isShieldWearer();
        }
        return character.isShieldWearer();
    }

    @Override
    public boolean rollForDeath() {
        return character.rollForDeath();
    }

    @Override
    public int getProficiencyScore() {
        if (completeTransformation) {
            return transformation.getProficiencyScore();
        }
        int proficiencyScore = character.getProficiencyScore();
        if (isTransformed() && proficiencyScore < transformation.getProficiencyScore()) {
            proficiencyScore = transformation.getProficiencyScore();
        }
        return proficiencyScore;
    }

    @Override
    public boolean isCanTransform() {
        if (isTransformed()) {
            return transformation.isCanTransform();
        }
        return character.isCanTransform();
    }

    @Override
    public Class<? extends ICharacter> getTransformType() {
        if (isTransformed()) {
            return transformation.getTransformType();
        }
        return character.getTransformType();
    }

    @Override
    public ChallengeRating getTransformChallengeRating() {
        if (isTransformed()) {
            return transformation.getTransformChallengeRating();
        }
        return character.getTransformChallengeRating();
    }

    @Override
    public ChallengeRating getChallengeRating() {
        if (isTransformed()) {
            return transformation.getChallengeRating();
        }
        return character.getChallengeRating();
    }

    @Override
    public boolean isFriendly() {
        return character.isFriendly();
    }

    @Override
    public String getArmorClassString() {
        if (isTransformed()) {
            return transformation.getArmorClassString();
        }
        return character.getArmorClassString();
    }

    @Override
    public Proficiency getSavingThrowProficiency(AbilityType abilityType) {
        if (completeTransformation) {
            return transformation.getSavingThrowProficiency(abilityType);
        }
        Proficiency proficiency = character.getSavingThrowProficiency(abilityType);
        if (isTransformed() && transformation.getSavingThrowProficiency(abilityType).compareTo(proficiency) > 0) {
            proficiency = transformation.getSavingThrowProficiency(abilityType);
        }
        return proficiency;
    }

    @Override
    public Proficiency getSkillProficiency(SkillType skillType) {
        if (completeTransformation) {
            return transformation.getSkillProficiency(skillType);
        }
        Proficiency proficiency = character.getSkillProficiency(skillType);
        if (isTransformed() && transformation.getSkillProficiency(skillType).compareTo(proficiency) > 0) {
            proficiency = transformation.getSkillProficiency(skillType);
        }
        return proficiency;
    }

    @Override
    public String getNotes() {
        if (isTransformed()) {
            return String.format(TEXT_FORMAT, transformation.getNotes(), character.getNotes());
        }
        return character.getNotes();
    }

    @Override
    public Size getSize() {
        if (isTransformed()) {
            return transformation.getSize();
        }
        return character.getSize();
    }

    public void transform(CombatantCharacter transformation, boolean completeTransformation) {
        if (isTransformed()) {
            transformation.transform(transformation, completeTransformation);
        } else {
            this.transformation = transformation;
            this.completeTransformation = completeTransformation;
        }
    }

    public void leaveTransformation() {
        this.transformation = null;
        this.completeTransformation = false;
    }

    private boolean isDeferToTransformation(AbilityType abilityType) {
        return completeTransformation || (isTransformed() && (abilityType == AbilityType.STR || abilityType == AbilityType.DEX || abilityType == AbilityType.CON));
    }

    private boolean isTransformed() {
        return transformation != null;
    }
}
