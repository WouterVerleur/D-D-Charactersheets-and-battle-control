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
package org.dndbattle.objects.enums;

import java.text.NumberFormat;

/**
 *
 * @author Wouter
 */
public enum ChallengeRating {

    ZERO("0", 2, 10),
    ONE_EIGHT("⅛", 2, 25),
    ONE_FORTH("¼", 2, 50),
    HALF("½", 2, 100),
    ONE("1", 2, 200),
    TWO("2", 2, 450),
    THREE("3", 2, 700),
    FOUR("4", 2, 1100),
    FIVE("5", 3, 1800),
    SIX("6", 3, 2300),
    SEVEN("7", 3, 2900),
    EIGHT("8", 3, 3900),
    NINE("9", 4, 5000),
    TEN("10", 4, 5900),
    ELEVEN("11", 4, 7200),
    TWELVE("12", 4, 8400),
    THIRTEEN("13", 5, 10000),
    FOURTEEN("14", 5, 11500),
    FIVETEEN("15", 5, 13000),
    SIXTEEN("16", 5, 15000),
    ZEVENTEEN("17", 6, 18000),
    EIGHTEEN("18", 6, 20000),
    NINETEEN("19", 6, 22000),
    TWENTY("20", 6, 25000),
    TWENRYONE("21", 7, 33000),
    TWENTYTWO("22", 7, 41000),
    TWENTYTHREE("23", 7, 50000),
    TWENTYFOUR("24", 7, 62000),
    TWENTYSIX("26", 8, 90000),
    THIRTY("30", 9, 155000);

    private static final String STRING_FORMAT = "%s (%s xp)";
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();

    private final String displayName;
    private final int exp;
    private final int proficiencyScore;

    private ChallengeRating(final String displayName, final int proficiencyScore, final int exp) {
        this.displayName = displayName;
        this.proficiencyScore = proficiencyScore;
        this.exp = exp;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, displayName, nf.format(exp));
    }

    public int getProficiencyScore() {
        return proficiencyScore;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        try {
            return Integer.parseInt(displayName);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public static ChallengeRating getForLevel(final int level) {
        if (level == 0) {
            return ZERO;
        }
        return ChallengeRating.values()[level + 3];
    }
}
