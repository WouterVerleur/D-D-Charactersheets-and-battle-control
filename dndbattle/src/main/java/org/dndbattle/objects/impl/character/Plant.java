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
public class Plant extends AbstractCharacter {

    public Plant() {
    }

    public Plant(ICharacter character) {
        super(character);
    }

    @Override
    public AbstractCharacter clone() {
        return new Plant(this);
    }
}
