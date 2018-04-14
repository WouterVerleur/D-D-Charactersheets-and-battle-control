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
import com.wouter.dndbattle.objects.ICombatant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wverl
 */
public class Combatant implements ICombatant {

    private static final Logger log = LoggerFactory.getLogger(Combatant.class);

    private static final String TRANSFORM_NAME_FORMAT = "%s (%s)";
    private static final String HEALTH_FORMAT = "%d (%d%%)";
    private static final String HEALTH_ZERO = "0 (0%)";
    private static final String DEAD = "Dead";

    private final String name;

    private final ICharacter character;
    private Combatant transformation;
    private int health;
    private boolean dead;
    private int deathRolls = 0;
    private int lifeRolls = 0;
    private int healthBuff = 0;
    private int initiative;
    private int totalDamageRecieved = 0;

    public Combatant(ICharacter character, String name, int initiative) {
        this(character, name, initiative, character.getMaxHealth());
    }

    public Combatant(ICharacter character, String name, int initiative, int health) {
        this.name = name;
        this.character = character;
        this.health = health;
        this.dead = false;
        this.initiative = initiative;
    }

    @Override
    public ICharacter getCharacter() {
        if (isTransformed()) {
            transformation.getCharacter();
        }
        return character;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isDead() {
        return dead || health < 0 || deathRolls >= 3;
    }

    @Override
    public int getDeathRolls() {
        return deathRolls;
    }

    public void addDeathRoll() {
        if (!isDead()) {
            deathRolls++;
            checkDead();
        }
    }

    @Override
    public int getLifeRolls() {
        return lifeRolls;
    }

    public void addLifeRoll() {
        if (!isDead()) {
            lifeRolls++;
        }
        if (lifeRolls >= 3) {
            giveHeal(1);
        }
    }

    @Override
    public int getHealthBuff() {
        if (isTransformed()) {
            return transformation.getHealthBuff();
        }
        return healthBuff;
    }

    public void setHealthBuff(int healthBuff) {
        if (isTransformed()) {
            transformation.setHealthBuff(healthBuff);
        } else {
            this.healthBuff = healthBuff;
        }
    }

    @Override
    public String getHealthString() {
        if (isTransformed()) {
            return transformation.getHealthString();
        }
        if (isDead()) {
            return DEAD;
        }
        if (health == 0) {
            return HEALTH_ZERO;
        }
        return String.format(HEALTH_FORMAT, health, (health * 100) / character.getMaxHealth());
    }

    @Override
    public int compareTo(ICombatant t) {
        return t.getInitiative() - initiative;
    }

    @Override
    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void giveHeal(int heal) {
        if (isTransformed()) {
            transformation.giveHeal(heal);
        } else {
            if (isDead()) {
                // Yeah... We don't accept zombies here...
                return;
            }
            deathRolls = 0;
            lifeRolls = 0;
            health += heal;
            if (health > character.getMaxHealth()) {
                health = character.getMaxHealth();
            }
        }
    }

    public int giveDamage(int damage) {
        int damageReturn = damage;
        if (isTransformed()) {
            int newDamage = transformation.giveDamage(damage);
            if (newDamage > 0 && (transformation.isDead() || transformation.getHealth() == 0)) {
                transformation = null;
                giveDamage(newDamage);
            }
        } else {
            if (isDead()) {
                // Stop kicking the dead body.
                return damage;
            }
            totalDamageRecieved += damage;
            if (health == 0 && damage < character.getMaxHealth()) {
                deathRolls += 1;
            } else {
                if (healthBuff >= damage) {
                    healthBuff -= damage;
                    // buff had it, we are done.
                    return 0;
                }
                int damageAfterBuff = damage - healthBuff;
                healthBuff = 0;
                health -= damageAfterBuff;
                if (health < 0) {
                    damageReturn = Math.abs(health);
                }
                if (health < 0 && health > (character.getMaxHealth() * -1)) {
                    // Ok, you were not killed right away...
                    health = 0;
                }
            }
            checkDead();
        }
        return damageReturn;
    }

    @Override
    public int getTotalDamageRecieved() {
        return totalDamageRecieved;
    }

    private void checkDead() {
        if (deathRolls >= 3 || health < 0) {
            dead = true;
        }
    }

    public void transform(ICharacter transform) {
        if (isTransformed()) {
            transformation.transform(transform);
        } else if (character.isCanTransform()) {
            log.debug("Transforming [{}] into [{}]", character.getName(), transform.getName());
            transformation = new Combatant(transform, transform.getName(), initiative);
        }
        log.debug("Transformation of [{}] into [{}] failed", character.getName(), transform.getName());
    }

    @Override
    public boolean isTransformed() {
        return character.isCanTransform() && transformation != null && !transformation.isDead();
    }

    @Override
    public String getName() {
        if (isTransformed()) {
            return String.format(TRANSFORM_NAME_FORMAT, transformation.getName(), name);
        }
        return name;
    }

    public void leaveTransformation() {
        transformation = null;
    }

    public Combatant getTransformation() {
        return transformation;
    }

    @Override
    public String toString() {
        return getName();
    }
}
