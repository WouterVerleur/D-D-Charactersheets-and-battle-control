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

    ZERO("0"),
    ONE_EIGHT("\u215B"),
    ONE_FORTH("\u00BC"),
    HALF("\u00BD"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    ELEVEN("11"),
    TWELVE("12"),
    THIRTEEN("13"),
    FOURTEEN("14"),
    FIVETEEN("15"),
    SIXTEEN("16"),
    ZEVENTEEN("17"),
    NINETEEN("19"),
    TWENTY("20"),
    TWENRYONE("21"),
    TWENTYTWO("22"),
    TWENTYTHREE("23"),
    TWENTYFOUR("24"),
    THIRTY("30");

    private final String displayName;

    private ChallengeRating(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
