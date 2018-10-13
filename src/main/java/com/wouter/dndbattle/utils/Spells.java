/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wouter.dndbattle.objects.ISpell;
import com.wouter.dndbattle.objects.impl.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Spells extends AbstractObjectStorer<ISpell> {

    private static final Logger log = LoggerFactory.getLogger(Weapons.class);

    private static final Spells INSTANCE = new Spells();

    private Map<String, ISpell> spells = null;

    private Spells() {
        super("Spells");
    }

    public static Spells getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ISpell> getAll() {
        List<ISpell> returnVal = new ArrayList(getSpells().values());
        Collections.sort(returnVal);
        return returnVal;
    }

    @Override
    public boolean add(ISpell spell) {
        if (!canCreate(spell)) {
            return false;
        }
        getSpells().put(spell.toString(), spell);
        store(spell, true);
        return true;
    }

    @Override
    public void update(ISpell spell) {
        if (getFile(spell).exists()) {
            store(spell, false);
        }
    }

    @Override
    public void remove(ISpell spell) {
        store(spell, true);// to make sure any change does not undo this remove
        File file = getFile(spell);
        if (file.exists()) {
            file.delete();
            getSpells().remove(spell.toString());
            log.debug("Spell [{}] has been deleted.", spell);
        }
    }

    public ISpell getByString(String spellName) {
        return getSpells().get(spellName);
    }

    private Map<String, ISpell> getSpells() {
        if (spells == null) {
            spells = new HashMap<>();
            loadFromFiles(Spell.class).forEach((spell) -> {
                spells.put(spell.toString(), spell);
            });
        }
        return spells;
    }
}
