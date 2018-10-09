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

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wouter.dndbattle.objects.ISaveableClass;
import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.objects.enums.SpellLevel;

/**
 *
 * @author Wouter
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Spell implements ISpell {

    private String castingTime;
    private String components;
    private String description;
    private String duration;
    private SpellLevel level = SpellLevel.CANTRIP;
    private String name;
    private String notes;
    private String range;

    public Spell() {
    }

    public Spell(ISpell spell) {

        this.castingTime = spell.getCastingTime();
        this.components = spell.getComponents();
        this.description = spell.getDescription();
        this.duration = spell.getDuration();
        this.level = spell.getLevel();
        this.name = spell.getName();
        this.notes = spell.getNotes();
        this.range = spell.getRange();
    }

    @Override
    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    @Override
    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public SpellLevel getLevel() {
        return level;
    }

    public void setLevel(SpellLevel level) {
        if (level != null) {
            this.level = level;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Spell) {
            Spell other = (Spell) obj;
            return name.equalsIgnoreCase(other.name) && level.equals(other.level);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.level);
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public int compareTo(ISaveableClass other) {
        if (other instanceof ISpell) {
            ISpell spell = (ISpell) other;
            int returnValue = level.compareTo(spell.getLevel());
            if (returnValue == 0) {
                if (name == null || spell.getName() == null) {
                    if (name == null) {
                        returnValue = -1;
                        if (spell.getName() == null) {
                            returnValue = 0;
                        }
                    } else {
                        returnValue = 1;
                    }
                } else {
                    returnValue = name.compareToIgnoreCase(spell.getName());
                }
            }
            return returnValue;
        }
        return ISpell.super.compareTo(other);
    }
}
