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
package com.wouter.dndbattle.objects.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wouter.dndbattle.objects.IAbility;
import com.wouter.dndbattle.objects.IArmor;
import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.ISavingThrow;
import com.wouter.dndbattle.objects.ISkill;
import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.enums.AbilityType;
import com.wouter.dndbattle.objects.enums.ChallengeRating;
import com.wouter.dndbattle.objects.enums.Proficiency;
import com.wouter.dndbattle.objects.enums.SkillType;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class AbstractCharacter implements ICharacter {

    private IArmor armor;
    private int conditionalArmorBonus = 0;
    private boolean friendly;
    private String name;
    private int speed;
    private List<ISpell> spells = new ArrayList<>();
    private boolean shieldWearer;
    private List<IWeapon> weapons = new ArrayList<>();
    private Map<AbilityType, IAbility> abilities = new HashMap<>(AbilityType.values().length);
    private Map<AbilityType, ISavingThrow> savingThrows = new HashMap<>(AbilityType.values().length);
    private Map<SkillType, ISkill> skills = new HashMap<>(SkillType.values().length);
    private int maxHealth = 1;
    private boolean canTransform = false;
    private Class<? extends ICharacter> transformType;
    private ChallengeRating transformChallengeRating;
    private ChallengeRating challengeRating = ChallengeRating.ZERO;
    private AbilityType spellCastingAbility;

    public AbstractCharacter() {
        for (AbilityType abilityType : AbilityType.values()) {
            abilities.put(abilityType, new Ability(abilityType));
            savingThrows.put(abilityType, new SavingThrow(abilityType));
        }
        for (SkillType skillType : SkillType.values()) {
            skills.put(skillType, new Skill(skillType));
        }
    }

    @Override
    public int compareTo(ICharacter other) {
        int compare = name.compareToIgnoreCase(other.getName());
        if (compare == 0) {
            return getDescription().compareToIgnoreCase(other.getDescription());
        }
        return compare;
    }

    public Map<AbilityType, IAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(Map<AbilityType, IAbility> abilities) {
        this.abilities = abilities;
    }

    @Override
    public int getAbilityModifier(AbilityType abilityType) {
        return abilities.get(abilityType).getModifier();
    }

    public int getAbilityScore(AbilityType abilityType) {
        return abilities.get(abilityType).getScore();
    }

    @JsonIgnore
    public String getArmorClassString() {
        StringBuilder builder = new StringBuilder();
        int armorClass = getArmorClass();
        builder.append(armorClass);
        if (conditionalArmorBonus > 0) {
            builder.append('/').append(armorClass + conditionalArmorBonus);
        }
        if (isShieldWearer()) {
            builder.append('/').append(armorClass + 2);
        }
        if (conditionalArmorBonus > 0 && isShieldWearer()) {
            builder.append('/').append(armorClass + conditionalArmorBonus + 2);
        }
        return builder.toString();
    }

    @Override
    public int getConditionalArmorBonus() {
        return conditionalArmorBonus;
    }

    public void setConditionalArmorBonus(int conditionalArmorBonus) {
        this.conditionalArmorBonus = conditionalArmorBonus;
    }

    public boolean hasChallengeRating() {
        return true;
    }

    public void setAbilityScore(AbilityType abilityType, int score) {
        ((Ability) abilities.get(abilityType)).setScore(score);
    }

    @Override
    public int getSavingThrowModifier(AbilityType abilityType) {
        return abilities.get(abilityType).getModifier() + savingThrows.get(abilityType).getProficiency().getMultiplier() * getProficiencyScore();

    }

    public Proficiency getSavingThrowProficiency(AbilityType abilityType) {
        return savingThrows.get(abilityType).getProficiency();
    }

    public void setSavingThrowProficiency(AbilityType abilityType, Proficiency proficiency) {
        ((SavingThrow) savingThrows.get(abilityType)).setProficiency(proficiency);
    }

    public Map<AbilityType, ISavingThrow> getSavingThrows() {
        return savingThrows;
    }

    public void setSavingThrows(Map<AbilityType, ISavingThrow> savingThrows) {
        this.savingThrows = savingThrows;
    }

    @Override
    public int getSkillModifier(SkillType skillType) {
        return abilities.get(skillType.getAbilityType()).getModifier() + getSkillProficiency(skillType).getMultiplier() * getProficiencyScore();
    }

    public Proficiency getSkillProficiency(SkillType skillType) {
        try {
            return skills.get(skillType).getProficiency();
        } catch (NullPointerException e) {
            return Proficiency.NONE;
        }
    }

    public void setSkillProficiency(SkillType skillType, Proficiency proficiency) {
        Skill skill = (Skill) skills.get(skillType);
        if (skill == null) {
            skill = new Skill(skillType);
            skills.put(skillType, skill);
        }
        skill.setProficiency(proficiency);
    }

    public Map<SkillType, ISkill> getSkills() {
        return skills;
    }

    public void setSkills(Map<SkillType, ISkill> skills) {
        this.skills = skills;
    }

    @Override
    public IArmor getArmor() {
        return armor;
    }

    public void setArmor(IArmor armor) {
        this.armor = armor;
    }

    @JsonIgnore
    @Override
    public int getArmorClass() {
        int dexMod = getAbilityModifier(AbilityType.DEX);
        if (armor == null) {
            return 10 + dexMod;
        }
        switch (armor.getArmorType()) {
            case LIGHT:
                return armor.getBaseArmorRating() + dexMod;
            case MEDIUM:
                return armor.getBaseArmorRating() + (dexMod > 2 ? 2 : dexMod);
            default:
                return armor.getBaseArmorRating();
        }

    }

    @JsonIgnore
    @Override
    public int getInitiative() {
        return getAbilityModifier(AbilityType.DEX);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @Override
    public int getPassiveWisdom() {
        return 10 + getSkillModifier(SkillType.PERCEPTION);
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public List<ISpell> getSpells() {
        return spells;
    }

    public void setSpells(List<ISpell> spells) {
        if (spells != null) {
            this.spells = spells;
        }
    }

    public void addSpell(ISpell spell) {
        spells.add(spell);
        sortSpells();
    }

    public void sortSpells() {
        Collections.sort(spells);
    }

    @Override
    public AbilityType getSpellCastingAbility() {
        return spellCastingAbility;
    }

    public void setSpellCastingAbility(AbilityType spellCastingAbility) {
        this.spellCastingAbility = spellCastingAbility;
    }

    @Override
    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<IWeapon> weapons) {
        if (weapons != null) {
            this.weapons = weapons;
        }
    }

    public void addWeapon(IWeapon weapon) {
        weapons.add(weapon);
        Collections.sort(weapons);
    }

    public void removeWeapon(IWeapon weapon) {
        weapons.remove(weapon);
    }

    @Override
    public boolean isShieldWearer() {
        return shieldWearer;
    }

    public void setUsingShield(boolean usingShield) {
        this.shieldWearer = usingShield;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        if (maxHealth > 0) {
            this.maxHealth = maxHealth;
        }
    }

    @JsonIgnore
    @Override
    public boolean rollForDeath() {
        switch (JOptionPane.showConfirmDialog(null, "The character " + this + " is about to die.\nDo you wish to use roll for death for this character?", "Roll for death", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
            case JOptionPane.YES_OPTION:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Class<? extends ICharacter> getTransformType() {
        return transformType;
    }

    public void setTransformType(Class<? extends ICharacter> transformType) {
        this.transformType = transformType;
    }

    @Override
    public boolean isCanTransform() {
        return canTransform;
    }

    public void setCanTransform(boolean canTransform) {
        this.canTransform = canTransform;
    }

    @Override
    public ChallengeRating getTransformChallengeRating() {
        return transformChallengeRating;
    }

    public void setTransformChallengeRating(ChallengeRating transformChallengeRating) {
        if (transformChallengeRating != null) {
            this.transformChallengeRating = transformChallengeRating;
        }
    }

    @Override
    public ChallengeRating getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(ChallengeRating challengeRating) {
        if (challengeRating != null) {
            this.challengeRating = challengeRating;
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    @JsonIgnore
    @Override
    public String getDescription() {
        return getName();
    }
}
