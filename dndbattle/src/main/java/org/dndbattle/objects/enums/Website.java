/*
 * Copyright (C) 2019 wverl
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
package org.dndbattle.objects.enums;

/**
 *
 * @author wverl
 */
public enum Website {
    DNDBEYOND("https://www.dndbeyond.com/search?q="),
    FANDOM("https://dnd5e.fandom.com/wiki/Special:Search?query="),
    ROLL20("https://roll20.net/compendium/dnd5e/searchbook/?terms=");

    private final String basePath;

    private Website(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }

}
