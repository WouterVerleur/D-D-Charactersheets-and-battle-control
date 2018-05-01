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
package com.wouter.dndbattle.objects.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wouter.dndbattle.objects.ICharacterClass;
import com.wouter.dndbattle.objects.IExtendedCharacter;

/**
 *
 * @author wverl
 */
public abstract class AbstractExtendedCharacter extends AbstractCharacter implements IExtendedCharacter {

    private static final String EMPTY_FORMAT = "Empty %s";

    private int age;
    private String aliesAndOrganizations;
    private String alignment;
    private String backstory;
    private String bonds;
    private String background;
    private List<ICharacterClass> characterClasses = new ArrayList<>();
    private String equipment;
    private int experiencePoints;
    private String eyes;
    private String featuresAndTraits;
    private String flaws;
    private String hair;
    private String height;
    private String ideals;
    private String languages;
    private String personalityTraits;
    private String playerName;
    private String proficiencies;
    private String race;
    private String skin;
    private String treasure;
    private String weight;

    @Override
    public List<ICharacterClass> getCharacterClasses() {
        return characterClasses;
    }

    public void setCharacterClasses(List<ICharacterClass> characterClasses) {
        this.characterClasses = characterClasses;
    }

    public void addCharacterClass(ICharacterClass characterClass) {
        characterClasses.add(characterClass);
    }

    public void removeCharacterClass(ICharacterClass characterClass) {
        characterClasses.remove(characterClass);
    }

    @Override
    public String getBackground() {
        return background;
    }

    public void setBackground(String backGround) {
        this.background = backGround;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Override
    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    @Override
    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    @Override
    public String getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    @Override
    public String getIdeals() {
        return ideals;
    }

    public void setIdeals(String ideals) {
        this.ideals = ideals;
    }

    @Override
    public String getBonds() {
        return bonds;
    }

    public void setBonds(String bonds) {
        this.bonds = bonds;
    }

    @Override
    public String getFlaws() {
        return flaws;
    }

    public void setFlaws(String flaws) {
        this.flaws = flaws;
    }

    @Override
    public String getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(String proficiencies) {
        this.proficiencies = proficiencies;
    }

    @Override
    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    @Override
    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @Override
    public String getFeaturesAndTraits() {
        return featuresAndTraits;
    }

    public void setFeaturesAndTraits(String featuresAndTraits) {
        this.featuresAndTraits = featuresAndTraits;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    @Override
    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    @Override
    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    @Override
    public String getBackstory() {
        return backstory;
    }

    public void setBackstory(String backstory) {
        this.backstory = backstory;
    }

    @Override
    public String getAliesAndOrganizations() {
        return aliesAndOrganizations;
    }

    public void setAliesAndOrganizations(String aliesAndOrganizations) {
        this.aliesAndOrganizations = aliesAndOrganizations;
    }

    @Override
    public String getTreasure() {
        return treasure;
    }

    public void setTreasure(String treasure) {
        this.treasure = treasure;
    }

    @JsonIgnore
    @Override
    public boolean rollForDeath() {
        return true;
    }

    @JsonIgnore
    @Override
    public int getProficiencyScore() {
        if (getCharacterClasses().isEmpty()) {
            return super.getProficiencyScore();
        }
        return (getTotalLevel() - 1) / 4 + 2;
    }

    @Override
    public boolean hasChallengeRating() {
        return false;
    }

    @JsonIgnore
    @Override
    public String getDescription() {
        StringBuilder builder = new StringBuilder();
        if (race != null) {
            builder.append(race);
        }
        getCharacterClasses().forEach((playerClass) -> {
            builder.append(' ').append(playerClass.getName());
        });
        return builder.toString().trim();
    }

    @Override
    public String toString() {
        if (getName() == null || getName().isEmpty()) {
            if (getDescription() == null || getDescription().isEmpty()) {
                return String.format(EMPTY_FORMAT, getClass().getSimpleName());
            }
            return getDescription();
        }
        return getName();
    }
}
