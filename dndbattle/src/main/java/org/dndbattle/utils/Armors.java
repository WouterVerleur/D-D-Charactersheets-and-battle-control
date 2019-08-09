/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.wouter.dndbattle.objects.IArmor;
import com.wouter.dndbattle.objects.impl.Armor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Armors extends AbstractObjectStorer<IArmor> {

    private static final Logger log = LoggerFactory.getLogger(Armors.class);

    private static final Armors INSTANCE = new Armors();

    private List<IArmor> armors = null;

    private Armors() {
        super("Armors");
    }

    public static Armors getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(IArmor armor) {
        if (!canCreate(armor)) {
            log.debug("Unable to add new armor. Already exists.");
            return false;
        }
        getAll().add(armor);
        Collections.sort(getAll());
        store(armor, true);
        return true;
    }

    @Override
    public void update(IArmor armor) {
        if (getFile(armor).exists()) {
            store(armor, false);
        }
    }

    @Override
    public List<IArmor> getAll() {
        if (!isInitialized()) {
            initialize();
        }
        return armors;
    }

    @Override
    protected void initializeHook() {
        armors = loadFromFiles(Armor.class);
        Collections.sort(armors);
    }

    @Override
    public void remove(IArmor armor) {
        store(armor, true);// to make sure any change does not undo this remove
        File file = getFile(armor);
        if (file.exists()) {
            file.delete();
            getAll().remove(armor);
            log.debug("Armor [{}] has been deleted.", armor);
        }
    }
}
