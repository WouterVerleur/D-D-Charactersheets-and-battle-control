/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.objects.impl.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Spells extends ObjectStorer<ISpell> {

    private static final Logger log = LoggerFactory.getLogger(Weapons.class);

    private static final Spells INSTANCE = new Spells();

    private List<ISpell> spells = null;

    private Spells() {
    }

    public static Spells getInstance() {
        return INSTANCE;
    }

    public List<ISpell> getAll() {
        if (spells == null) {
            spells = loadFromFiles(Spell.class);
        }
        return spells;
    }

    public boolean addCharacter(ISpell spell) {
        if (!canCreate(spell)) {
            return false;
        }
        getAll().add(spell);
        Collections.sort(getAll());
        store(spell, true);
        return true;
    }

    public void updateCharacter(ISpell spell) {
        if (getFile(spell).exists()) {
            store(spell, false);
        }
    }

    public List<ISpell> getWeapons() {
        return spells;
    }

    @Override
    public void remove(ISpell spell) {
        store(spell, true);// to make sure any change does not undo this remove
        File file = getFile(spell);
        if (file.exists()) {
            file.delete();
            spells.remove(spell);
            log.debug("Character [{}] has been deleted.", spell);
        }
    }
}
