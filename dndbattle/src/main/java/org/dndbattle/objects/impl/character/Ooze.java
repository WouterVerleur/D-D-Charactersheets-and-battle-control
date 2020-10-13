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
package org.dndbattle.objects.impl.character;

import org.dndbattle.objects.ICharacter;
import org.dndbattle.objects.enums.Size;
import org.dndbattle.objects.impl.AbstractCharacter;

/**
 *
 * @author wverl
 */
public class Ooze extends AbstractCharacter {

    private boolean canSplit = false;
    private Size lowestSplitSize = Size.MEDIUM;

    public Ooze() {
    }

    public Ooze(ICharacter character) {
        super(character);
        if (character instanceof Ooze) {
            canSplit = ((Ooze) character).isCanSplit();
        }
    }

    @Override
    public AbstractCharacter clone() {
        return new Ooze(this);
    }

    public boolean isCanSplit() {
        return canSplit;
    }

    public void setCanSplit(boolean canSplit) {
        this.canSplit = canSplit;
    }

    public Size getLowestSplitSize() {
        return lowestSplitSize;
    }

    public void setLowestSplitSize(Size lowestSplitSize) {
        this.lowestSplitSize = lowestSplitSize;
    }
}
