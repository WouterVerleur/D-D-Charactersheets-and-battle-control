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
import com.wouter.dndbattle.objects.ISaveableClass;
import com.wouter.dndbattle.objects.impl.AbstractCharacter;

/**
 *
 * @author wverl
 */
public class Ooze extends AbstractCharacter {

    public Ooze() {
    }

    public Ooze(ICharacter character) {
        super(character);
    }

    @Override
    public int compareTo(ISaveableClass other) {
        if (other instanceof Ooze) {
            Ooze beast = (Ooze) other;
            int crCompare = getChallengeRating().compareTo(beast.getChallengeRating());
            if (crCompare != 0) {
                return crCompare;
            }
        }
        return super.compareTo(other);
    }
}
