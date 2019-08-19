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
package org.dndbattle.objects.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.dndbattle.objects.ISkill;
import org.dndbattle.objects.enums.Proficiency;
import org.dndbattle.objects.enums.SkillType;

/**
 *
 * @author wverl
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Skill implements ISkill {

    private SkillType skillType;
    private Proficiency proficiency = Proficiency.NONE;

    public Skill() {
        // FOR JSON
    }

    public Skill(SkillType skillType) {
        this.skillType = skillType;
    }

    @Override
    public SkillType getSkillType() {
        return skillType;
    }

    public void setSkilltype(SkillType skillType) {
        this.skillType = skillType;
    }

    @Override
    public Proficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(Proficiency proficiency) {
        this.proficiency = proficiency;
    }

    @Override
    public int compareTo(ISkill other) {
        return skillType.compareTo(other.getSkillType());
    }

}
