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

import java.io.Serializable;
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
import com.wouter.dndbattle.objects.ISaveableClass;
import com.wouter.dndbattle.objects.ISavingThrow;
import com.wouter.dndbattle.objects.ISkill;
import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.enums.AbilityType;
import com.wouter.dndbattle.objects.enums.ChallengeRating;
import com.wouter.dndbattle.objects.enums.Proficiency;
import com.wouter.dndbattle.objects.enums.Size;
import com.wouter.dndbattle.objects.enums.SkillType;
import com.wouter.dndbattle.objects.enums.SpellLevel;
import com.wouter.dndbattle.objects.enums.WeaponType;
import com.wouter.dndbattle.utils.Armors;
import com.wouter.dndbattle.utils.Spells;
import com.wouter.dndbattle.utils.Weapons;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class AbstractCharacter implements ICharacter {

    private int armorOverride = 0;
    private IArmor armor;
    private int conditionalArmorBonus = 0;
    private int shieldBonus = 2;
    private boolean friendly;
    private String name;
    private String notes;
    private int speed;
    private List<ISpell> spells = new ArrayList<>();
    private boolean shieldWearer;
    private Map<AbilityType, IAbility> abilities = new HashMap<>(AbilityType.values().length);
    private Map<AbilityType, ISavingThrow> savingThrows = new HashMap<>(AbilityType.values().length);
    private Map<SkillType, ISkill> skills = new HashMap<>(SkillType.values().length);
    private int maxHealth = 1;
    private boolean canTransform = false;
    private Class<? extends ICharacter> transformType;
    private ChallengeRating transformChallengeRating;
    private ChallengeRating challengeRating = ChallengeRating.ZERO;
    private AbilityType spellCastingAbility;
    private Map<SpellLevel, Integer> spellSlots = new HashMap<>(SpellLevel.values().length);
    private WeaponProficiency weaponProficiency;
    private List<IWeapon> privateWeapons = new ArrayList<>();
    private Size size = Size.MEDIUM;

    public AbstractCharacter() {
        createEmptySettings();
    }

    public AbstractCharacter(ICharacter character) {
        this.armor = character.getArmor();
        this.conditionalArmorBonus = character.getConditionalArmorBonus();
        this.friendly = character.isFriendly();
        this.name = character.getName();
        this.notes = character.getNotes();
        this.speed = character.getSpeed();
        this.spells = character.getSpells();
        this.shieldWearer = character.isShieldWearer();
        if (character instanceof AbstractCharacter) {
            final AbstractCharacter aCharacter = (AbstractCharacter) character;
            this.armorOverride = aCharacter.getArmorOverride();
            this.abilities = aCharacter.getAbilities();
            this.savingThrows = aCharacter.getSavingThrows();
            this.skills = aCharacter.getSkills();
            this.spellSlots = aCharacter.getSpellSlots();
            this.weaponProficiency = ((AbstractCharacter) character).getWeaponProficiency();
        } else {
            createEmptySettings();
        }
        this.maxHealth = character.getMaxHealth();
        this.canTransform = character.isCanTransform();
        this.transformType = character.getTransformType();
        this.transformChallengeRating = character.getTransformChallengeRating();
        this.challengeRating = character.getChallengeRating();
        this.spellCastingAbility = character.getSpellCastingAbility();
        this.size = character.getSize();
    }

    @Override
    public AbstractCharacter clone() {
        return new AbstractCharacter(this) {
        };
    }

    private void createEmptySettings() {
        for (AbilityType abilityType : AbilityType.values()) {
            abilities.put(abilityType, new Ability(abilityType));
            savingThrows.put(abilityType, new SavingThrow(abilityType));
        }
        for (SkillType skillType : SkillType.values()) {
            skills.put(skillType, new Skill(skillType));
        }
        for (SpellLevel spellLevel : SpellLevel.values()) {
            spellSlots.put(spellLevel, 0);
        }
        weaponProficiency = new WeaponProficiency();
    }

    @Override
    public int compareTo(ISaveableClass other) {
        if (other instanceof ICharacter) {
            ICharacter character = (ICharacter) other;
            int compare = name.compareToIgnoreCase(character.getName());
            if (compare == 0) {
                return getDescription().compareToIgnoreCase(character.getDescription());
            }
            return compare;
        }
        return ICharacter.super.compareTo(other);
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

    public int getAbilityModifier(String typeName) {
        return getAbilityModifier(AbilityType.valueOf(typeName.toUpperCase()));
    }

    @Override
    public int getAbilityScore(AbilityType abilityType) {
        return abilities.get(abilityType).getScore();
    }

    public int getAbilityScore(String typeName) {
        return getAbilityScore(AbilityType.valueOf(typeName.toUpperCase()));
    }

    public int getArmorOverride() {
        return armorOverride;
    }

    public void setArmorOverride(int armorOverride) {
        this.armorOverride = armorOverride;
    }

    @Override
    public String getArmorClassString() {
        int armorClass;
        if (armor == null) {
            armorClass = 10 + getAbilityModifier(AbilityType.DEX);
        } else {
            armorClass = armor.getArmorClass(this);
        }
        if (armorOverride > 0) {
            armorClass = armorOverride;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(armorClass);
        if (conditionalArmorBonus > 0) {
            builder.append('/').append(armorClass + conditionalArmorBonus);
        }
        if (isShieldWearer()) {
            builder.append('/').append(armorClass + shieldBonus);
        }
        if (conditionalArmorBonus > 0 && isShieldWearer()) {
            builder.append('/').append(armorClass + conditionalArmorBonus + shieldBonus);
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

    @Override
    public int getShieldBonus() {
        return shieldBonus;
    }

    public void setShieldBonus(int shieldBonus) {
        this.shieldBonus = shieldBonus;
    }

    /**
     * Funtion to return a name based string that is save for usage in
     * filenames.
     *
     * @return a filename save representation of the name of this character.
     */
    @Override
    public String getSaveFileName() {
        String fileName = getName().replaceAll(SPECIAL_CHARACTER_REGEX, SPECIAL_CHARACTER_REPLACEMENT);
        while (fileName.startsWith(SPECIAL_CHARACTER_REPLACEMENT)) {
            fileName = fileName.substring(1);
        }
        while (fileName.endsWith(SPECIAL_CHARACTER_REPLACEMENT)) {
            fileName = fileName.substring(0, fileName.length() - 1);
        }
        return fileName;
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

    public int getSavingThrowModifier(String typeName) {
        return getSavingThrowModifier(AbilityType.valueOf(typeName.toUpperCase()));
    }

    @Override
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

    public int getSkillModifier(String typeName) {
        return getSkillModifier(SkillType.valueOf(typeName.toUpperCase()));
    }

    @Override
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

    public String getArmorName() {
        if (armor == null) {
            return "";
        }
        return armor.getName();
    }

    public void setArmorName(String armorName) {
        armor = Armors.getInstance().getForString(armorName);
    }

    @JsonIgnore
    @Override
    public IArmor getArmor() {
        return armor;
    }

    public void setArmor(IArmor armor) {
        this.armor = armor;
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

    @JsonIgnore
    @Override
    public List<ISpell> getSpells() {
        return spells;
    }

    public void setSpells(List<ISpell> spells) {
        if (spells != null) {
            this.spells = spells;
        }
    }

    public void setSpellNames(List<String> spellNames) {
        Spells spellsStorer = Spells.getInstance();
        for (String spellName : spellNames) {
            ISpell spell = spellsStorer.getByString(spellName);
            if (!spells.contains(spell) && spell != null) {
                addSpell(spell);
            }
        }
    }

    public List<String> getSpellNames() {
        List<String> returnList = new ArrayList<>(spells.size());
        spells.forEach((spell) -> {
            returnList.add(spell.toString());
        });
        return returnList;
    }

    public void addSpell(ISpell spell) {
        spells.add(spell);
        if (spell instanceof Spell) {
            ((Spell) spell).addUser(this);
        }
        sortSpells();
    }

    public void removeSpell(ISpell spell) {
        if (spells.remove(spell) && spell instanceof Spell) {
            ((Spell) spell).removeUser(this);
        }
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
    public int getSpellSlotsByLevel(SpellLevel level) {
        return spellSlots.get(level);
    }

    public int getSpellSlotsByLevel(String level) {
        return getSpellSlotsByLevel(SpellLevel.valueOf(level.toUpperCase()));
    }

    public void setSpellSlotsByLevel(SpellLevel level, int slots) {
        spellSlots.put(level, slots);
    }

    public Map<SpellLevel, Integer> getSpellSlots() {
        return spellSlots;
    }

    public void setSpellSlots(Map<SpellLevel, Integer> spellSlots) {
        this.spellSlots = spellSlots;
    }

    @Override
    public boolean isProficient(IWeapon weapon) {
        return weaponProficiency.isProficient(weapon);
    }

    public void setPrivateWeapons(List<IWeapon> privateWeapons) {
        this.privateWeapons = privateWeapons;
    }

    @Override
    public List<IWeapon> getPrivateWeapons() {
        return privateWeapons;
    }

    public void addPrivateWeapon(IWeapon weapon) {
        privateWeapons.add(weapon);
        Collections.sort(privateWeapons);
    }

    public void removePrivateWeapon(IWeapon weapon) {
        privateWeapons.remove(weapon);
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

    @Override
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int getProficiencyScore() {
        if (hasChallengeRating()) {
            return getChallengeRating().getProficiencyScore();
        }
        return 2;
    }

    public WeaponProficiency getWeaponProficiency() {
        return weaponProficiency;
    }

    @Override
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    public static class WeaponProficiency implements Serializable {

        private boolean allWeapons = false;
        private WeaponType type = null;
        private List<IWeapon> weapons = new ArrayList<>();

        public WeaponType getType() {
            return type;
        }

        public void setType(WeaponType type) {
            this.type = type;
        }

        @JsonIgnore
        public List<IWeapon> getWeapons() {
            return weapons;
        }

        public void setWeapons(List<IWeapon> weapons) {
            this.weapons = weapons;
        }

        public void addWeapon(IWeapon weapon) {
            weapons.add(weapon);
        }

        public void removeWeapon(IWeapon weapon) {
            weapons.remove(weapon);
        }

        public List<String> getWeaponNames() {
            List<String> names = new ArrayList<>();
            for (IWeapon weapon : weapons) {
                names.add(weapon.getName());
            }
            return names;
        }

        public void setWeaponNames(List<String> weaponNames) {
            List<IWeapon> completeWeaponList = Weapons.getInstance().getAll();
            for (IWeapon weapon : completeWeaponList) {
                if (weaponNames.contains(weapon.getName())) {
                    weapons.add(weapon);
                }
            }
        }

        public boolean isAllWeapons() {
            return allWeapons;
        }

        public void setAllWeapons(boolean allWeapons) {
            this.allWeapons = allWeapons;
        }

        public boolean isProficient(IWeapon weapon) {
            if (weapon.getType() == WeaponType.PERSONAL) {
                return weapon.isProficient();
            }
            if (allWeapons) {
                return true;
            }
            if (type != null && weapon.getType() == type) {
                return true;
            }
            return weapons.contains(weapon);
        }

    }
}
