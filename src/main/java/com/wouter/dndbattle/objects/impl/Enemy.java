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

import com.wouter.dndbattle.objects.ICharacter;

/**
 *
 * @author wverl
 */
public class Enemy extends AbstractCharacter implements ICharacter {

    @Override
    public boolean isFriendly() {
        return false;
    }

    @Override
    public boolean hasChallengeRating() {
        return true;
    }

}
