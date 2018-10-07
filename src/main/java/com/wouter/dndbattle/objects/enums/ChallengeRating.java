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
package com.wouter.dndbattle.objects.enums;

/**
 *
 * @author Wouter
 */
public enum ChallengeRating {

    ZERO("0", 2),
    ONE_EIGHT("⅛", 2),
    ONE_FORTH("¼", 2),
    HALF("½", 2),
    ONE("1", 2),
    TWO("2", 2),
    THREE("3", 2),
    FOUR("4", 2),
    FIVE("5", 3),
    SIX("6", 3),
    SEVEN("7", 3),
    EIGHT("8", 3),
    NINE("9", 4),
    TEN("10", 4),
    ELEVEN("11", 4),
    TWELVE("12", 4),
    THIRTEEN("13", 5),
    FOURTEEN("14", 5),
    FIVETEEN("15", 5),
    SIXTEEN("16", 5),
    ZEVENTEEN("17", 6),
    NINETEEN("19", 6),
    TWENTY("20", 6),
    TWENRYONE("21", 7),
    TWENTYTWO("22", 7),
    TWENTYTHREE("23", 7),
    TWENTYFOUR("24", 7),
    THIRTY("30", 8);

    private final String displayName;
    private final int proficiencyScore;

    private ChallengeRating(final String displayName, final int proficiencyScore) {
        this.displayName = displayName;
        this.proficiencyScore = proficiencyScore;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getProficiencyScore() {
        return proficiencyScore;
    }
}
