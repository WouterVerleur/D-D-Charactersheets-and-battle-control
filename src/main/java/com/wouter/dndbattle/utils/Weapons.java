/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.wouter.dndbattle.objects.IWeapon;
import com.wouter.dndbattle.objects.impl.Weapon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Weapons extends AbstractObjectStorer<IWeapon> {

    private static final Logger log = LoggerFactory.getLogger(Weapons.class);

    private static final Weapons INSTANCE = new Weapons();

    private List<IWeapon> weapons = null;

    private Weapons() {
        super("Weapons");
    }

    public static Weapons getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(IWeapon weapon) {
        if (!canCreate(weapon)) {
            return false;
        }
        getAll().add(weapon);
        Collections.sort(getAll());
        store(weapon, true);
        return true;
    }

    @Override
    public void update(IWeapon weapon) {
        if (getFile(weapon).exists()) {
            store(weapon, false);
        }
    }

    @Override
    public List<IWeapon> getAll() {
        if (weapons == null) {
            weapons = loadFromFiles(Weapon.class);
        }
        return weapons;
    }

    @Override
    public void remove(IWeapon weapon) {
        store(weapon, true);// to make sure any change does not undo this remove
        File file = getFile(weapon);
        if (file.exists()) {
            file.delete();
            getAll().remove(weapon);
            log.debug("Weapon [{}] has been deleted.", weapon);
        }
    }
}
