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
package com.wouter.dndbattle.utils;

/**
 *
 * @author Wouter
 */
public class EncounterXpCalculator {

    private static final int EASY = 0;
    private static final int MEDIUM = 1;
    private static final int HARD = 2;
    private static final int DEATHLY = 3;
    private static final int DAILY = 4;

    /*
     * This array was generated from the DMG and holds the easy, medium, hard, deathly & daily exp for a character per level.
     */
    private static final int[][] LEVEL_XP_TABLE = {
        {25, 50, 75, 100, 300},
        {50, 100, 150, 200, 600},
        {75, 150, 225, 400, 1200},
        {125, 250, 375, 500, 1700},
        {250, 500, 750, 1100, 3500},
        {300, 600, 900, 1400, 4000},
        {350, 750, 1100, 1700, 5000},
        {450, 900, 1400, 2100, 6000},
        {550, 1100, 1600, 2400, 7500},
        {600, 1200, 1900, 2800, 9000},
        {800, 1600, 2400, 3600, 10500},
        {1000, 2000, 3000, 4500, 11500},
        {1100, 2200, 3400, 5100, 13500},
        {1250, 2500, 3800, 5700, 15000},
        {1400, 2800, 4300, 6400, 18000},
        {1600, 3200, 4800, 7200, 20000},
        {2000, 3900, 5900, 8800, 25000},
        {2100, 4200, 6300, 9500, 27000},
        {2400, 4900, 7300, 10900, 30000},
        {2800, 5700, 8500, 12700, 40000}
    };

    private static int getExperience(final int level, final int setting) {
        if (level < 1) {
            return getExperience(1, setting);
        }
        if (level > 20) {
            return getExperience(20, setting);
        }
        return LEVEL_XP_TABLE[level - 1][setting];
    }

    public static int getEasyExperience(final int level) {
        return getExperience(level, EASY);
    }

    public static int getMediumExperience(final int level) {
        return getExperience(level, MEDIUM);
    }

    public static int getHardExperience(final int level) {
        return getExperience(level, HARD);
    }

    public static int getDeathlyExperience(final int level) {
        return getExperience(level, DEATHLY);
    }

    public static int getDailyExperience(final int level) {
        return getExperience(level, DAILY);
    }

    /*
     * This array was taken from the DMG
     */
    private static final double[] ENCOUNTER_MULTIPLIER = {0.5, 1, 1.5, 2, 2.5, 3, 4, 5};

    public static double getEncounterMultiplier(final int partySize, final int numberOfMonsters) {
        int base = 1;
        if (numberOfMonsters > 1) {
            base++;
            if (numberOfMonsters > 2) {
                base++;
                if (numberOfMonsters > 6) {
                    base++;
                    if (numberOfMonsters > 10) {
                        base++;
                        if (numberOfMonsters > 14) {
                            base++;
                        }
                    }
                }
            }
        }
        if (partySize < 3 && base > 0) {
            base--;
        } else if (partySize > 6 && base < (ENCOUNTER_MULTIPLIER.length - 1)) {
            base++;
        }
        return ENCOUNTER_MULTIPLIER[base];
    }
}
