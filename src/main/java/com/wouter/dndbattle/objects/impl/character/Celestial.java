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
package com.wouter.dndbattle.objects.impl.character;

import com.wouter.dndbattle.objects.ICharacter;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;

/**
 *
 * @author wverl
 */
public class Celestial extends AbstractCharacter {

    private static final String STRING_FORMAT = "%s (CR: %s)";

    public Celestial() {
    }

    public Celestial(ICharacter character) {
        super(character);
    }

    @Override
    public int compareTo(ICharacter other) {
        if (other instanceof Celestial) {
            int crCompare = getChallengeRating().compareTo(other.getChallengeRating());
            if (crCompare != 0) {
                return crCompare;
            }
        }
        return super.compareTo(other);
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, getName(), getChallengeRating());
    }
}
